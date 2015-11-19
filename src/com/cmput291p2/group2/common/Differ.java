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
        List<String> expectedList = fileToStringList(expectedFilename);
        List<String> actualList = fileToStringList(actualFilename);
        return stringListsAreSame(expectedList, actualList);
    }

    private static List<String> fileToStringList(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
        List<String> list = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static Boolean stringListsAreSame(List<String> expected, List<String> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }

        for (int i = 0; i < expected.size(); i++) {
            if (stringsAreDifferent(expected.get(i), actual.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static Boolean stringsAreDifferent(String expected, String actual) {
        return !stringsAreSame(expected, actual);
    }

    public static Boolean stringsAreSame(String expected, String actual) {
        return expected.equals(actual);
    }
}
