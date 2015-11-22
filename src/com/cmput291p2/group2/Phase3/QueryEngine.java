package com.cmput291p2.group2.Phase3;

import java.util.HashSet;
import java.util.Set;
import com.cmput291p2.group2.common.Debugging;
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
        doubleRangeQuery(query);
        return null;
    }

    private Set<Integer> doubleRangeQuery(String query)
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
            Integer searchNumber = Integer.parseInt(brokenQuery[2]);
            Cursor cursor = this.scIndex.openCursor(null, null);
            if (operand.equals(">")) {
                //Start at the back and work up to that number
                oprStatus = cursor.getLast(key, data, LockMode.DEFAULT);
                reviewId = Integer.parseInt(new String(data.getData()));
                score = Double.parseDouble(new String(key.getData()));
                while (score > searchNumber && oprStatus == OperationStatus.SUCCESS)
                {
                    results.add(reviewId);
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
        return results;
    }

    private Set<Integer> prefixQuery(String query)
    {
        return null;
    }

    private Set<Integer> matchQuery(String query)
    {
        return null;
    }
}
