package com.log.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogStats {
    public Map<String, Integer> generateStats(List<String> logs) {
        Map<String, Integer> stats = new HashMap<>();
        for (String log : logs) {
            if (log.contains("ERROR")) stats.merge("ERROR", 1, Integer::sum);
            if (log.contains("WARN")) stats.merge("WARN", 1, Integer::sum);
            if (log.contains("INFO")) stats.merge("INFO", 1, Integer::sum);
        }
        return stats;
    }
}
