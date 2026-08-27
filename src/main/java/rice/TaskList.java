package rice;
import java.util.List;
import java.util.ArrayList;

public class TaskList {
  private List<Task> taskList;

  public TaskList() {
    this.taskList = new ArrayList<>();
  }
  /** Adds a task to the list. */
  public void add(Task task) { 
    taskList.add(task); 
  }
  /** Removes and returns the task at the zero-based index. */
  public Task remove(int index) { 
    return taskList.remove(index); 
  }
  /** Returns the task at the zero-based index. */
  public Task get(int index) { 
    return taskList.get(index); 
  }
  /** Returns the number of tasks currently stored. */
  public int size() { 
    return taskList.size(); 
  }
  /** Returns the underlying tasks for persistence. */
  public List<Task> getTasks() { 
    return taskList; 
  }
  /** Adds all supplied tasks to this list. */
  public void addAll(List<Task> tasks) { 
    taskList.addAll(tasks); 
  }

  /** Marks the task at the zero-based index as complete. */
  public void mark(int index) { 
    get(index).mark(); 
  }
  /** Marks the task at the zero-based index as incomplete. */
  public void unmark(int index) { 
    get(index).unmark(); 
  }
}
