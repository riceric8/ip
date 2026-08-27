import java.util.ArrayList;
import java.util.List;

/**
 * Converts the text representation of saved tasks into Task objects.
 *
 * expected form is class|status|*. A status of 1 means completed, while 0 means incomplete. *demarcates additional info attached to the 
 * respective task subclass
 */
public class TaskParser {

  
    // Parses all saved task lines into newly created task objects. 
    public List<Task> parseTasks(List<String> savedLines) {
        List<Task> tasks = new ArrayList<>();

        for (String line : savedLines) {
            Task task = parseTask(line);
            if (task != null) {
                tasks.add(task);
            }
        }

        return tasks;
    }

    //Parses one line and reconstructs the appropriate task subtype.
    private Task parseTask(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] fields = line.split("\\|", -1);
        if (fields.length < 3 || !(fields[1].equals("0") || fields[1].equals("1"))) {
            return null;
        }

        boolean completed = fields[1].equals("1"); //1 represents completed task``
        Task task;

        switch (fields[0]) {
        case "T":
            if (fields.length != 3) {
              return null;
            }

            task = new Todo(fields[2]);
            break;

        case "D":
            if (fields.length != 4) {
              return null;
            }
            task = new Deadline(fields[2], fields[3]);
            break;

        case "E":
            if (fields.length != 5) {
              return null;
            }
            task = new Events(fields[2], fields[3], fields[4]);
            break;

        default:
            return null;
        }

        if (completed) {
            task.mark();
        }
        return task;
    }
}
