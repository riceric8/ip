package rice;

import java.time.LocalDate;

/**
 * Represents a task that takes place between two dates.
 */
public class Event extends Task {
    private LocalDate start;
    private LocalDate end;

    /**
     * Creates an event task from its description, start date, and end date.
     *
     * @param task the task description
     * @param start the start date in yyyy-MM-dd format
     * @param end the end date in yyyy-MM-dd format
     */
    public Event(String task, String start, String end) {
        super(task);
        this.start = Parser.parseDate(start);
        this.end = Parser.parseDate(end);
    }

    public String getStart() {
        return this.start.toString();
    }

    public String getEnd() {
        return this.end.toString();
    }

    @Override
    public LocalDate getDeadlineDate() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.completionMarker() + " " + super.getTask() + "(from: "
                + Parser.formatDate(this.start) + " to: "
                + Parser.formatDate(this.end) + ")";
    }
}
