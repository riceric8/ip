import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

/*
Handles all disk writing of edited List from RiceBot
*/

public class Storage {
  private final Path filePath;

  public Storage(String... pathElements) {
    this.filePath = Paths.get(pathElements[0], java.util.Arrays.copyOfRange(pathElements, 1, pathElements.length));
  }

  public Path getPath(){
    return this.filePath;
  }

  public void save(List<Task> currList){
    Path parentDir = this.filePath.getParent();
    try {
      //Handles the case if the data dir is not written into local memory
      if (parentDir != null) {
          Files.createDirectories(parentDir);
      }
      //overwrite local stored checklist with currList
      List<String> convertedListString = new ArrayList<>();
      for (Task task : currList) {
        convertedListString.add(taskToListString(task));
      }
      
       Files.write(this.filePath, convertedListString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.err.println("Failed to load: " + e.getMessage());
    }
  }

  /** Converts one task into the delimiter-separated storage format. */
  private String taskToListString(Task task) {
    String completion = task.getStatus() ? "1" : "0";

    return switch (task) {
      case Todo todo -> "T|" + completion + "|" + todo.getTask();
      case Deadline deadline -> "D|" + completion + "|" + deadline.getTask()
              + "|" + deadline.getDeadline();
      case Events event -> "E|" + completion + "|" + event.getTask()
              + "|" + event.getStart() + "|" + event.getEnd();
      case null -> throw new IllegalArgumentException("Cannot save a null task");
      default -> throw new IllegalArgumentException("Unknown task type");
    };
  }


  public List<String> load(){
    try {
      if (Files.exists(this.filePath)) {
        List<String> prev_session_list = Files.readAllLines(this.filePath);
        System.out.println(String.join("\n", prev_session_list));
        return prev_session_list;
      } else {
        System.out.println("You have no tasks to display !!");
      }
    } catch (IOException e) {
      System.err.println("Failed to load checklist: " + e.getMessage());
    }
    return new ArrayList<>();
  }
}
