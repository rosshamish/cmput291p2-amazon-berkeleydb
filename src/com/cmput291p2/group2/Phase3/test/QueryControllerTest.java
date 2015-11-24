package com.cmput291p2.group2.Phase3.test;

import com.cmput291p2.group2.Phase3.QueryController;
import com.cmput291p2.group2.Phase3.QueryEngine;
import com.cmput291p2.group2.common.Review;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QueryControllerTest {

    QueryController qc;

    @Before
    public void setup() {
        qc = new QueryController(new QueryEngine("rw.idx", "pt.idx", "rt.idx", "sc.idx"));
    }

    @Test
    public void test1_pterm() {
        String title = "cameras";
        String query = String.format("p:%s", title);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(title));
        }
    }

    @Test
    public void test2_rterm() {
        String summaryOrText = "great";
        String query = String.format("r:%s", summaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getSummary().toLowerCase().contains(summaryOrText) ||
                    review.getText().toLowerCase().contains(summaryOrText));
        }
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
            assertTrue(Integer.valueOf(review.getScore()) > Integer.valueOf(rscoreGT));
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
            assertTrue(Integer.valueOf(review.getScore()) < Integer.valueOf(rscoreLT));
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
            assertTrue(Integer.valueOf(review.getPrice()) < Integer.valueOf(ppriceLT));
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
        }
    }

    @Test
    public void test8b_ppriceGTComparisonAndQuery() {
        String ppriceGT = "60";
        String titleOrSummaryOrText = "camera";
        String query = String.format("pprice > %s %s", ppriceGT, titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(Integer.valueOf(review.getPrice()) > Integer.valueOf(ppriceGT));
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

        Date rdateGTDate = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(rdateGT);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(review.getTimeAsDate().after(rdateGTDate));
        }
    }

    @Test
    public void test9b_queryAndRdateLTComparison() throws ParseException {
        String titleOrSummaryOrText = "camera";
        String rdateLT = "2007/06/20";
        String query = String.format("%s rdate < %s", titleOrSummaryOrText, rdateLT);
        Collection<Review> reviews = qc.executeQuery(query);

        Date rdateLTDate = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(rdateLT);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(review.getTimeAsDate().before(rdateLTDate));
        }
    }

    @Test
    public void test10_queryAndRdateGTComparisonAndPpriceGTComparisonAndPpriceLTComparison() throws ParseException {
        String titleOrSummaryOrText = "camera";
        String rdateGT = "2007/06/20";
        String ppriceGT = "20";
        String ppriceLT = "60";
        String query = String.format("%s rdate > %s pprice > %s pprice < %s",
                titleOrSummaryOrText, rdateGT, ppriceGT, ppriceLT);
        Collection<Review> reviews = qc.executeQuery(query);

        Date rdateGTDate = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(rdateGT);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText));
            assertTrue(review.getTimeAsDate().after(rdateGTDate));
            assertTrue(Integer.valueOf(review.getPrice()) > Integer.valueOf(ppriceGT));
            assertTrue(Integer.valueOf(review.getPrice()) < Integer.valueOf(ppriceLT));
        }
    }

    @Test
    public void testCaseInsensitiveQuery() {
        String titleOrSummaryOrText = "CaMeRa";
        String query = String.format("%s", titleOrSummaryOrText);
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);
        for (Review review : reviews) {
            assertTrue(review.getTitle().toLowerCase().contains(titleOrSummaryOrText.toLowerCase()) ||
                    review.getSummary().toLowerCase().contains(titleOrSummaryOrText.toLowerCase()) ||
                    review.getText().toLowerCase().contains(titleOrSummaryOrText.toLowerCase()));
        }
    }


}