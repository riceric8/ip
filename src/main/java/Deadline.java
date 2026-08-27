public class Deadline extends Task {

  private String deadline;

  public Deadline(String task, String deadline) {
    super(task);
    this.deadline = deadline;
  }

  public String getDeadline() {
    return this.deadline;
  }

  @Override
  public String toString(){ 
    String completion = "";

    if (super.getStatus()) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return "[D]" + completion + " " + super.getTask() + "(by: " + this.deadline + ")";
  }
}
