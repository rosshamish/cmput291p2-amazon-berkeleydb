package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * class QueryIO is responsible for collecting user input from the command line, and for
 * printing output back to the user.
 *
 * It is expected to delegate:
 * - building the query from the input string
 * - executing the query on the indexes
 *
 * It is used by QueryCLI.
 */
public class QueryIO {

    public String getQueryInput() {
        return this.readLine(">> ");
    }

    public void printOutput(String message) {
        // ...
    }

    public void printTable(Collection<Review> reviews) {
        // ...
    }

    private String readLine(String format, Object... args) {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.print(String.format(format, args));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("readLine IOException: %s", e.getMessage());
            }
        }
        return "NULL";
    }
}
