package com.log.analyzer;

public class LogEntry {
    private String message;
    private String level;
    private String time;
    private String date;
    private String threadId;
    private String className;
    private String completeLog;

    public LogEntry() {}

    // Getters
    public String getMessage() {
        return message;
    }

    public String getLevel() {
        return level;
    }

    public String getTime() {
        return time;
    }

    public String getDate() { return date; }

    public void setLevel(String level) { this.level = level; }

    public void setTime(String time) { this.time = time; }

    public void setDate(String date) { this.date = date; }

    public void setMessage(String message) { this.message = message; }

    public void setThreadId(String threadId) { this.threadId = threadId; }

    public String getThreadId() { return this.threadId; }

    public void setClassName(String className) { this.className = className; }

    public String getClassName() { return this.className; }

    public String getCompleteLog() { return this.completeLog; }

    public void setCompleteLog(String completeLog) { this.completeLog = completeLog; }

    @Override
    public String toString() {
        return getCompleteLog();
    }
}
