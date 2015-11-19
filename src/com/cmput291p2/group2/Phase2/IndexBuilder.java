package com.cmput291p2.group2.Phase2;

import com.cmput291p2.group2.Phase1.ReviewFileWriter;
import com.cmput291p2.group2.common.Debugging;
import com.cmput291p2.group2.common.Review;

import java.io.IOException;

/**
 * class IndexBuilder is responsible for creating Berkeley DB indexes
 * using the data files written from Phase 1 by
 * {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
 *
 * The indexes it creates are used by
 * {@link com.cmput291p2.group2.Phase3.QueryCLI}.
 */
public class IndexBuilder implements Runnable {

    public static final String rwIndexFilename = "rw.idx";
    public static final String ptIndexFilename = "pt.idx";
    public static final String rtIndexFilename = "rt.idx";
    public static final String scIndexFilename = "sc.idx";

    private static final String allowScript = "chmod u+x break.pl";
    private static final String makeRW = String.format("cat %s | ./break.pl | db_load -c duplicates=1 -T -t hash %s",
            ReviewFileWriter.reviewFile, rwIndexFilename);
    private static final String makePT = String.format("cat %s | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree %s",
            ReviewFileWriter.pTermFile, ptIndexFilename);
    private static final String makeRT = String.format("cat %s | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree %s",
            ReviewFileWriter.rTermFile, rtIndexFilename);
    private static final String makeSC = String.format("cat %s | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree %s",
            ReviewFileWriter.scoreFile, scIndexFilename);


    public void run() {
        Runtime run = Runtime.getRuntime();
        try {
            run.exec(new String[]{"/bin/sh", "-c", allowScript});
            run.exec(new String[]{"/bin/sh", "-c", makeRW});
            run.exec(new String[]{"/bin/sh", "-c", makePT});
            run.exec(new String[]{"/bin/sh", "-c", makeRT});
            run.exec(new String[]{"/bin/sh", "-c", makeSC});
        }  catch (IOException e) {
            if (Debugging.isEnabled()) {
                 System.err.printf("ShellFailed IOException: %s\n", e.getMessage());
            }
            return;
        }
        System.out.println("Success. Files created:");
        System.out.printf("\t%s\n", rwIndexFilename);
        System.out.printf("\t%s\n", ptIndexFilename);
        System.out.printf("\t%s\n", rtIndexFilename);
        System.out.printf("\t%s\n", scIndexFilename);
    }

}
