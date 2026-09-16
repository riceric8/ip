package rice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void remove_firstTask_removesAtZeroIndex() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Buy rice"));
        tasks.add(new Todo("Read book"));

        Task removed = tasks.remove(0);

        assertEquals("Buy rice", removed.getTask());
        assertEquals(1, tasks.size());
        assertEquals("Read book", tasks.get(0).getTask());
    }

    @Test
    void markAndUnmark_changesCompletionState() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read book"));

        tasks.mark(0);
        assertTrue(tasks.get(0).isDone());

        tasks.unmark(0);
        assertFalse(tasks.get(0).isDone());
    }

    @Test
    void addAll_addsAndSortsAllTasks() {
        TaskList tasks = new TaskList();
        tasks.addAll(List.of(
                new Todo("Read book"),
                new Deadline("Submit assignment", "2026-12-01"),
                new Event("Attend meeting", "2026-12-10", "2026-12-12")));

        assertEquals("Submit assignment", tasks.get(0).getTask());
        assertEquals("Attend meeting", tasks.get(1).getTask());
        assertEquals("Read book", tasks.get(2).getTask());
    }
}
