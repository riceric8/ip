public class Todo extends Task {

  public Todo(String task) {
    super(task);
  }

  @Override
  public String toString(){ 
    String completion = "";

    if (super.getStatus()) {
        completion = "[X]";
    } else {
        completion = "[ ]";     
    }

    return "[T]" + completion + " " + super.getTask();
  }
}
