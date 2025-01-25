public class Event extends Task {
    private final String startDate;
    private final String endDate;

    Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private Event(String name, boolean isDone, String startDate, String endDate) {
        super(name, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    static Event of(String rawInput) throws FidoException {
        String[] ins = rawInput.split("(/from|/to)");
        if (ins.length == 0) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DESCRIPTION);
        } else if (ins.length == 1) {
            throw new FidoException(FidoException.ErrorType.EVENT_EMPTY_DATE);
        } else if (ins.length < 3) {
            throw new FidoException(FidoException.ErrorType.EVENT_MISSING_DATE);
        }
        return new Event(ins[0].trim(), ins[1].trim(), ins[2].trim());
    }
    
    @Override
    String fileFormat() {
        return String.format("E%s%s%s%s%s%s%s%s",
            Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName(), 
            Parser.DIVIDER, this.startDate, Parser.DIVIDER, this.endDate);
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
            String.format(" (from: %s to: %s)", this.startDate, this.endDate);
    }
}
