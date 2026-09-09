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

    @Test
    void add_tasksWithDifferentDeadlines_sortsByDeadlineAndPlacesTodosLast() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read book"));
        tasks.add(new Event("Attend meeting", "2026-12-10", "2026-12-12"));
        tasks.add(new Deadline("Submit assignment", "2026-12-01"));

        assertEquals("Submit assignment", tasks.get(0).getTask());
        assertEquals("Attend meeting", tasks.get(1).getTask());
        assertEquals("Read book", tasks.get(2).getTask());
    }
}
