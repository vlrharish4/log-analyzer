package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.stream.Stream;

public interface TerminalCommand {
    void execute(Stream<LogEntry> logEntryStream);
}
