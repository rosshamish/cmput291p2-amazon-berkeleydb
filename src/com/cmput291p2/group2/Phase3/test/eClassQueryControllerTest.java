package com.cmput291p2.group2.Phase3.test;

import com.cmput291p2.group2.Phase3.QueryController;
import com.cmput291p2.group2.Phase3.QueryEngine;
import com.cmput291p2.group2.common.Review;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class eClassQueryControllerTest {

    QueryController qc;

    @Before
    public void setup() {
        qc = new QueryController(new QueryEngine("rw.idx", "pt.idx", "rt.idx", "sc.idx"));
    }

    @Test
    public void test1() {
        String query = "p:camera";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() == 0);
    }

    @Test
    public void test2() {
        String query = "r:great";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        Set<Integer> expectedReviewIds = new HashSet<Integer>() {{
            add(new Integer(164));
            add(new Integer(171));
            add(new Integer(173));
            add(new Integer(178));
            add(new Integer(179));
            add(new Integer(181));
            add(new Integer(184));
            add(new Integer(188));
            add(new Integer(204));
            add(new Integer(806));
            add(new Integer(848));
            add(new Integer(852));
            add(new Integer(853));
            add(new Integer(854));
            add(new Integer(855));
            add(new Integer(856));
            add(new Integer(858));
            add(new Integer(860));
            add(new Integer(862));
            add(new Integer(863));
            add(new Integer(864));
            add(new Integer(869));
        }};
        for (Review review : reviews) {
            assertTrue(expectedReviewIds.contains(review.getReviewId()));
            expectedReviewIds.remove(review.getReviewId());
        }
        assertTrue(expectedReviewIds.isEmpty());
    }

    @Test
    public void test3_query() {
        String titleOrSummaryOrText = "camera";
        String query = String.format("%s", titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
        }
    }

    @Test
    public void test4_queryWithWildcard() {
        String titleOrSummaryOrText = "cam";
        String query = String.format("%s%%", titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
        }
    }

    @Test
    public void test5_rtermAndQueryWithWildcard() {
        String summaryOrText = "great";
        String titleOrSummaryOrText = "cam";
        String query = String.format("r:%s %s%%", summaryOrText, titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getSummary().toLowerCase().contains(summaryOrText) ||
                    review.getText().toLowerCase().contains(summaryOrText));
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
        }
    }

    @Test
    public void test6_rscoreGTComparison() {
        String rscoreGT = "4";
        String query = String.format("rscore > %s", rscoreGT);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(Double.valueOf(review.getScore()) > Double.valueOf(rscoreGT));
        }
    }

    @Test
    public void test7_queryAndRscoreLTComparison() {
        String titleOrSummaryOrText = "camera";
        String rscoreLT = "3";
        String query = String.format("%s rscore < %s", titleOrSummaryOrText, rscoreLT);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(Double.valueOf(review.getScore()) < Double.valueOf(rscoreLT));
        }
    }

    @Test
    public void test8_ppriceLTComparisonAndQuery() {
        String ppriceLT = "60";
        String titleOrSummaryOrText = "camera";
        String query = String.format("pprice < %s %s", ppriceLT, titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(Double.valueOf(review.getPrice()) < Double.valueOf(ppriceLT));
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
        }
    }

    @Test
    public void test9_queryAndRdateGTComparison() throws ParseException {
        String titleOrSummaryOrText = "camera";
        String rdateGT = "2007/06/20";
        String query = String.format("%s rdate > %s", titleOrSummaryOrText, rdateGT);
        Collection<Review> reviews = qc.executeQuery(query);

        Calendar rdateGTCal = Calendar.getInstance();
        rdateGTCal.setTime(new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(rdateGT));

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(review.getTimeAsCalendar().after(rdateGTCal));
        }
    }

    @Test
    public void test10() throws ParseException {
        String query = String.format("%s rdate > %s pprice > %s pprice < %s",
                titleOrSummaryOrText, rdateGT, ppriceGT, ppriceLT);
        Collection<Review> reviews = qc.executeQuery(query);

        Calendar rdateGTCal = Calendar.getInstance();
        rdateGTCal.setTime(new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(rdateGT));

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(review.getTimeAsCalendar().after(rdateGTCal));
            assertTrue(Double.valueOf(review.getPrice()) > Double.valueOf(ppriceGT));
            assertTrue(Double.valueOf(review.getPrice()) < Double.valueOf(ppriceLT));
        }
    }
}