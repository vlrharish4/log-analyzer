package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.stream.Stream;

public class PrintLogsCommands implements TerminalCommand {

    @Override
    public void execute(Stream<LogEntry> logs) {
        logs.forEach(log -> {
            System.out.println(log.toString()); // Print each LogEntry to the console
        });
    }
}
