package com.cmput291p2.group2.Phase1;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;

import java.io.*;
import java.util.ArrayList;

/**
 * class ReviewFileWriter is responsible for reading reviews from stdin and
 * writing them to comma separated files in the format specified on eClass.
 * <p/>
 * The files it writes are used by
 * {@link com.cmput291p2.group2.Phase2.IndexBuilder}.
 */
public class ReviewFileWriter {

    public static final String reviewFile = "reviews.txt";
    public static final String pTermFile = "pterms.txt";
    public static final String rTermFile = "rterms.txt";
    public static final String scoreFile = "scores.txt";
    private static final String regexSplit = "[^0-9a-zA-Z_]";

    public void run() {
        this.run(new BufferedReader(new InputStreamReader(System.in)));
    }

    public void run(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            this.run(reader);
        } catch (FileNotFoundException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("FileNotFoundException for filename: %s\n", filename);
            }
        }
    }

    public void run(BufferedReader reader) {
        try {
            String line;
            ArrayList<String> reviewDetails = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    reviewDetails = this.writeOutput(reviewDetails);
                } else {
                    reviewDetails.add(line);
                }
            }
            this.writeOutput(reviewDetails);
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Writes all relevant details about a review to all relevant files.
     * Returns a new empty ArrayList.
     *
     * The return value should be assigned back to reviewDetails.
     *
     * @param reviewDetails Details of the review to be written to files
     * @return An empty ArrayList to assign back to reviewDetails
     */
    private ArrayList<String> writeOutput(ArrayList<String> reviewDetails) {
        Review review = new Review(reviewDetails);
        this.writeReview(review);
        this.writePTerm(review);
        this.writeRTerm(review);
        this.writeScore(review);
        return new ArrayList<>();
    }

    private void writeReview(Review review) {
        try {
            FileWriter fw = new FileWriter(reviewFile, true);
            //As described by spec, should be: reviewid, productid, title, price, userid, profilename, helpfulness, score
            //time, summary and text
            //These fields should be escaped with "": title, profilename, summary, text
            fw.write(String.format("%d,%s,\"%s\",%s,%s,\"%s\",%s,%s,%s,\"%s\",\"%s\"%s",
                    review.getReviewId(), review.getProductId(),
                    review.getTitle(), // " "
                    review.getPrice(), review.getUserId(),
                    review.getProfileName(), // " "
                    review.getHelpfulness(), review.getScore(), review.getTime(),
                    review.getSummary(), // " "
                    review.getText(), // " "
                    System.lineSeparator()));
            fw.close();
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    private void writePTerm(Review review) {
        try {
            FileWriter fw = new FileWriter(pTermFile, true);
            for (String s : review.getTitle().split(regexSplit)) {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + String.valueOf(review.getReviewId()) + System.lineSeparator());
                }
            }
            fw.close();
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    private void writeRTerm(Review review) {
        try {
            FileWriter fw = new FileWriter(rTermFile, true);
            for (String s : review.getSummary().split(regexSplit)) {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + String.valueOf(review.getReviewId()) + System.lineSeparator());
                }
            }
            for (String s : review.getText().split(regexSplit)) {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + String.valueOf(review.getReviewId()) + System.lineSeparator());
                }
            }
            fw.close();
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    private void writeScore(Review review) {
        try {
            FileWriter fw = new FileWriter(scoreFile, true);
            fw.write(review.getScore() + "," + String.valueOf(review.getReviewId()));
            fw.close();
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }
}
