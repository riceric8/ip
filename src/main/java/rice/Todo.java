package rice;
import java.time.LocalDate;

/**
 * Represents a task with no date attached.
 */
public class Todo extends Task {

    /**
     * Creates a todo task from its description.
     *
     * @param task the task description
     */
    public Todo(String task) {
        super(task);
    }

    @Override
    public LocalDate getDeadlineDate() {
        return LocalDate.MAX;
    }

    @Override
    public String toString() {
        return "[T]" + super.completionMarker() + " " + super.getTask();
    }
}
