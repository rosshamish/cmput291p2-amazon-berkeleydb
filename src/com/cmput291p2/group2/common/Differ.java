package com.cmput291p2.group2.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class Differ provides utility methods to determine if two
 * String-like objects are the same or not.
 */
public class Differ {

    public static Boolean filesAreSame(String expectedFilename, String actualFilename) throws IOException {
        Process diff = Runtime.getRuntime().exec(new String[]{"diff", expectedFilename, actualFilename});
        try {
            return (diff.waitFor() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
