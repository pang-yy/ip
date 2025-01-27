package fido.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import fido.exception.FidoException;
import fido.storage.Parser;

public class Deadline extends Task {
    private final LocalDate date;

    /**
     * Expect date format: MMM dd yyyy
     */
    public Deadline(String name, String date) {
        super(name);
        this.date = LocalDate.parse(date, Parser.DATE_OUTPUT_FORMAT);
    }

    public Deadline(String name, LocalDate date) {
        super(name);
        this.date = date;
    }

    private Deadline(String name, boolean isDone, LocalDate date) {
        super(name, isDone);
        this.date = date;
    }

    /**
     * Expect date format: YYYY-MM-DD
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

    @Override
    public String fileFormat() {
        return String.format("D%s%s%s%s%s%s",
                Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(),
                Parser.DIVIDER, this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }

    @Override
    public Deadline mark() {
        return new Deadline(super.getName(), true, this.date);
    }

    @Override
    public Deadline unmark() {
        return new Deadline(super.getName(), false, this.date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + String.format(" (by: %s)", this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }
}
