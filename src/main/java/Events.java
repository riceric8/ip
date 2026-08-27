import java.time.LocalDate;

public class Events extends Task {
  private LocalDate start;
  private LocalDate end;

  public Events(String task, String start, String end) {
    super(task);
    this.start = Parser.parseDate(start);
    this.end = Parser.parseDate(end);
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
            + Parser.formatDate(this.start) + " to: "
            + Parser.formatDate(this.end) + ")";
  }
}
