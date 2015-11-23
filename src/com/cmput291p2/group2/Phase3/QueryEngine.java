package com.cmput291p2.group2.Phase3;

import java.util.*;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;
import com.sleepycat.db.*;

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
        Set<String> b = dateOrPriceRangeQuery(query);
        for (String s : b)
        {
            System.out.println(s);
        }
        return null;
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
                reviewId = Integer.parseInt(new String(data.getData()));
                score = Double.parseDouble(new String(key.getData()));
                while (score > searchNumber && oprStatus == OperationStatus.SUCCESS)
                {
                    results.add(reviewId);
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getPrev(key, data, LockMode.DEFAULT);
                    reviewId = Integer.parseInt(new String(data.getData()));
                    score = Double.parseDouble(new String(key.getData()));
                }
            }
            if (operand.equals("<")) {
                //Start at the front and work up to that number
                oprStatus = cursor.getFirst(key, data, LockMode.DEFAULT);
                reviewId = Integer.parseInt(new String(data.getData()));
                score = Double.parseDouble(new String(key.getData()));
                while (score < searchNumber && oprStatus == OperationStatus.SUCCESS)
                {
                    results.add(reviewId);
                    key = new DatabaseEntry();
                    data = new DatabaseEntry();
                    oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
                    reviewId = Integer.parseInt(new String(data.getData()));
                    score = Double.parseDouble(new String(key.getData()));
                }
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("QueryFailed: %s\n", e.getMessage());
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
            foundData = keyDataToReviewString(key, data);
            while (oprStatus == OperationStatus.SUCCESS)
            {
                if (operand.equals("<") && !dateOrPriceCompare(queryData, foundData)) {
                    results.add(foundData);
                }
                if (operand.equals(">") && dateOrPriceCompare(queryData, foundData)) {
                    results.add(foundData);
                }
                key = new DatabaseEntry();
                data = new DatabaseEntry();
                oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
                foundData = keyDataToReviewString(key, data);
            }
        } catch (Exception e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("QueryFailed: %s\n", e.getMessage());
            }
        }
        return results;
    }

    //If data is greater than otherData, return true
    private Boolean dateOrPriceCompare(String data, String otherData)
    {
        Review reviewData = new Review(otherData);
        if (data.contains("/")) {
            String[] dateData = data.split("/");
            Calendar thisDate = Calendar.getInstance();
            //year month day
            thisDate.set(Integer.parseInt(dateData[0]), Integer.parseInt(dateData[1]), Integer.parseInt(dateData[2]));
            Calendar otherDate = Calendar.getInstance();
            otherDate.setTimeInMillis(Long.parseLong(reviewData.getTime()));
            return thisDate.compareTo(otherDate) > 0;
        }
        else {
            return Double.parseDouble(data) > Double.parseDouble(reviewData.getPrice());
        }
    }

    private Set<String> prefixQuery(String query)
    {
        return null;
    }

    private Set<String> matchQuery(String query)
    {
        return null;
    }

    private Set<String> getReviewsByIds(Set<Integer> ids)
    {
        Set<String> results = new HashSet<>();
        try {
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();
            Cursor cursor = this.rwIndex.openCursor(null, null);
            for (Integer id : ids)
            {
                String keyString = id.toString();
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
