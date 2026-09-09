package rice;
import java.time.LocalDate;

/**
 * Represents a task with a description and completion isDone.
 */
public abstract class Task {
    private String task;
    private boolean isDone = false;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param task the task description
     */
    public Task(String task) {
        this.task = task;
    }

    /**
     * Marks this task as complete.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmark() {
        this.isDone = false;
    }

    public String getTask() {
        return this.task;
    }

    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return this.completionMarker() + " " + this.task;
    }

    public String completionMarker() {
        return this.isDone() ? "[X]" : "[ ]";
    }

    public abstract LocalDate getDeadlineDate();
}
