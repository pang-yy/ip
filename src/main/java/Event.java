import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    //private final String startDate;
    //private final String endDate;

    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Expect date format: MMM dd yyyy
     */
    Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = LocalDate.parse(startDate, Parser.DATE_OUTPUT_FORMAT);
        this.endDate = LocalDate.parse(endDate, Parser.DATE_OUTPUT_FORMAT);
    }
    
    Event(String name, LocalDate startDate, LocalDate endDate) {
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
     * Expect date format: YYYY-MM-DD
     */
    static Event of(String rawInput) throws FidoException {
        String[] ins = rawInput.split("(/from|/to)");
        if (ins.length == 0) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DESCRIPTION);
        } else if (ins.length == 1) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DATE);
        } else if (ins.length < 3) {
            throw new FidoException(FidoException.ErrorType.EVENT_MISSING_DATE);
        }
        try {
            return new Event(ins[0].trim(), 
                LocalDate.parse(ins[1].trim()), LocalDate.parse(ins[2].trim()));
        } catch (DateTimeParseException e) {
            throw new FidoException(FidoException.ErrorType.NOT_VALID_DATE);
        }
    }
    
    @Override
    String fileFormat() {
        return String.format("E%s%s%s%s%s%s%s%s",
            Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(), 
            Parser.DIVIDER, this.startDate.format(Parser.DATE_OUTPUT_FORMAT), 
            Parser.DIVIDER, this.endDate.format(Parser.DATE_OUTPUT_FORMAT));
    }
    
    @Override
    Event mark() {
        return new Event(super.getName(), true, this.startDate, this.endDate);
    }
    
    @Override
    Event unmark() {
        return new Event(super.getName(), false, this.startDate, this.endDate);
    }
    
    @Override
    public String toString() {
        return "[E]" + super.toString() +
            String.format(" (from: %s to: %s)", 
            this.startDate.format(Parser.DATE_OUTPUT_FORMAT), 
            this.endDate.format(Parser.DATE_OUTPUT_FORMAT));
    }
}
