package com.cmput291p2.group2.Phase1;

import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;

/**
 * class ReviewFileWriter is responsible for reading reviews from stdin and
 * writing them to comma separated files in the format specified on eClass.
 *
 * The files it writers are used by
 * {@link com.cmput291p2.group2.Phase2.IndexBuilder}.
 */
public class ReviewFileWriter {

    private static final String reviewFile = "reviews.txt";
    private static final String pTermFile = "pterms.txt";
    private static final String rTermFile = "rterms.txt";
    private static final String scoreFile = "scores.txt";
    private static final String regexSplit = "[^0-9a-zA-Z_]";
    private Integer count = 0;

    public void run(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            ArrayList<String> productDesc = new ArrayList();
            count++;
            while ((line = br.readLine()) != null)
            {
                if(line == "\n")
                {
                    Review product = new Review(productDesc);
                    this.writeReview(product);
                    this.writePTerm(product);
                    this.writeRTerm(product);
                    this.writeScore(product);
                    productDesc = new ArrayList();
                    count++;
                }
                else
                {
                    productDesc.add(line);
                }
            }
        }
        catch (IOException e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s", e.getMessage());
            }
        }
    }

    private void writeReview(Review product)
    {
        try {
            FileWriter fw = new FileWriter(reviewFile, true);
            //As described by spec, should be: count, productid, title, price, userid, profilename, helpfulness, score
            //time, summary and text
            //These fields should be escaped with "": title, profilename, summary, text
            fw.write(count + "," + product.getReviewId() + ",\"" + product.getTitle() + "\"," +
                    product.getPrice() + "," + product.getUserId() + ",\"" + product.getProfileName() +
                    "\"," + product.getHelpfulness() + "," + product.getScore() + "," + product.getTime() + ",\"" +
                    product.getSummary() + "\",\"" + product.getText() + "\"" + System.lineSeparator());
            fw.close();
        }
        catch (IOException e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s", e.getMessage());
            }
        }
    }

    private void writePTerm(Review product)
    {
        try {
            FileWriter fw = new FileWriter(pTermFile, true);
            for (String s : product.getTitle().split(regexSplit))
            {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + count + System.lineSeparator());
                }
            }
            fw.close();
        }
        catch (IOException e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s", e.getMessage());
            }
        }
    }

    private void writeRTerm(Review product)
    {
        try {
            FileWriter fw = new FileWriter(rTermFile, true);
            for (String s : product.getSummary().split(regexSplit))
            {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + count + System.lineSeparator());
                }
            }
            for (String s : product.getText().split(regexSplit))
            {
                if (s.length() >= 3) {
                    fw.write(s.toLowerCase() + "," + count + System.lineSeparator());
                }
            }
            fw.close();
        }
        catch (IOException e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s", e.getMessage());
            }
        }
    }

    private void writeScore(Review product)
    {
        try {
            FileWriter fw = new FileWriter(scoreFile, true);
            fw.write(product.getScore() + "," + count);
            fw.close();
        }
        catch (IOException e)
        {
            if (Debugging.isEnabled()) {
                System.err.printf("Filewrite IOException: %s", e.getMessage());
            }
        }
    }
}
