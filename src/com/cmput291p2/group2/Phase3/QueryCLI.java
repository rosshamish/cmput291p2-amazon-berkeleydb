package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Review;

import java.util.Collection;

/**
 * class QueryCLI is responsible for interacting with the user
 *
 * It delegates to:
 * - QueryIO for input and output
 * - QueryController for performing non-trivial tasks
 *
 * It is run directly by Main.
 */
public class QueryCLI implements Runnable {

    private QueryController controller;
    private QueryIO io;

    public QueryCLI() {
        controller = new QueryController();
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
