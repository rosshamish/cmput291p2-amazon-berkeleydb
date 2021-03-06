package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;
import com.sleepycat.db.*;

import java.util.*;

/**
 * class QueryEngine is responsible for performing query operations
 * on the BerkeleyDB indexes on behalf of the QueryController.
 */
public class QueryEngine implements IQueryEngine {

    private Database rwIndex;
    private Database ptIndex;
    private Database rtIndex;
    private Database scIndex;

    /**
     * Creates a QueryEngine object which operates on the four given BerkeleyDB index files.
     *
     * @param rwIndex the `rw.idx` file as described in the eClass specification
     * @param ptIndex the `pt.idx` file as described in the eClass specification
     * @param rtIndex the `rt.idx` file as described in the eClass specification
     * @param scIndex the `sc.idx` file as described in the eClass specification
     */
    public QueryEngine(String rwIndex, String ptIndex, String rtIndex, String scIndex) {
        try {
            //Am unsure if you can reuse the same database config object
            DatabaseConfig dbConfigHash = new DatabaseConfig();
            dbConfigHash.setType(DatabaseType.HASH);
            this.rwIndex = new Database(rwIndex, null, dbConfigHash);
            DatabaseConfig dbConfigBTree = new DatabaseConfig();
            dbConfigBTree.setType(DatabaseType.BTREE);
            this.ptIndex = new Database(ptIndex, null, dbConfigBTree);
            this.rtIndex = new Database(rtIndex, null, dbConfigBTree);
            this.scIndex = new Database(scIndex, null, dbConfigBTree);
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("DatabaseFailed: %s\n", e.getMessage());
            }
        }
    }

    public Set<String> executeQuery(String query)
    {
        Set<String> results = Collections.emptySet();
        int last = query.length();
        Boolean ispartial = false;
        if (query.charAt(last-1) == '%') {
            ispartial = true;
        }
        if (query.charAt(0) == 'r' && query.charAt(1) == ':') {
            String newquery = query.replace("r:","");
            if (ispartial){
                newquery = newquery.replace("%","");
            }
            results = matchQuery(newquery, false, true, ispartial);
            return results;
        }
        if (query.charAt(0) == 'p' && query.charAt(1) == ':') {
            String newquery = query.replace("p:","");
            if (ispartial){
                newquery = newquery.replace("%","");
            }
            results = matchQuery(newquery, true, false, ispartial);
            return results;
        }

        if (query.contains("score >") || query.contains("score <")){
            results = doubleRangeQuery(query);
            return results;
        }
        if (query.contains("price <") || query.contains("price >") || query.contains("date >") || query.contains("date <")){
            results = dateOrPriceRangeQuery(query);
            return results;
        }
        else{
            if (ispartial){
                query = query.replace("%","");
            }
            results = matchQuery(query, true, true, ispartial);
            return results;
        }
    }

    private Set<String> doubleRangeQuery(String query)
    {
        Set<Integer> results = new HashSet<>();
        try {
            OperationStatus oprStatus;
            Double score;
            Integer reviewId;
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();
            String[] brokenQuery = query.split("\\s+");
            String operand = brokenQuery[1];
            Double searchNumber = Double.parseDouble(brokenQuery[2]);
            Cursor cursor = this.scIndex.openCursor(null, null);
            if (operand.equals(">")) {
                //Start at the back and work up to that number
                oprStatus = cursor.getLast(key, data, LockMode.DEFAULT);
                while (oprStatus == OperationStatus.SUCCESS && Double.parseDouble(new String(key.getData())) > searchNumber)
                {
                    reviewId = Integer.parseInt(new String(data.getData()));
                    results.add(reviewId);
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getPrev(key, data, LockMode.DEFAULT);
                }
            }
            if (operand.equals("<")) {
                //Start at the front and work up to that number
                oprStatus = cursor.getFirst(key, data, LockMode.DEFAULT);
                while (oprStatus == OperationStatus.SUCCESS && Double.parseDouble(new String(key.getData())) < searchNumber)
                {
                    reviewId = Integer.parseInt(new String(data.getData()));
                    results.add(reviewId);
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
                }
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Double Range Query Failed: %s\n", e.getMessage());
            }
        }
        return getReviewsByIds(results);
    }

    private Set<String> dateOrPriceRangeQuery(String query)
    {
        Set<String> results = new HashSet<>();
        try {
            OperationStatus oprStatus;
            String foundData;
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();
            String[] brokenQuery = query.split("\\s+");
            String operand = brokenQuery[1];
            String queryData = brokenQuery[2];
            Cursor cursor = this.rwIndex.openCursor(null, null);
            oprStatus = cursor.getFirst(key, data, LockMode.DEFAULT);
            while (oprStatus == OperationStatus.SUCCESS)
            {
                foundData = keyDataToReviewString(key, data);
                try {
                    if (operand.equals("<") && !dateOrPriceCompare(queryData, foundData)) {
                        results.add(foundData);
                    }
                    if (operand.equals(">") && dateOrPriceCompare(queryData, foundData)) {
                        results.add(foundData);
                    }
                    //Parsing int can throw error if its value "unknown", which means it wont match query
                } catch (Exception e)
                {
                }
                key = new DatabaseEntry();
                data = new DatabaseEntry();
                oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Date or Price Query Failed: %s\n", e.getMessage());
            }
        }
        return results;
    }

    //If other data is greater than data, return true
    private Boolean dateOrPriceCompare(String data, String otherData)
    {
        Review reviewData = new Review(otherData);
        if (data.contains("/")) {
            String[] dateData = data.split("/");
            Calendar thisDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            Integer year = Integer.parseInt(dateData[0]);
            Integer month = Integer.parseInt(dateData[1]) - 1; // months are 0-based
            Integer day = Integer.parseInt(dateData[2]);
            thisDate.set(year, month, day);
            return thisDate.before(reviewData.getTimeAsCalendar());
        }
        else {
            return Double.parseDouble(reviewData.getPrice()) > Double.parseDouble(data);
        }
    }

    private Set<String> matchQuery(String query, Boolean isPTerm, Boolean isRTerm, Boolean isPrefix)
    {
        Set<Integer> results = new HashSet<>();
        try {
            if (isPTerm) {
                results.addAll(matchQueryHelper(query, this.ptIndex.openCursor(null, null), isPrefix));
            }
            if (isRTerm) {
                results.addAll(matchQueryHelper(query, this.rtIndex.openCursor(null, null), isPrefix));
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Match query failed: %s\n", e.getMessage());
            }
        }
        return getReviewsByIds(results);
    }

    private Set<Integer> matchQueryHelper(String query, Cursor cursor, Boolean isPrefix)
    {
        Set<Integer> results = new HashSet<>();
        try {
            OperationStatus oprStatus;
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();
            key.setData(query.getBytes());
            key.setSize(query.length());
            if (isPrefix)
            {
                oprStatus = cursor.getSearchKeyRange(key, data, LockMode.DEFAULT);
                while (oprStatus == OperationStatus.SUCCESS &&
                        new String(key.getData()).length() >= query.length() &&
                        new String(key.getData()).substring(0, query.length()).equals(query))
                {
                    results.add(Integer.parseInt(new String(data.getData())));
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
                }
            } else {
                oprStatus = cursor.getSearchKey(key, data, LockMode.DEFAULT);
                while (oprStatus == OperationStatus.SUCCESS && new String(key.getData()).equals(query)) {
                    results.add(Integer.parseInt(new String(data.getData())));
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
                }
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Match query failed: %s\n", e.getMessage());
            }
        }
        return results;
    }

    private Set<String> getReviewsByIds(Set<Integer> ids)
    {
        Set<String> results = new HashSet<>();
        try {
            DatabaseEntry key;
            DatabaseEntry data;
            Cursor cursor = this.rwIndex.openCursor(null, null);
            for (Integer id : ids)
            {
                String keyString = id.toString();
                key = new DatabaseEntry();
                data = new DatabaseEntry();
                key.setData(keyString.getBytes());
                key.setSize(keyString.length());
                cursor.getSearchKey(key, data, LockMode.DEFAULT);
                results.add(keyDataToReviewString(key, data));
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Fetching review by id failed: %s\n", e.getMessage());
            }
        }
        return results;
    }

    private String keyDataToReviewString(DatabaseEntry key, DatabaseEntry data)
    {
        return new String(key.getData()) + "," + new String(data.getData());
    }
}
