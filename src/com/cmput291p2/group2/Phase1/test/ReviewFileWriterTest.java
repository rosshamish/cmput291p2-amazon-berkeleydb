package com.cmput291p2.group2.Phase1.test;

import com.cmput291p2.group2.Phase1.ReviewFileWriter;
import com.cmput291p2.group2.common.Differ;
import com.cmput291p2.group2.common.Review;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class ReviewFileWriterTest {
    private static final String TEST_PATH = "src" + File.separator +
            "com" + File.separator +
            "cmput291p2" + File.separator +
            "group2" + File.separator +
            "Phase1" + File.separator +
            "test" + File.separator;
    private static final String TEST1_INPUT_FILENAME = TEST_PATH + "Phase1_Test1_Input.txt";
    private static final String TEST1_EXPECTED_PREFIX = TEST_PATH + "Phase1_Test1_Expected_";
    private static final String TEST1_EXPECTED_REVIEW_FILENAME = TEST1_EXPECTED_PREFIX + ReviewFileWriter.reviewFile;
    private static final String TEST1_EXPECTED_PTERM_FILENAME = TEST1_EXPECTED_PREFIX + ReviewFileWriter.pTermFile;
    private static final String TEST1_EXPECTED_RTERM_FILENAME = TEST1_EXPECTED_PREFIX + ReviewFileWriter.rTermFile;
    private static final String TEST1_EXPECTED_SCORE_FILENAME = TEST1_EXPECTED_PREFIX + ReviewFileWriter.scoreFile;

    private ReviewFileWriter reviewFileWriter;

    private String duration(Date before) {
        Long duration = new Date().getTime() - before.getTime();
        return String.format("%dms (%ds)\n", duration, duration / 1000);
    }

    private String getInputFilename(String size) {
        return getFilePath(size, String.format("%s.txt", size));
    }

    private String getFilePath(String size, String filename) {
        return getSizeDirectory(size) + filename;
    }

    private String getSizeDirectory(String size) {
        return TEST_PATH + File.separator +
                size + File.separator;
    }

    @Before
    public void setup() throws Exception {
        reviewFileWriter = new ReviewFileWriter();
        Review.resetReviewIds();
    }

    @Test
    public void testRun10() throws Exception {
        final String size = "10";

        Date before = new Date();
        System.out.println("Input size: " + size);
        reviewFileWriter.run(getInputFilename(size));
        System.out.println(duration(before));

        assertTrue(Differ.filesAreSame(getFilePath(size, "reviews.txt"), ReviewFileWriter.reviewFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "rterms.txt"), ReviewFileWriter.rTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "pterms.txt"), ReviewFileWriter.pTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "scores.txt"), ReviewFileWriter.scoreFile));
    }
    @Test
    public void testRun1k() throws Exception {
        final String size = "1k";

        Date before = new Date();
        System.out.println("Input size: " + size);
        reviewFileWriter.run(getInputFilename(size));
        System.out.println(duration(before));

        assertTrue(Differ.filesAreSame(getFilePath(size, "reviews.txt"), ReviewFileWriter.reviewFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "rterms.txt"), ReviewFileWriter.rTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "pterms.txt"), ReviewFileWriter.pTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "scores.txt"), ReviewFileWriter.scoreFile));
    }
    @Test
    public void testRun10k() throws Exception {
        final String size = "10k";

        Date before = new Date();
        System.out.println("Input size: " + size);
        reviewFileWriter.run(getInputFilename(size));
        System.out.println(duration(before));

        assertTrue(Differ.filesAreSame(getFilePath(size, "reviews.txt"), ReviewFileWriter.reviewFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "rterms.txt"), ReviewFileWriter.rTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "pterms.txt"), ReviewFileWriter.pTermFile));
        assertTrue(Differ.filesAreSame(getFilePath(size, "scores.txt"), ReviewFileWriter.scoreFile));
    }
    @Test
    public void testRun100k() throws Exception {
        final String size = "100k";

        Date before = new Date();
        System.out.println("Input size: " + size);
        reviewFileWriter.run(getInputFilename(size));
        System.out.println(duration(before));

        /* Don't diff output, the files are too big, this is a waste of time */
    }


}