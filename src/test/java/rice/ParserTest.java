package rice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

/** Tests parsing and task creation performed by Parser. */
public class ParserTest {

    @Test
    void parseDate_validIsoDate_returnsExpectedLocalDate() {
        assertEquals(LocalDate.of(2026, 12, 12), Parser.parseDate("2026-12-12"));
    }

    @Test
    void parseDate_singleDigitMonthOrDay_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseDate("2026-2-3"));
    }

    @Test
    void parseDate_wrongDateFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseDate("12/12/2026"));
    }

    @Test
    void parseDate_dateWithWhitespace_returnsExpectedLocalDate() {
        assertEquals(LocalDate.of(2026, 12, 12), Parser.parseDate(" 2026-12-12 "));
    }

    @Test
    void parseTasks_validAndInvalidRecords_returnsOnlyValidTasks() {
        Parser parser = new Parser();

        List<Task> tasks = parser.parseTasks(List.of(
                "T|1|read book",
                "D|0|submit report|2026-12-12",
                "E|1|team meeting|2026-12-13|2026-12-14",
                "D|0|broken|not-a-date",
                "X|0|ignored",
                "T|0"));

        assertEquals(3, tasks.size());
        assertEquals("read book", tasks.get(0).getTask());
        assertTrue(tasks.get(0).isDone());
        assertEquals("submit report", tasks.get(1).getTask());
        assertEquals("team meeting", tasks.get(2).getTask());
    }

    @Test
    void createTask_validCommands_buildExpectedTaskTypes() throws RiceException {
        Parser parser = new Parser();

        assertEquals("todo task", parser.createTask("todo todo task").getTask());
        assertEquals("Submit report", parser.createTask("deadline Submit report /by 2026-12-12").getTask());
        assertEquals("Team meeting", parser.createTask("event Team meeting /from 2026-12-13 /to 2026-12-14").getTask());
    }

    @Test
    void createTask_emptyDescription_throwsException() {
        Parser parser = new Parser();

        assertThrows(RiceException.class, () -> parser.createTask("todo "));
        assertThrows(RiceException.class, () -> parser.createTask("deadline "));
    }

    @Test
    void createTask_unknownCommand_throwsException() {
        Parser parser = new Parser();

        assertThrows(RiceException.class, () -> parser.createTask("random task"));
    }

    @Test
    void formatDate_formatsAsExpected() {
        assertEquals("Dec 12 2026", Parser.formatDate(LocalDate.of(2026, 12, 12)));
    }
}
