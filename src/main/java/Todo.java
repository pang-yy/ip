public class Todo extends Task {
    
    Todo(String name) {
        super(name);
    }

    private Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    /**
     * Determine if this {@code Todo} item is overdue or due within the next day.
     *
     * Because a {@code Todo} item does not have a specific deadline, it will never be
     * considered overdue. Consequently, this method always returns {@code false}.
     *
     * @return {@code false} always.
     */
    @Override
    boolean isDue() {
        return false;
    }

    @Override
    String fileFormat() {
        return String.format("T%s%s%s%s",
            Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName());
    }
    
    @Override
    Todo mark() {
        return new Todo(super.getName(), true);
    }
    
    @Override
    Todo unmark() {
        return new Todo(super.getName(), false);
    }
   
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
