package com.cmput291p2.group2.Phase2;

import com.cmput291p2.group2.Phase1.ReviewFileWriter;
import com.cmput291p2.group2.common.Debugging;

import java.io.IOException;

/**
 * class IndexBuilder is responsible for creating Berkeley DB indexes
 * using the data files written from Phase 1 by
 * {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
 * <p/>
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
            Process allow = run.exec(new String[]{"/bin/sh", "-c", allowScript});
            allow.waitFor();
            Process rw = run.exec(new String[]{"/bin/sh", "-c", makeRW});
            Process pt = run.exec(new String[]{"/bin/sh", "-c", makePT});
            Process rt =run.exec(new String[]{"/bin/sh", "-c", makeRT});
            Process sc = run.exec(new String[]{"/bin/sh", "-c", makeSC});
            rw.waitFor();
            pt.waitFor();
            rt.waitFor();
            sc.waitFor();
        } catch (Exception e) {
            if (Debugging.isEnabled()) {
                System.err.printf("ShellFailed Exception: %s\n", e.getMessage());
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
