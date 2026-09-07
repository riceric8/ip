package rice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stores and manages the current list of tasks.
 */
public class TaskList {
    private List<Task> taskList;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Removes and returns the task at the zero-based index.
     *
     * @param index zero-based task index
     * @return removed task
     */
    public Task remove(int index) {
        assert index > 0 && index < taskList.size();
        return taskList.remove(index);
    }

    public Task get(int index) {
        assert index >= 0 && index < taskList.size();
        return taskList.get(index);
    }

    public int size() {
        return taskList.size();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    /**
     * Adds all supplied tasks to this list.
     *
     * @param tasks tasks to add
     */
    public void addAll(List<Task> tasks) {
        taskList.addAll(tasks);
    }

    /**
     * Returns tasks whose descriptions contain the keyword, ignoring case.
     *
     * @param keyword search keyword
     * @return matching tasks
     */
    public List<Task> find(String keyword) {
        String searchTerm = keyword.toLowerCase();

        return taskList.stream()
                       .filter(task -> task.getTask().toLowerCase().contains(searchTerm))
                       .toList();
    }

    /**
     * Marks the task at the zero-based index as complete.
     *
     * @param index zero-based task index
     */
    public void mark(int index) {
        get(index).mark();
    }

    /**
     * Marks the task at the zero-based index as incomplete.
     *
     * @param index zero-based task index
     */
    public void unmark(int index) {
        get(index).unmark();
    }
}
