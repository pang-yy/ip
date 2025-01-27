package fido.task;

public abstract class Task {
    private final String name;
    private final boolean isDone;

    public Task(String name) {
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

    protected boolean getIsDone() {
        return this.isDone;
    }

    public boolean contains(String str) {
        return this.name.contains(str);
    }

    public abstract boolean isDue();

    public abstract String fileFormat();

    public abstract Task mark();
   
    public abstract Task unmark();

    public String toString() {
        if (this.isDone) {
            return "[X] " + this.name;
        }
        return "[ ] " + this.name;
    }
}
