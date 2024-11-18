package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SummaryCommand implements TerminalCommand {

    @Override
    public void execute(Stream<LogEntry> logs) {
        List<LogEntry> logList = logs.toList();

        if (logList.isEmpty()) {
            System.out.println("No logs to summarize.");
            return;
        }

        // Total number of logs
        int totalLogs = logList.size();

        // Count logs by log level
        Map<String, Long> levelCounts = logList.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getLevel,
                        Collectors.counting()
                ));

        // Count unique threadIds
        Set<String> uniqueThreads = logList.stream()
                .map(LogEntry::getThreadId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Count unique classNames
        Set<String> uniqueClasses = logList.stream()
                .map(LogEntry::getClassName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Determine date-time range
        Optional<String> minTimestamp = logList.stream()
                .map(this::combineDateTime)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder());

        Optional<String> maxTimestamp = logList.stream()
                .map(this::combineDateTime)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder());

        // Print summary
        System.out.println("==== Log Summary ====");
        System.out.println("Total logs: " + totalLogs);

        System.out.println("Logs by level:");
        levelCounts.forEach((level, count) ->
                System.out.println("  " + level + ": " + count)
        );

        System.out.println("Unique threads: " + uniqueThreads.size());
        uniqueThreads.forEach(thread ->
                System.out.println("  Thread: " + thread)
        );

        System.out.println("Unique classes: " + uniqueClasses.size());
        uniqueClasses.forEach(className ->
                System.out.println("  Class: " + className)
        );

        System.out.println("Date-Time Range:");
        System.out.println("  Start: " + minTimestamp.orElse("N/A"));
        System.out.println("  End: " + maxTimestamp.orElse("N/A"));
    }

    private String combineDateTime(LogEntry logEntry) {
        String date = logEntry.getDate();
        String time = logEntry.getTime();

        // Combine date and time into a single string, handling nulls
        if (date != null && time != null) {
            return date + " " + time;
        } else if (date != null) {
            return date; // Only date is present
        } else if (time != null) {
            return time; // Only time is present
        }
        return null; // Neither date nor time is present
    }
}
