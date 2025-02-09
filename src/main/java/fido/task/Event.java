package fido.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import fido.exception.FidoException;
import fido.storage.Parser;

/**
 * The {@code Event} class represents a {@link Task} that occurs over
 * a specific time period, it can be marked as done or not done.
 * It extends the abstract {@link Task} class.
 */
public class Event extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a new {@code Event} task with the specified name, start date,
     * and end date.
     * The dates are provided as a {@link String} in the
     * format "MMM dd yyy" (e.g., "May 20 2024").
     *
     * @param name The name or description of the task.
     * @param startDate The start date of the task as a {@code String} in "MMM dd yyyy" format.
     * @param endDate The start date of the task as a {@code String} in "MMM dd yyyy" format.
     */
    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = LocalDate.parse(startDate, Parser.DATE_OUTPUT_FORMAT);
        this.endDate = LocalDate.parse(endDate, Parser.DATE_OUTPUT_FORMAT);
    }

    /**
     * Constructs a new {@code Event} task with the specified name, start date,
     * and end date.
     * The dates are provided as a {@link LocalDate} in the
     * format "yyyy-MM-dd" (e.g., "2024-04-20").
     *
     * @param name The name or description of the task.
     * @param startDate The start date of the task as a {@code LocalDate} in "yyyy-MM-dd" format.
     * @param endDate The start date of the task as a {@code LocalDate} in "yyyy-MM-dd" format.
     */
    public Event(String name, LocalDate startDate, LocalDate endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private Event(String name, boolean isDone, LocalDate startDate, LocalDate endDate) {
        super(name, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Creates a new {@code Event} instance from raw input.
     * The raw input should include the task name, and dates in "yyyy-MM-dd" format,
     * separated by "/from" and "to"
     * (e.g., sales /from 2024-05-20 /to 2024-06-20).
     *
     * @param rawInput The raw input string containing the task name, start date, and end date.
     *                 Expected format: "Task name /from yyyy-MM-dd /to yyyy-MM-dd".
     * @return A new {@code Event} instance based on the provided raw input.
     * @throws FidoException If the raw input is malformed.
     *                       - {@link FidoException.ErrorType.EVENT_EMPTY_DESCRIPTION}
     *                                if the input is missing required parts.
     *                       - {@link FidoException.ErrorType.EVENT_EMPTY_DATE}
     *                                if the dates are missing.
     *                       - {@link FidoException.ErrorType.EVENT_MISSING_DATE}
     *                                if only one date is provided.
     *                       - {@link FidoException.ErrorType.NOT_VALID_DATE}
     *                                if the dates format are invalid.
     */
    public static Event of(String rawInput) throws FidoException {
        String[] ins = rawInput.split("(/from|/to)");
        if (ins.length == 0) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DESCRIPTION);
        } else if (ins.length == 1) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DATE);
        } else if (ins.length < 3) {
            throw new FidoException(FidoException.ErrorType.EVENT_MISSING_DATE);
        }
        assert ins.length >= 3 : "Event task should be 3 arguments after spliting";
        try {
            return new Event(ins[0].trim(),
                    LocalDate.parse(ins[1].trim()), LocalDate.parse(ins[2].trim()));
        } catch (DateTimeParseException e) {
            throw new FidoException(FidoException.ErrorType.NOT_VALID_DATE);
        }
    }

    /**
     * Determine if the current date is after or equal to the starting date,
     * or exactly one day before the starting date.
     *
     * @return {@code true} if today's date is within one day before
     *         or on/after the starting date; {@code false} otherwise.
     */
    @Override
    public boolean isDue() {
        return !super.getIsDone() && !LocalDate.now().isBefore(this.startDate.minusDays(1));
    }

    /**
     * Returns a string representation of this {@code Event} task in a format
     * suitable for file storage.
     * The format includes the task type, task status, task name, start date, and end date.
     *
     * @return A {@code String} representing the task in file format.
     */
    @Override
    public String fileFormat() {
        return String.format("E%s%s%s%s%s%s%s%s",
                Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(),
                Parser.DIVIDER, this.startDate.format(Parser.DATE_OUTPUT_FORMAT),
                Parser.DIVIDER, this.endDate.format(Parser.DATE_OUTPUT_FORMAT));
    }

    /**
     * Marks the {@code Event} task as done.
     * This method returns a new {@code Event} instance with the updated status.
     *
     * @return A new {@code Event} instance marked as done.
     */
    @Override
    public Event mark() {
        return new Event(super.getName(), true, this.startDate, this.endDate);
    }

    /**
     * Marks the {@code Event} task as not done.
     * This method returns a new {@code Event} instance with the updated status.
     *
     * @return A new {@code Event} instance marked as not done.
     */
    @Override
    public Event unmark() {
        return new Event(super.getName(), false, this.startDate, this.endDate);
    }

    /**
     * Returns a string representation of this {@code Event} task in the
     * format "[E][ ] Task Name (from: MMM dd yyyy to: MMM dd yyyy)".
     * - "[E]" denotes it is a {@code Event} task.
     * - "[ ]" or "[X]" indicates whether the task is not done or done respectively.
     *
     * @return A formatted {@code String} representing the task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + String.format(" (from: %s to: %s)",
                        this.startDate.format(Parser.DATE_OUTPUT_FORMAT),
                        this.endDate.format(Parser.DATE_OUTPUT_FORMAT));
    }
}
