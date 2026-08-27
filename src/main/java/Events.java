import java.time.LocalDate;

public class Events extends Task {

  private LocalDate start;
  private LocalDate end;

  public Events(String task, String start, String end) {
    super(task);
    this.start = DateParser.parseDate(start);
    this.end = DateParser.parseDate(end);
  }

  public String getStart() {
    return this.start.toString();
  }

  public String getEnd() {
    return this.end.toString();
  }

  @Override
  public String toString(){ 
    String completion = "";

    if (super.getStatus()) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return "[E]" + completion + " " + super.getTask() + "(from: "
            + DateParser.formatDate(this.start) + " to: "
            + DateParser.formatDate(this.end) + ")";
  }
}
