public class Events extends Task {

  private String start;
  private String end;

  public Events(String task, String start, String end) {
    super(task);
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString(){ 
    String completion = "";

    if (super.getStatus()) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return "[E]" + completion + " " + super.getTask() + "(form: " + this.start + " to: " +  this.end + ")";
  }
}
