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

    static Event of(String rawInput) {
        String[] ins = rawInput.split("(/from|/to)");
        return new Event(ins[0].trim(), ins[1].trim(), ins[2].trim());
    }
    
    @Override
    Event mark() {
        return new Event(super.name, true, this.startDate, this.endDate);
    }
    
    @Override
    Event unmark() {
        return new Event(super.name, false, this.startDate, this.endDate);
    }
    
    @Override
    public String toString() {
        return "[E]" + super.toString() +
            String.format(" (from: %s to: %s)", this.startDate, this.endDate);
    }
}
