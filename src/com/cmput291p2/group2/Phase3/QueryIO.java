package com.cmput291p2.group2.Phase3;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * class QueryIO is responsible for collecting user input from the command line, and for
 * printing output back to the user.
 * <p/>
 * It is expected to delegate:
 * - building the query from the input string
 * - executing the query on the indexes
 * <p/>
 * It is used by QueryCLI.
 */
public class QueryIO {

    public String getQueryInput() {
        return this.readLine(">> ");
    }

    public void printOutput(String message) {
        System.out.println(message);
    }

    public void printTable(Collection<Review> reviews) {

        List lreviews;
        if (reviews instanceof List){
            lreviews = (List)reviews;
        }
        else{
            lreviews = new ArrayList(reviews);
        }
        Collections.sort(lreviews, new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                int productId1 = Integer.parseInt(o1.getProductId());
                int productId2 = Integer.parseInt(o2.getProductId());
                return productId1 - productId2;
            }
        });
        if (reviews.size() == 0) {
            System.out.println("Your Query has no results");

        }

        else{
            System.out.println(Review.rowDescription());
            Iterator<Review> itr = lreviews.iterator();
            while(itr.hasNext()) {
                Review review = itr.next();
                System.out.println(review.rowToString());
            }
        }
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
