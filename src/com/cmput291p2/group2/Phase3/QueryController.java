package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * class QueryController is responsible for performing high-level
 * tasks on behalf of the QueryCLI.
 * <p/>
 * Each query contains 1..N sub-queries.
 * <p/>
 * It delegates sub-query execution to the QueryEngine.
 */
public class QueryController {
    private IQueryEngine queryEngine;

    public QueryController(IQueryEngine queryEngine) {
        this.queryEngine = queryEngine;
    }


    /**
     * executeQuery is responsible for
     * - splitting the query into its respective subqueries,
     * - delegating each subquery to the QueryEngine,
     * - AND-ing the results back together (ie set intersection)
     * - returning the results to the caller
     * <p/>
     * Example:
     * <pre>
     * query is "rscore > 3 camera pprice < 60"
     * sub-queries are {"rscore > 3", "camera", "pprice < 60"}
     * </pre>
     * <p/>
     * It is up to the implementer whether
     * (a) the QueryEngine has methods specific to each index,
     * and the QueryController decides which indexes to use and
     * calls the specific methods, or
     * (b) the QueryEngine has one method, executeQuery, and
     * the QueryController calls that on each subquery. The QueryEngine
     * decides which indexes to use to perform the query.
     * <p/>
     * I'm partial to (b).
     *
     * @param query the query (containing 1..N sub-queries)
     * @return all matching Reviews
     */
    public Collection<Review> executeQuery(String query) {
        try {
            Collection<String> subQueries = this.splitQuery(query);
            Boolean firstIteration = true;
            Set<String> commonSet = new HashSet<>();
            for (String q : subQueries) {
                if (firstIteration) {
                    commonSet.addAll(queryEngine.executeQuery(q));
                } else {
                    commonSet.retainAll(queryEngine.executeQuery(q));
                }
            }
            Collection<Review> reviews = new ArrayList<>();
            for (String r : commonSet){
                reviews.add(new Review(r));
            }
            return reviews;
        } catch (Exception ex)
        {
            System.out.println("Invalid query, please try again.");
            return new ArrayList<>();
        }
    }

    //Every sub query is one term unless it contains > or <
    private Collection<String> splitQuery(String query) {
        ArrayList<String> subQueries = new ArrayList<>();
        String[] spaceSplitQuery = query.split("\\s+");
        for (int i = 0; i < spaceSplitQuery.length; i++)
        {
            if (i < spaceSplitQuery.length - 2 && spaceSplitQuery[i+1].matches(">|<"))
            {
                subQueries.add(String.format("%s %s %s",
                        spaceSplitQuery[i], spaceSplitQuery[i+1], spaceSplitQuery[i+2]));
                i = i + 2;
            } else {
                subQueries.add(spaceSplitQuery[i]);
            }
        }
        return subQueries;
    }
}
