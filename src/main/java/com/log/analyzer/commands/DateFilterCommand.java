package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class DateFilterCommand implements LogCommand {

    private final LocalDate startDate;
    private final LocalDate endDate;

    // Constructor that accepts start and end date as strings
    public DateFilterCommand(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = (startDate != null) ? LocalDate.parse(startDate, formatter) : null;
        this.endDate = (endDate != null) ? LocalDate.parse(endDate, formatter) : null;
    }

    @Override
    public Stream<LogEntry> execute(Stream<LogEntry> logs) {
        return logs.filter(logEntry -> {
            // Parse the log date from the log entry
            LocalDate logDate = LocalDate.parse(logEntry.getDate());

            // Check if the log date is within the startDate and endDate range
            boolean isAfterStart = (startDate == null || !logDate.isBefore(startDate));
            boolean isBeforeEnd = (endDate == null || !logDate.isAfter(endDate));
            return isAfterStart && isBeforeEnd;
        });
    }
}
