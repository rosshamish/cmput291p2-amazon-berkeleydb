package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Review;

import java.util.Collection;

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
        return null;
    }
}
