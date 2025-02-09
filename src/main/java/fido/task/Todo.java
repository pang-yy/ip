package fido.task;

import java.util.Objects;

import fido.storage.Parser;

/**
 * The {@code Todo} class represents a simple {@link Task} with a name
 * that can be marked as done or not done.
 * It extends the abstract {@link Task} class.
 */
public class Todo extends Task {

    /**
     * Constructs a new {@code Todo} task with the specified name.
     * By default, the task is not marked as done.
     *
     * @param name The name or description of the task.
     */
    public Todo(String name) {
        super(name);
    }

    private Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    /**
     * Returns {@code true} if this {@code Todo} item is overdue or due within the next day.
     * Because a {@code Todo} item does not have a specific deadline, it will never be
     * considered overdue. Consequently, this method always returns {@code false}.
     *
     * @return {@code false} always.
     */
    @Override
    public boolean isDue() {
        return false;
    }

    /**
     * Returns a string representation of this {@code Todo} task in a format
     * suitable for file storage.
     * The format includes the task type, task status, and its name.
     *
     * @return A {@code String} representing the task in file format.
     */
    @Override
    public String fileFormat() {
        return String.format("T%s%s%s%s",
                Parser.DIVIDER, super.getIsDone(), Parser.DIVIDER, super.getName());
    }

    /**
     * Marks the {@code Todo} task as done.
     * This method returns a new {@code Todo} instance with the updated status.
     *
     * @return A new {@code Todo} instance marked as done.
     */
    @Override
    public Todo mark() {
        return new Todo(super.getName(), true);
    }

    /**
     * Marks the {@code Todo} task as not done.
     * This method returns a new {@code Todo} instance with the updated status.
     *
     * @return A new {@code Todo} instance marked as not done.
     */
    @Override
    public Todo unmark() {
        return new Todo(super.getName(), false);
    }

    /**
     * Compares this {@code Todo} to the specified object. 
     * Returns {@code true} if:
     *     - the other object is also a {@code Todo}, and
     *     - both have the same {@code name}, and
     *     - both are not done.
     *
     * @param obj The object to compare with this {@code Todo}.
     * @return {@code true} if this {@code Todo} is the same as the specified object, 
     *          otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Todo other) {
            return this.getName().equals(other.getName()) &&
                !this.getIsDone() && !other.getIsDone();
        }
        return false;
    }
    
    // Returns a hash code value for this Task.
    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), super.getIsDone());
    }

    /**
     * Returns a string representation of this {@code Todo} task in the
     * format "[T][ ] Task Name".
     * - "[T]" denotes it is a {@code Todo} task.
     * - "[ ]" or "[X]" indicates whether the task is not done or done respectively.
     *
     * @return A formatted {@code String} representing the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
