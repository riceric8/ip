//Written by Codex, Model: gpt-5.6-luna low
package rice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/** Tests date parsing performed by Parser. */
public class ParserTest {

    @Test
    void parseDate_validIsoDate_returnsExpectedLocalDate() {
        assertEquals(LocalDate.of(2026, 12, 12), Parser.parseDate("2026-12-12"));
    }

    @Test
    void parseDate_singleDigitMonthOrDay_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.parseDate("2026-2-3"));
    }

    @Test
    void parseDate_impossibleCalendarDate_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.parseDate("2026-02-30"));
    }

    @Test
    void parseDate_wrongDateFormat_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.parseDate("12/12/2026"));
    }

    @Test
    void parseDate_dateWithWhitespace_returnsExpectedLocalDate() {
        assertEquals(LocalDate.of(2026, 12, 12), Parser.parseDate(" 2026-12-12 "));
    }
}
