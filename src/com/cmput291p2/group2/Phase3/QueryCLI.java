package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Review;

import java.io.File;
import java.util.Collection;

/**
 * class QueryCLI is responsible for interacting with the user
 * <p/>
 * It delegates to:
 * - QueryIO for input and output
 * - QueryController for performing non-trivial tasks
 * <p/>
 * It is run directly by Main.
 */
public class QueryCLI {

    private QueryController controller;
    private QueryIO io;

    public QueryCLI() {
        QueryEngine qe = new QueryEngine(new File("rw.idx"), new File("pt.idx"), new File("rt.idx"), new File("sc.idx"));
        controller = new QueryController(qe);
        io = new QueryIO();
    }

    public void run() {
        System.out.println("Amazon Review Query Engine\n");

        while (true) {
            String query = io.getQueryInput();
            Collection<Review> reviews = controller.executeQuery(query);
            io.printTable(reviews);
        }
    }

}
