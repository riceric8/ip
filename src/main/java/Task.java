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
