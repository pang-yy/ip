import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    //private final String date;
    private final LocalDate date;

    /**
     * Expect date format: MMM dd yyyy
     */
    Deadline(String name, String date) {
        super(name);
        this.date = LocalDate.parse(date, Parser.DATE_OUTPUT_FORMAT);
    }

    Deadline(String name, LocalDate date) {
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
    static Deadline of(String rawInput) throws FidoException {
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

    @Override
    String fileFormat() {
        return String.format("D%s%s%s%s%s%s",
            Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(), 
            Parser.DIVIDER, this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }
    
    @Override
    Deadline mark() {
        return new Deadline(super.getName(), true, this.date);
    }
    
    @Override
    Deadline unmark() {
        return new Deadline(super.getName(), false, this.date);
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() +
            String.format(" (by: %s)", this.date.format(Parser.DATE_OUTPUT_FORMAT));
    }
}
