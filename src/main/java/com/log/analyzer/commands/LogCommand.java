package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.stream.Stream;

public interface LogCommand {
    Stream<LogEntry> execute(Stream<LogEntry> logs);
}
