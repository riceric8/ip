import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;





public class DateParser {

  //Converts a yyyy-MM-dd input into a LocalDate.
  public static LocalDate parseDate(String date) {
    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");

      try {
        LocalDate parsedDate = LocalDate.parse(date, inputFormat);
        return parsedDate;
      } catch (DateTimeParseException ignored) {
        //Do nothing and ignore
      }
    throw new IllegalArgumentException("Invalid date. Use yyyy-MM-dd, for example 2026-12-12.");
  }

  // Converts a LocalDate into the format shown to the user.
  public static String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
  }

}
