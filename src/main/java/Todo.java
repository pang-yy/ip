public class Todo extends Task {
    
    Todo(String name) {
        super(name);
    }

    private Todo(String name, boolean isDone) {
        super(name, isDone);
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
