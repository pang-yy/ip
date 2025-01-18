public class Todo extends Task {
    
    Todo(String name) {
        super(name);
    }

    private Todo(String name, boolean isDone) {
        super(name, isDone);
    }
    
    @Override
    Todo mark() {
        return new Todo(super.name, true);
    }
    
    @Override
    Todo unmark() {
        return new Todo(super.name, false);
    }
   
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
