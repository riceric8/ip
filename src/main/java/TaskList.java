import java.util.List;
import java.util.ArrayList;

public class TaskList {
  private List<Task> taskList;

  public TaskList() {
    this.taskList = new ArrayList<>();
  }
  public void add(Task task) { 
    taskList.add(task); 
  }
  public Task remove(int index) { 
    return taskList.remove(index); 
  }
  public Task get(int index) { 
    return taskList.get(index); 
  }
  public int size() { 
    return taskList.size(); 
  }
  public List<Task> getTasks() { 
    return taskList; 
  }
  public void addAll(List<Task> tasks) { 
    taskList.addAll(tasks); 
  }

  public void mark(int index) { 
    get(index).mark(); 
  }
  public void unmark(int index) { 
    get(index).unmark(); 
  }
}
