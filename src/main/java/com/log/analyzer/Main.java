package com.log.analyzer;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar log-analyzer.jar <file|directory>");
            return;
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

        if (!inputFile.exists()) {
            System.err.println("Error: The specified path does not exist.");
            return;
        }

        System.out.println("Starting Log Analyzer...");

        try {
            if (inputFile.isFile()) {
                System.out.println("Processing file: " + inputPath);
                processFile(inputFile);
            } else if (inputFile.isDirectory()) {
                System.out.println("Processing directory: " + inputPath);
                processDirectory(inputFile);
            } else {
                System.err.println("Error: Invalid input. Please provide a valid file or directory.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void processFile(File file) throws IOException {
        LogParser parser = new LogParser(file.getAbsolutePath());
        parser.parse();
    }

    private static void processDirectory(File directory) throws IOException {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".log") || name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No log or text files found in the directory.");
            return;
        }

        for (File file : files) {
            System.out.println("Processing file: " + file.getName());
            processFile(file);
        }
    }
}
