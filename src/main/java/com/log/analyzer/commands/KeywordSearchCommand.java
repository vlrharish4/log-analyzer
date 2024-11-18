package com.log.analyzer.commands;

import com.log.analyzer.LogEntry;

import java.util.stream.Stream;

public class KeywordSearchCommand implements LogCommand {

    private final String keyword;

    public KeywordSearchCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Stream<LogEntry> execute(Stream<LogEntry> logs) {
        String word = this.keyword.toLowerCase();
        return logs.filter(log -> log.getMessage().toLowerCase().contains(word));
    }
}
