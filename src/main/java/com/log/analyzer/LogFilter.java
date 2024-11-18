package com.log.analyzer;

import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    public List<String> filterByLevel(List<String> logs, String level) {
        List<String> filteredLogs = new ArrayList<>();
        for (String log : logs) {
            if (log.contains(level)) {
                filteredLogs.add(log);
            }
        }
        return filteredLogs;
    }
}
