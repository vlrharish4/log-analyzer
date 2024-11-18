package com.log.analyzer;

import com.log.analyzer.commands.*;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@CommandLine.Command(name = "log-analyzer", mixinStandardHelpOptions = true, version = "1.0", description = "Analyze and process log files.")
public class Main implements Runnable {

    @CommandLine.Option(names = {"-p", "--path"}, description = "Path to the log file or directory", required = true)
    private String path;

    @CommandLine.Option(names = {"-f", "--format"}, description = "Log format (comma-separated). Example: LEVEL,DATE,TIME,THREAD_ID", required = true)
    private String format;

    @CommandLine.Option(names = {"-s", "--summary"}, description = "Generate a summary of the log files")
    private boolean summary;

    @CommandLine.Option(names = {"-l", "--level"}, description = "Filter logs by level (INFO, WARN, ERROR, DEBUG)")
    private String level;

    @CommandLine.Option(names = {"-k", "--keyword"}, description = "Keyword to search for in log messages")
    private String keyword;

    @CommandLine.Option(names = {"--start-date"}, description = "Start date for log entries (YYYY-MM-DD)")
    private String startDate;

    @CommandLine.Option(names = {"--end-date"}, description = "End date for log entries (YYYY-MM-DD)")
    private String endDate;

    public static void main(String[] args) {
        CommandLine.run(new Main(), args); // Run Picocli with the Main class
    }

    @Override
    public void run() {
        // Read logs from file/directory
        LogReader logReader = new LogReader(format);
        Path logPath = Paths.get(path);
        Stream<LogEntry> logs = null;
        try {
            logs = logReader.readLogs(logPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<LogCommand> intermediateCommands = new ArrayList<>();

        // Set up the command pipeline for intermediate operations
        if (level != null) {
           intermediateCommands.add(new LogLevelFilterCommand(level));
        }
        // Apply date filter if startDate and endDate are provided
        if (startDate != null || endDate != null) {
            intermediateCommands.add(new DateFilterCommand(startDate, endDate));
        }

        // Apply keyword search if keyword is provided
        if (keyword != null) {
           intermediateCommands.add(new KeywordSearchCommand(keyword));
        }

        // Execute Intermediate commands
        for (LogCommand command : intermediateCommands) {
            logs = command.execute(logs);
        }

        // Execute terminal command
        if (summary) {
            new SummaryCommand().execute(logs); // Print summary of the logs
        } else {
            new PrintLogsCommands().execute(logs); // Print logs
        }
    }
}
