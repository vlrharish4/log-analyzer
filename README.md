# Log Analyzer

A powerful command-line tool for analyzing and processing log files, allowing users to filter logs by various parameters such as log level, date, and keywords. This tool supports configurable log formats and is flexible enough to handle both single log files and directories of logs.

## Features

* **Log Parsing:** Parse log files with different formats (e.g., .log, .txt).

* **Filters:** Apply filters by log level, date range, and search for specific keywords in log messages.

* **Summarize Logs:** Generate a summary of logs, including the count of logs and log levels.

* **Print Logs:** Print the logs after applying filters.

* **Customizable Format:** Define the format for parsing logs (e.g., LEVEL, DATE, TIME, THREAD_ID, CLASSNAME).

* **Directory Support:** Process directories of log files.

## Installation

### Prerequisites

* Java 21 (or later) installed on your system.
* Maven for building and running the application.

### Building from Source

1. Clone the repository: `git clone https://github.com/yourusername/log-analyzer.git`
2. Navigate to the project directory: `cd log-analyzer`
3. Build the project using Maven: `mvn clean package`
4. The application JAR file will be generated in the target directory: `target/log-analyzer-1.0-SNAPSHOT.jar`

## Usage

### Running the Application

You can run the application via the command line using the following syntax:
`java -jar target/log-analyzer-1.0-SNAPSHOT.jar [options]`

### Command-Line Options

* `-p` or `--path`	
**Description:** Path to the log file or directory (required).

* `-f` or `--format`
**Description:** Log format (comma-separated). Example: LEVEL,DATE,TIME,MESSAGE (required).

* `-s` or `--summary`
**Description:** Generate a summary of the log files.

* `-l` or `--level`
**Description:** Filter logs by level (e.g., INFO, WARN, ERROR, DEBUG).

* `-k` or `--keyword`
**Description:** Search for a specific keyword in log messages.

* `-d` or `--date`
**Description:** Filter logs by date range (format: yyyy-MM-dd).

* `-V` or `--version`
**Description:** Display the version of the application.

* `-h` or `--help`
**Description:** Show help information for the application.

### Example Commands

1. Filter logs by level (INFO) and print a summary: 
`java -jar target/log-analyzer-1.0-SNAPSHOT.jar -p /path/to/logs -f LEVEL,DATE,TIME -l INFO -s`

2. Search for logs containing a keyword and print them: 
`java -jar target/log-analyzer-1.0-SNAPSHOT.jar -p /path/to/logs -f LEVEL,DATE,TIME -k "error" -V`

3. Analyze logs in a directory: 
`java -jar target/log-analyzer-1.0-SNAPSHOT.jar -p /path/to/log/directory -f LEVEL,DATE,TIME -l ERROR -k "failed"`

4. Generate a log summary with specific date range: 
`java -jar target/log-analyzer-1.0-SNAPSHOT.jar -p /path/to/logs -f LEVEL,DATE,TIME,MESSAGE -d "2023-01-01,2023-12-31" -s`

### How It Works

1. **LogReader:** Reads the log files or directories and parses them according to the specified format.
2. **Commands:** The application supports multiple commands such as:
   * **LogLevelFilterCommand:** Filters logs based on the log level (e.g., INFO, DEBUG).
   * **DateFilterCommand:** Filters logs by a date range.
   * **KeywordSearchCommand:** Filters logs containing a specific keyword.
   * **SummaryCommand:** Summarizes the logs (e.g., count of logs, log levels).
   * **PrintLogsCommand:** Prints the filtered logs.
3. **LogEntry:** Represents a single log entry, with properties such as level, date, time, message, etc.

### Log Format

The log format must be defined as a comma-separated string, specifying the order of the log components. Example formats:
* **LEVEL,DATE,TIME**
* **LEVEL,DATE,TIME,THREAD_ID,CLASSNAME**

### Customizable Log Parsing

You can customize how logs are parsed by adjusting the log format string. The log entries are parsed according to the specified format, allowing flexibility in how the logs are structured and processed.

## Contributing

1. Fork the repository and create your branch (git checkout -b feature-xyz).
2. Commit your changes (git commit -am 'Add new feature').
3. Push to your branch (git push origin feature-xyz).
4. Create a new pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.