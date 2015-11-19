package com.cmput291p2.group2.Phase1;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            new ReviewFileWriter().run();
        } else if (args.length == 1) {
            new ReviewFileWriter().run(args[0]);
        } else {
            Usage();
        }
    }

    private static void Usage() {
        System.out.println("Invalid number of arguments.");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("1) java -cp build:. com.cmput291p2.group2.Phase1.Main <filename>");
        System.out.println("\t- Where <filename> is the input filename.");
        System.out.println("\t- Phase1.Main will read the file of name <filename> in the working directory.");
        System.out.println();
        System.out.println("2) cat <filename> | java -cp build:. com.cmput291p2.group2.Phase1.Main");
        System.out.println("\t- Where <filename> is the input filename.");
        System.out.println("\t- Phase1.Main will read stdin through the pipe.");
    }

}
