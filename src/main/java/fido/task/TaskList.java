package fido.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import fido.exception.FidoException;
import fido.storage.Parser;
import fido.storage.Storage;

/**
 * The {@code TaskList} class manages a list of {@link Task} and handles user actions,
 * by interacting with {@link Storage} instance.
 * It perform actions based on user commands, and update both the in-memory task list
 * and the task file in hark disk accordingly.
 *
 * This class encapsulates a {@code List<Task>} to store tasks and a {@code Storage}
 * instance to handle file operations.
 */
public class TaskList {
    private List<Task> tasks;
    private final Storage storage;

    /**
     * Constructs a new {@code TaskList} with the specified {@link Storage} instance.
     * This constructor attempts to load all existing tasks from the storage. If loading fails
     * due to reasons such as access issues or parsing issues, a {@link FidoException}
     * is thrown to indicate the failure.
     *
     * @param storage The {@code Storage} instance responsible for file operations.
     * @throws FidoException If an error occurs while loading tasks from the storage.
     */
    public TaskList(Storage storage) throws FidoException {
        try {
            this.storage = storage;
            this.tasks = new ArrayList<>(Parser.parseFromFile(this.storage.readFromFile()));
        } catch (FidoException e) {
            throw e;
        }
    }

    /**
     * Executes an action based on the user command provided in the {@code inputs} array.
     * The first element of the {@code inputs} array determines the type of action to perform.
     * Depending on the command, this method may add, delete, update, or display tasks.
     *
     * @param inputs An array of {@code String} representing the user command and its arguments.
     *               The command is expected to be in {@code inputs[0]}, followed by relevant arguments.
     * @return A {@code String} message indicating the result of the action, which can be displayed to the user.
     * @throws FidoException If the action fails due to reasons such as incorrect arguments, and invalid arguments.
     */
    public String action(String[] inputs) throws FidoException {
        String command = inputs[0].toLowerCase();
        switch (command) {
        case "list":
            return Stream.<Integer>iterate(0, i -> i < this.tasks.size(), i -> i + 1)
                .map(i -> (i + 1) + ". " + this.tasks.get(i).toString())
                .reduce("", (x, y) -> x + "\n" + y);
        case "due":
            return Stream.<Integer>iterate(0, i -> i < this.tasks.size(), i -> i + 1)
                .filter(i -> this.tasks.get(i).isDue())
                .map(i -> (i + 1) + ". " + this.tasks.get(i).toString())
                .reduce("Here's the list of tasks that are due or will be due in 1 day:", (x, y) -> x + "\n" + y);
        case "find":
            try {
                return Stream.<Integer>iterate(0, i -> i < this.tasks.size(), i -> i + 1)
                    .filter(i -> this.tasks.get(i).contains(inputs[1]))
                    .map(i -> (i + 1) + ". " + this.tasks.get(i).toString())
                    .reduce("Here's the list of tasks that contain " + inputs[1] + ":", (x, y) -> x + "\n" + y);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_INDEX);
            }
        case "mark": //Fallthrough
        case "unmark": //Fallthrough
        case "delete":
            try {
                int idx = Integer.parseInt(inputs[1]) - 1;
                if (command.equals("mark")) {
                    Task newTask = this.tasks.get(idx).mark();
                    this.tasks.set(idx, newTask);
                    this.storage.writeToFile(Parser.parseToFile(this.tasks));
                    return "This task has been marked as done.\n"
                        + "  " + newTask;
                } else if (command.equals("unmark")) {
                    Task newTask = this.tasks.get(idx).unmark();
                    this.tasks.set(idx, newTask);
                    this.storage.writeToFile(Parser.parseToFile(this.tasks));
                    return "This task has been unmarked.\n"
                        + "  " + newTask;
                } else { // delete
                    Task deletedTask = this.tasks.remove(idx);
                    this.storage.writeToFile(Parser.parseToFile(this.tasks));
                    return "Following task has been removed:\n"
                        + "  " + deletedTask;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_INDEX);
            } catch (NumberFormatException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_NUMBER);
            }
        case "todo": // Fallthrough
        case "deadline": // Fallthrough
        case "event":
            if (inputs.length < 2) {
                throw new FidoException(FidoException.ErrorType.EMPTY_DESCRIPTION);
            }
            Task task;
            if (command.equals("todo")) {
                task = new Todo(String.join(" ", Arrays.copyOfRange(inputs, 1, inputs.length)));
            } else if (command.equals("deadline")) {
                task = Deadline.of(String.join(" ", Arrays.copyOfRange(inputs, 1, inputs.length)));
            } else { // event
                task = Event.of(String.join(" ", Arrays.copyOfRange(inputs, 1, inputs.length)));
            }

            /* Find if there are same task exists,
             */
            Optional<Task> sameTask = this.tasks.stream()
                .filter(t -> t.equals(task))
                .findFirst();
            this.tasks.add(task);
            this.storage.writeToFile(Parser.parseToFile(this.tasks));
            return sameTask
                .map(t -> 
                    "added: " + task.toString() 
                    + "\nPossible dupleicate tasks: " + t.toString()
                    + "\nUse `rmdp` to remove duplicates.")
                .orElse("added: " + task.toString());
        case "rmdp":
            // stream toList returns immutable list
            this.tasks = new ArrayList<>(
                this.tasks.stream().distinct().toList());
            this.storage.writeToFile(Parser.parseToFile(this.tasks));
            return "Removed duplicate.";
        case "help":
            return this.menu();
        default:
            return "Sorry it is too complicated.\n"
                + "Want to see what I can do? Try `help`.";
        }
    }

    private String menu() {
        return "Here's the list of commands:\n"
                + "todo <task>" + "\n"
                + "deadline <task> /by <date>" + "\n"
                + "event <task> /from <date> /to <date>" + "\n"
                + "list" + "\n"
                + "find <task name>" + "\n"
                + "mark <task index>" + "\n"
                + "unmark <task index>" + "\n"
                + "delete <task index>" + "\n"
                + "due" + "\n"
                + "rmdp" + "\n"
                + "bye" + "\n"
                + "help";
    }
}
