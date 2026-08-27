package rice;
public class Task {

  private String task;
  private boolean status = false;
 
  public Task(String task) {
      this.task = task;
  }

  public void mark() {
    this.status = true;
  }

  public void unmark() {
    this.status = false;
  }

  public String getTask() {
    return this.task;
  }

  public boolean getStatus() {
    return this.status;
  }

  @Override
  public String toString() {
    String completion = "";

    if (this.status) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return completion + " " + this.task;
  }
}
