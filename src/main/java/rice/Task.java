package rice;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String task;
    private boolean status = false;

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
        this.status = true;
    }

    /**
     * Marks this task as incomplete.
     */
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
