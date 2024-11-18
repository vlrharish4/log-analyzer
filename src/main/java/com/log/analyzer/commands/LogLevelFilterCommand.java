package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.stream.Stream;

public class LogLevelFilterCommand implements LogCommand {

    private final String logLevel;

    public LogLevelFilterCommand(String logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public Stream<LogEntry> execute(Stream<LogEntry> logs) {
        return logs.filter(log -> log.getLevel().equalsIgnoreCase(logLevel));
    }
}
