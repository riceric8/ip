package rice;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/* Handles all parsing within the Programme
 * DateParsng and TaskParsing
 */

public class Parser {
  /** Reconstructs task objects from saved delimiter-separated records. */
  public List<Task> parseTasks(List<String> lines) {
    List<Task> result = new ArrayList<>();
    for (String line : lines) {
      String[] f = line.split("\\|", -1);
      Task task;
      try {
        task = switch (f[0]) {
          case "T" -> f.length == 3 ? new Todo(f[2]) : null; 
          case "D" -> f.length == 4 ? new Deadline(f[2], f[3]) : null;
          case "E" -> f.length == 5 ? new Events(f[2], f[3], f[4]) : null;
          default -> null;
        };
      } catch (IllegalArgumentException e) { 
        task = null; 
      }
      if (task != null) { 
        if (f[1].equals("1")) task.mark(); result.add(task); 
      }
    }
    return result;
  }

 /**
  *Parses an input string in the ascribed input format into a valid date
  * @param {String} date - User input date
  */
  public static LocalDate parseDate(String date) {
    try { 
      return LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("uuuu-MM-dd")); 
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date. Use yyyy-MM-dd, for example 2026-12-12.");
    }
  }

  /** Formats a date for displaying to the user. */
  public static String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
  }

  /** Creates a task from a complete user command. */
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

  /** Parses the description and date in a deadline command. */
  private Task createDeadline(String body) throws RiceException {
    String[] p = body.split("/", 2);
    if (p.length != 2 || !p[1].trim().startsWith("by ")) {
      throw new RiceException("Use: deadline description /by yyyy-MM-dd");
    }
    return new Deadline(p[0].trim(), p[1].trim().substring(3).trim());
  }

  /** Parses the description and dates in an event command. */
  private Task createEvent(String body) throws RiceException {
    String[] p = body.split("/", 3);
    if (p.length != 3 || !p[1].trim().startsWith("from ") || !p[2].trim().startsWith("to ")) {
      throw new RiceException("Use: event description /from date /to date");
    }
    return new Events(p[0].trim(), p[1].trim().substring(5).trim(), p[2].trim().substring(3).trim());
  }
}
