public abstract class Task {
    private final String name;
    private final boolean isDone;

    Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    protected Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    protected String getName() {
        return this.name;
    }

    protected String getIsDone() {
        return this.isDone ? "1" : "0";
    }

    abstract String fileFormat();

    abstract Task mark();
   
    abstract Task unmark();

    public String toString() {
        if (this.isDone) {
            return "[X] " + this.name;
        }
        return "[ ] " + this.name;
    }
}
