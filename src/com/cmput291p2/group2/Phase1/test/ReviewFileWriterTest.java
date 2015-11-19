package com.cmput291p2.group2.Phase1.test;

import com.cmput291p2.group2.Phase1.ReviewFileWriter;
import com.cmput291p2.group2.common.Differ;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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

    @Before
    public void setup() throws Exception {
        reviewFileWriter = new ReviewFileWriter();
    }

    @Test
    public void testRun1() throws Exception {
        reviewFileWriter.run(TEST1_INPUT_FILENAME);

        assertTrue(Differ.filesAreSame(TEST1_EXPECTED_REVIEW_FILENAME, ReviewFileWriter.reviewFile));
        assertTrue(Differ.filesAreSame(TEST1_EXPECTED_RTERM_FILENAME, ReviewFileWriter.rTermFile));
        assertTrue(Differ.filesAreSame(TEST1_EXPECTED_PTERM_FILENAME, ReviewFileWriter.pTermFile));
        assertTrue(Differ.filesAreSame(TEST1_EXPECTED_SCORE_FILENAME, ReviewFileWriter.scoreFile));
    }
}