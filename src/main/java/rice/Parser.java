package rice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles command parsing, date parsing, and saved task reconstruction.
 */
public class Parser {
    /**
     * Reconstructs task objects from saved delimiter-separated records.
     *
     * @param lines saved task records
     * @return parsed tasks
     */
    public List<Task> parseTasks(List<String> lines) {
        List<Task> result = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split("\\|", -1);
            Task task;
            try {
                task = switch (fields[0]) {
                    case "T" -> fields.length == 3 ? new Todo(fields[2]) : null;
                    case "D" -> fields.length == 4 ? new Deadline(fields[2], fields[3]) : null;
                    case "E" -> fields.length == 5 ? new Event(fields[2], fields[3], fields[4]) : null;
                    default -> null;
                };
            } catch (IllegalArgumentException e) {
                task = null;
            }
            if (task != null) {
                if (fields[1].equals("1")) {
                    task.mark();
                }
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Parses a user-provided date in yyyy-MM-dd format.
     *
     * @param date user input date
     * @return parsed date
     */
    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date. Use yyyy-MM-dd, for example 2026-12-12.");
        }
    }

    /**
     * Formats a date for displaying to the user.
     *
     * @param date date to format
     * @return formatted date
     */
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    /**
     * Creates a task from a complete user command.
     *
     * @param input full command entered by the user
     * @return created task
     * @throws RiceException if the command is missing required details
     */
    public Task createTask(String input) throws RiceException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new RiceException("Task description cannot be empty");
        }
        String body = parts[1];
        return switch (parts[0]) {
            case "todo" -> new Todo(body.trim());
            case "deadline" -> createDeadline(body);
            case "event" -> createEvent(body);
            default -> throw new RiceException("Unknown task command");
        };
    }

    /**
     * Parses the description and date in a deadline command.
     */
    private Task createDeadline(String body) throws RiceException {
        String[] parts = body.split("/", 2);
        if (parts.length != 2 || !parts[1].trim().startsWith("by ")) {
            throw new RiceException("Use: deadline description /by yyyy-MM-dd");
        }
        return new Deadline(parts[0].trim(), parts[1].trim().substring(3).trim());
    }

    /**
     * Parses the description and dates in an event command.
     */
    private Task createEvent(String body) throws RiceException {
        String[] parts = body.split("/", 3);
        if (parts.length != 3 || !parts[1].trim().startsWith("from ") || !parts[2].trim().startsWith("to ")) {
            throw new RiceException("Use: event description /from date /to date");
        }
        return new Event(parts[0].trim(), parts[1].trim().substring(5).trim(), parts[2].trim().substring(3).trim());
    }
}
