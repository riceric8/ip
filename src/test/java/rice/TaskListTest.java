package rice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/** Tests keyword searches in TaskList. */
public class TaskListTest {

    @Test
    void find_matchingKeyword_returnsMatchingTasksIgnoringCase() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Buy rice"));
        tasks.add(new Todo("Read book"));

        List<Task> matchingTasks = tasks.find("RICE");

        assertEquals(1, matchingTasks.size());
        assertEquals("Buy rice", matchingTasks.get(0).getTask());
    }

    @Test
    void find_keywordNotFound_returnsEmptyList() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Buy rice"));

        assertEquals(List.of(), tasks.find("homework"));
    }
}
