package com.log.analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogParser {
    private final String filePath;

    public LogParser(String filePath) {
        this.filePath = filePath;
    }

    public void parse() throws IOException {
        System.out.println("Parsing log file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                processLogLine(line);
            }
        }
    }

    private void processLogLine(String line) {
        System.out.println("Processing log line: " + line);
        // Additional parsing logic can be added here
    }
}
