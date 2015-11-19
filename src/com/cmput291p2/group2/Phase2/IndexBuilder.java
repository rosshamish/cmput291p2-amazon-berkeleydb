package com.cmput291p2.group2.Phase2;

import com.cmput291p2.group2.common.Debugging;

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

    private static final String allowScript = "chmod u+x break.pl";
    private static final String makeRW = "cat reviews.txt | ./break.pl | db_load -c duplicates=1 -T -t hash rw.idx";
    private static final String makePT = "cat pterms.txt | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree pt.idx";
    private static final String makeRT = "cat rterms.txt | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree rt.idx";
    private static final String makeSC = "cat scores.txt | sort -u | ./break.pl | db_load -c duplicates=1 -T -t btree sc.idx";


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
        }
    }

}
