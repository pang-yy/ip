package fido.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import fido.exception.FidoException;
import fido.storage.Parser;

/**
 * The {@code Deadline} class represents a {@link Task} that has a specific deadline,
 * it can be marked as done or not done.
 * It extends the abstract {@link Task} class.
 */
public class Deadline extends Task {
    private final LocalDate date;

    /**
     * Constructs a new {@code Deadline} task with the specified name and due date.
     * The date is provided as a {@link String} in the
     * format "MMM dd yyy" (e.g., "May 20 2024").
     *
     * @param name The name or description of the task.
     * @param date The due date of the task as a {@code String} in "MMM dd yyyy" format.
     */
    public Deadline(String name, String date) {
        super(name);
        this.date = LocalDate.parse(date, Parser.DATE_OUTPUT_FORMAT);
    }

    /**
     * Constructs a new {@code Deadline} task with the specified name and due date.
     * The date is provided as a {@link LocalDate} in the
     * format "yyyy-MM-dd" (e.g., "2024-05-20").
     *
     * @param name The name or description of the task.
     * @param date The due date of the task as a {@code LocalDate} in "yyyy-MM-dd" format.
     */
    public Deadline(String name, LocalDate date) {
        super(name);
        this.date = date;
    }

    private Deadline(String name, boolean isDone, LocalDate date) {
        super(name, isDone);
        this.date = date;
    }

    /**
     * Creates a new {@code Deadline} instance from raw input.
     * The raw input should include the task name, and due date in "yyyy-MM-dd" format,
     * separated by "/by" (e.g., do assignment /by 2024-05-20).
     *
     * @param rawInput The raw input string containing the task name and due date.
     *                 Expected format: "Task name /by yyyy-MM-dd".
     * @return A new {@code Deadline} instance based on the provided raw input.
     * @throws FidoException If the raw input is malformed.
     *                       - {@link FidoException.ErrorType.DEADLINE_EMPTY_DESCRIPTION}
     *                                if the input is missing required parts.
     *                       - {@link FidoException.ErrorType.DEADLINE_EMPTY_DATE}
     *                                if the due date is missing.
     *                       - {@link FidoException.ErrorType.NOT_VALID_DATE}
     *                                if the due date format is invalid.
     */
    public static Deadline of(String rawInput) throws FidoException {
        String[] ins = rawInput.split("/by");
        if (ins.length == 0) {
            throw new FidoException(FidoException.ErrorType.DEADLINE_EMPTY_DESCRIPTION);
        } else if (ins.length == 1) {
            throw new FidoException(FidoException.ErrorType.DEADLINE_EMPTY_DATE);
        }
        try {
            return new Deadline(ins[0].trim(), LocalDate.parse(ins[1].trim()));
        } catch (DateTimeParseException e) {
            throw new FidoException(FidoException.ErrorType.NOT_VALID_DATE);
        }
    }

    /**
     * Determine if this {@code Deadline} item is already past due or will be due in
     * 1 day.
     *
     * This method compares the current system date with the stored deadline date.
     * If the deadline has already elapsed, or is within 1 day from now, it returns
     * {@code true}. Otherwise, it returns {@code false}.
     *
     * @return {@code true} If the deadline is overdue or will occur within 1 day;
     *         {@code false} otherwise.
     */
    @Override
    public boolean isDue() {
        return !super.getIsDone() && !LocalDate.now().isBefore(this.date.minusDays(1));
    }

    /**
     * Returns a string representation of this {@code Deadline} task in a format
     * suitable for file storage.
     * The format includes the task type, task status, task name, and its due date.
     *
     * @return A {@code String} representing the task in file format.
     */
    @Override
    public String fileFormat() {
        return String.format("D%s%s%s%s%s%s",
                Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(),
                Parser.DIVIDER, this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }

    /**
     * Marks the {@code Deadline} task as done.
     * This method returns a new {@code Deadline} instance with the updated status.
     *
     * @return A new {@code Deadline} instance marked as done.
     */
    @Override
    public Deadline mark() {
        return new Deadline(super.getName(), true, this.date);
    }

    /**
     * Marks the {@code Deadline} task as not done.
     * This method returns a new {@code Deadline} instance with the updated status.
     *
     * @return A new {@code Deadline} instance marked as not done.
     */
    @Override
    public Deadline unmark() {
        return new Deadline(super.getName(), false, this.date);
    }

    /**
     * Returns a string representation of this {@code Deadline} task in the
     * format "[D][ ] Task Name (by: MMM dd yyyy)".
     * - "[D]" denotes it is a {@code Deadline} task.
     * - "[ ]" or "[X]" indicates whether the task is not done or done respectively.
     *
     * @return A formatted {@code String} representing the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + String.format(" (by: %s)", this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }
}
