public class Deadline extends Task {
    private final String date;

    Deadline(String name, String date) {
        super(name);
        this.date = date;
    }

    private Deadline(String name, boolean isDone, String date) {
        super(name, isDone);
        this.date = date;
    }

    static Deadline of(String rawInput) {
        String[] ins = rawInput.split("/by");
        return new Deadline(ins[0].trim(), ins[1].trim());
    }
    
    @Override
    Deadline mark() {
        return new Deadline(super.name, true, this.date);
    }
    
    @Override
    Deadline unmark() {
        return new Deadline(super.name, false, this.date);
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() +
            String.format(" (by: %s)", this.date);
    }
}
