package rice;

import java.time.LocalDate;


public class Deadline extends Task {
  private LocalDate deadline;

  public Deadline(String task, String deadline) {
    super(task);
    this.deadline = Parser.parseDate(deadline);
  }

  public String getDeadline() {
    return this.deadline.toString();
  }

  @Override
  public String toString(){ 
    String completion = "";

    if (super.getStatus()) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return "[D]" + completion + " " + super.getTask() + "(by: "
            + Parser.formatDate(this.deadline) + ")";
  }
}
