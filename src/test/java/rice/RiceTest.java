package rice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests the command-processing logic in Rice without exercising the JavaFX GUI. */
public class RiceTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void getResponse_addTodoAndList_returnsTaskInformation() throws Exception {
        Rice rice = createFreshRice();

        String addResponse = rice.getResponse("todo Read book");
        assertTrue(addResponse.contains("Got it. I've added this task:"));
        assertTrue(addResponse.contains("Read book"));

        String listResponse = rice.getResponse("list");
        assertTrue(listResponse.contains("1.[T][ ] Read book"));
    }

    @Test
    void getResponse_markAndDelete_updatesTaskState() throws Exception {
        Rice rice = createFreshRice();
        rice.getResponse("todo Buy rice");

        String markResponse = rice.getResponse("mark 1");
        assertTrue(markResponse.contains("Nice! I've marked this task as done:"));
        assertTrue(markResponse.contains("[X] Buy rice"));

        String deleteResponse = rice.getResponse("delete 1");
        assertTrue(deleteResponse.contains("Removed:"));
        assertTrue(deleteResponse.contains("Buy rice"));

        String listResponse = rice.getResponse("list");
        assertEquals("There are no tasks. Our bowl is empty :(", listResponse);
    }

    @Test
    void getResponse_listOnEmptyTaskList_returnsEmptyListMessage() throws Exception {
        Rice rice = createFreshRice();

        assertEquals("There are no tasks. Our bowl is empty :(", rice.getResponse("list"));
    }

    @Test
    void getResponse_findAndUnknownCommand_produceExpectedOutputs() throws Exception {
        Rice rice = createFreshRice();
        rice.getResponse("todo Read book");
        rice.getResponse("todo Buy rice");

        String findResponse = rice.getResponse("find rice");
        assertTrue(findResponse.contains("Buy rice"));
        assertTrue(findResponse.contains("Read book") || findResponse.contains("Buy rice"));

        String unknownResponse = rice.getResponse("hello");
        assertEquals("I'm sorry, but I dont know what that means :-(", unknownResponse);
    }

    @Test
    void getSuggestion_returnsMatchingAdviceForErrors() throws Exception {
        Rice rice = createFreshRice();

        assertEquals("Try list, find, todo, deadline, event, mark, unmark, or delete.",
                rice.getSuggestion("I'm sorry, but I dont know what that means :-("));
        assertEquals("Try: find <keyword>, e.g. find rice",
                rice.getSuggestion("Please provide a keyword to search for"));
        assertEquals("Use the date format yyyy-MM-DD, e.g. 2026-12-12",
                rice.getSuggestion("Invalid date. Use yyyy-MM-dd, for example 2026-12-12."));
    }

    private Rice createFreshRice() throws Exception {
        Rice rice = new Rice();
        setField(rice, "storage", new Storage(temporaryDirectory.toString(), "tasks.txt"));
        setField(rice, "tasks", new TaskList());
        setField(rice, "hasLoadedSavedTasks", true);
        return rice;
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = Rice.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
