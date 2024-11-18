package com.log.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LogReader {
    private final List<String> formatParts;

    /**
     * Constructor to initialize LogReader with a log format.
     *
     * @param formatString the user-defined format string, e.g., "LEVEL,DATE,TIME,MESSAGE"
     */
    public LogReader(String formatString) {
        this.formatParts = Arrays.asList(formatString.split(","));
    }

    /**
     * Reads logs from the given path, which can be a file or a directory.
     *
     * @param path the path to a file or directory containing log files
     * @return a Stream of LogEntry objects
     * @throws IOException if an error occurs while reading files
     */
    public Stream<LogEntry> readLogs(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            // Process all `.log` or `.txt` files in the directory
            return Files.walk(path)
                    .filter(Files::isRegularFile) // Only regular files
                    .filter(p -> p.toString().endsWith(".log") || p.toString().endsWith(".txt")) // Match file extensions
                    .flatMap(this::readFileSafely); // Safely read each file
        } else if (Files.isRegularFile(path)) {
            // Process a single file
            return readFile(path);
        } else {
            throw new IllegalArgumentException("The provided path is neither a file nor a directory: " + path);
        }
    }

    /**
     * Reads a single log file and returns a Stream of LogEntry objects.
     *
     * @param filePath the path to the log file
     * @return a Stream of LogEntry objects
     */
    private Stream<LogEntry> readFile(Path filePath) {
        try {
            return Files.lines(filePath)
                    .map(this::parseLogEntry)
                    .filter(entry -> entry != null); // Filter out any invalid entries
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    /**
     * Safely reads a file and wraps potential exceptions in an empty Stream.
     *
     * @param filePath the path to the file
     * @return a Stream of LogEntry objects or an empty Stream if an error occurs
     */
    private Stream<LogEntry> readFileSafely(Path filePath) {
        try {
            return readFile(filePath);
        } catch (RuntimeException e) {
            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
            return Stream.empty();
        }
    }

    /**
     * Parses a single line of log data into a LogEntry object.
     *
     * @param line the log line
     * @return a LogEntry object or null if parsing fails
     */
    private LogEntry parseLogEntry(String line) {
        try {
            String[] parts = line.trim().split("\\s+");
            if (parts.length < formatParts.size()) {
                throw new IllegalArgumentException("Log line does not match the specified format: " + line);
            }

            LogEntry logEntry = new LogEntry();
            logEntry.setCompleteLog(line);

            for (int i = 0; i < formatParts.size(); i++) {
                switch (formatParts.get(i).toUpperCase()) {
                    case "LEVEL" -> logEntry.setLevel(parts[i]);
                    case "DATE" -> logEntry.setDate(parts[i]);
                    case "TIME" -> logEntry.setTime(parts[i]);
                    case "THREAD_ID" -> logEntry.setThreadId(parts[i]);
                    case "CLASSNAME" -> logEntry.setClassName(parts[i]);
                    default -> System.err.println("Unknown format part: " + formatParts.get(i));
                }
            }

            // Now concatenate everything after the loop to form the message
            StringBuilder messageBuilder = new StringBuilder();
            for (int j = formatParts.size(); j < parts.length; j++) {
                messageBuilder.append(parts[j]);
                if (j < parts.length - 1) {
                    messageBuilder.append(" "); // Add space between message parts
                }
            }
            logEntry.setMessage(messageBuilder.toString());
            return logEntry;
        } catch (Exception e) {
            System.err.println("Failed to parse log entry: " + line);
            return null;
        }
    }
}

