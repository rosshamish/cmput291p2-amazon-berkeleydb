package com.cmput291p2.group2.Phase3;

import java.io.File;
import java.util.Set;

/**
 * class QueryEngine is responsible for performing query operations
 * on the BerkeleyDB indexes on behalf of the QueryController.
 */
public class QueryEngine implements IQueryEngine {

    private File rwIndex;
    private File ptIndex;
    private File rtIndex;
    private File scIndex;

    /**
     * Creates a QueryEngine object which operates on the four given BerkeleyDB index files.
     *
     * @param rwIndex the `rw.idx` file as described in the eClass specification
     * @param ptIndex the `pt.idx` file as described in the eClass specification
     * @param rtIndex the `rt.idx` file as described in the eClass specification
     * @param scIndex the `sc.idx` file as described in the eClass specification
     */
    public QueryEngine(File rwIndex, File ptIndex, File rtIndex, File scIndex) {
        this.rwIndex = rwIndex;
        this.ptIndex = ptIndex;
        this.rtIndex = rtIndex;
        this.scIndex = scIndex;
    }

    public Set<String> executeQuery(String query)
    {
        return null;
    }
}
