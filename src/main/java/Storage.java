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

  public void save(List<Task> currList){
    Path parentDir = this.filePath.getParent();
    try {
      //Handles the case if the data dir is not written into local memory
      if (parentDir != null && Files.notExists(this.filePath)) {
          Files.createDirectory(parentDir); //create parentDir
      }
      //overwrite local stored checklist with currList
      List<String> convertedListString= new ArrayList<>();
      for (int i = 0; i < currList.size(); i += 1) {
          convertedListString.add(String.format("%d.%s", i + 1, currList.get(i)));
        }
      Files.write(this.filePath, convertedListString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.err.println("Failed to load: " + e.getMessage());
    }
  }


  public List<String> load(){
    try {
      if (Files.exists(this.filePath)) {
        List<String> prev_session_list = Files.readAllLines(this.filePath);
        Files.write(this.filePath, prev_session_list, StandardOpenOption.WRITE);
        System.out.println(String.join("\n", prev_session_list));
      } else {
        System.out.println("You have no tasks to display !!");
      }
    } catch (IOException e) {
      System.err.println("Failed to load checklist: " + e.getMessage());
    }
    return new ArrayList<>();
  }
}
