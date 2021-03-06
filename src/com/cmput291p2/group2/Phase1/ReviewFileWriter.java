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
    
    private static FileWriter fwReview;
    private static FileWriter fwPTerm;
    private static FileWriter fwRTerm;
    private static FileWriter fwScore;
    
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
            this.clearOutputFiles();
            this.openOutputFilesForAppend();
            String line;
            ArrayList<String> reviewDetails = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    reviewDetails = this.writeOutput(reviewDetails, true);
                } else {
                    reviewDetails.add(line);
                }
            }
            //In case empty lines at end of file
            if (reviewDetails.size() > 1) {
                this.writeOutput(reviewDetails, false);
            }
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("IOException: %s\n", e.getMessage());
            }
            return;
        }
        try {
        	this.closeOutputFiles();
        } catch (IOException e) {
        	if (Debugging.isEnabled()) {
        		System.err.printf("IOException: %s\n", e.getMessage());
        	}
        	return;
        }
        System.out.println("Success. Files created:");
        System.out.printf("\t%s\n", reviewFile);
        System.out.printf("\t%s\n", pTermFile);
        System.out.printf("\t%s\n", rTermFile);
        System.out.printf("\t%s\n", scoreFile);
    }

    /**
     * Deletes all content in the following files:
     * <ul>
     * <li>reviewFile</li>
     * <li>pTermFile</li>
     * <li>rTermFile</li>
     * <li>scoreFile</li>
     * </ul>
     *
     * @throws IOException if any files are not found or if file writing fails
     */
    private void clearOutputFiles() throws IOException {
        clearOutputFile(reviewFile);
        clearOutputFile(pTermFile);
        clearOutputFile(rTermFile);
        clearOutputFile(scoreFile);
    }

    private void clearOutputFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write("");
        fw.close();
    }
    
    /**
     * Open all output files for appending
     */
    private void openOutputFilesForAppend() throws IOException {
    	fwReview = new FileWriter(reviewFile, true);
    	fwPTerm = new FileWriter(pTermFile, true);
    	fwRTerm = new FileWriter(rTermFile, true);
    	fwScore = new FileWriter(scoreFile, true);
    }
    
    /**
     * Closes all output files
     */
    private void closeOutputFiles() throws IOException {
    	fwReview.close();
    	fwPTerm.close();
    	fwRTerm.close();
    	fwScore.close();
    }

    /**
     * Writes all relevant details about a review to all relevant files.
     * Returns a new empty ArrayList.
     * <p/>
     * The return value should be assigned back to reviewDetails.
     *
     * @param reviewDetails Details of the review to be written to files
     * @return An empty ArrayList to assign back to reviewDetails
     */
    private ArrayList<String> writeOutput(ArrayList<String> reviewDetails, Boolean trailingNewline) {
        Review review = new Review(reviewDetails);
        this.appendReview(review, trailingNewline);
        this.appendPTerm(review, trailingNewline);
        this.appendRTerm(review, trailingNewline);
        this.appendScore(review, trailingNewline);
        return new ArrayList<>();
    }

    /**
     * Appends a review object to reviews.txt as described by spec.
     * <p/>
     * Should be: reviewid,productid,title,price,userid,profilename,helpfulness,score,time,summary,text
     * These fields should be escaped with "": title, profilename, summary, text
     *
     * @param review          the Review object to write to file.
     * @param trailingNewline True if a newline should be printed, False otherwise
     */
    private void appendReview(Review review, Boolean trailingNewline) {
        String reviewString = String.format("%d,%s,\"%s\",%s,%s,\"%s\",%s,%s,%s,\"%s\",\"%s\"",
                review.getReviewId(), review.getProductId(),
                review.getTitle(), // " "
                review.getPrice(), review.getUserId(),
                review.getProfileName(), // " "
                review.getHelpfulness(), review.getScore(), review.getTime(),
                review.getSummary(), // " "
                review.getText());
        if (trailingNewline) {
            reviewString += System.lineSeparator();
        }
        try {
            fwReview.write(reviewString);
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Appends a review's data to pterms.txt as described by spec.
     *
     * @param review          the Review object whose data should be appended to pterms.txt.
     * @param trailingNewline True if a newline should be printed, False otherwise
     */
    private void appendPTerm(Review review, Boolean trailingNewline) {
        try {
            String[] terms = review.getTitle().split(regexSplit);
            for (int i = 0; i < terms.length; i++) {
                String term = terms[i];
                if (term.length() >= 3) {
                    String toWrite = String.format("%s,%d",
                            term.toLowerCase(), review.getReviewId());
                    if (i < terms.length - 1 || trailingNewline) {
                        toWrite += System.lineSeparator();
                    }
                    fwPTerm.write(toWrite);
                }
            }
        } catch (Exception e) {
            if (Debugging.isEnabled()) {
                System.err.printf("AppendPTerm failed: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Appends a review's data to rterms.txt as described by spec.
     *
     * @param review          the Review object whose data should be appended to rterms.txt.
     * @param trailingNewline True if a newline should be printed, False otherwise
     */
    private void appendRTerm(Review review, Boolean trailingNewline) {
        try {
            for (String term : review.getSummary().split(regexSplit)) {
                if (term.length() >= 3) {
                    String toWrite = String.format("%s,%d",
                            term.toLowerCase(), review.getReviewId());
                    toWrite += System.lineSeparator();
                    fwRTerm.write(toWrite);
                }
            }
            String[] terms = review.getText().split(regexSplit);
            for (int i = 0; i < terms.length; i++) {
                String term = terms[i];
                if (term.length() >= 3) {
                    String toWrite = String.format("%s,%d",
                            term.toLowerCase(), review.getReviewId());
                    if (i < terms.length - 1 || trailingNewline) {
                        toWrite += System.lineSeparator();
                    }
                    fwRTerm.write(toWrite);
                }
            }
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Appends a review's data to scores.txt as described by spec.
     *
     * @param review          the Review object whose data should be appended to scores.txt.
     * @param trailingNewline True if a newline should be printed, False otherwise
     */
    private void appendScore(Review review, Boolean trailingNewline) {
        try {
            String toWrite = String.format("%s,%d", review.getScore(), review.getReviewId());
            if (trailingNewline) {
                toWrite += System.lineSeparator();
            }
            fwScore.write(toWrite);
        } catch (IOException e) {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s\n", e.getMessage());
            }
        }
    }
}
