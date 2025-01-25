import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputHandler {
    private List<Task> tasks;
    private final Path filepath;

    InputHandler() throws IOException {
        this.filepath = Storage.init("data/data.txt");
        this.tasks = new ArrayList<>(Parser.parseFromFile(Storage.readFromFile(this.filepath)));
    }

    String action(String[] inputs) throws FidoException {
        String command = inputs[0].toLowerCase();
        switch(command) {
        case "list":
            return this.tasks.stream()
                .map(t -> (this.tasks.indexOf(t) + 1) + ". " + t.toString())
                .reduce("", (x, y) -> x + "\n" + y);
        case "mark":
        case "unmark":
        case "delete":
            try {
                int idx = Integer.parseInt(inputs[1]) - 1;
                if (command.equals("mark")) {
                    Task newTask = this.tasks.get(idx).mark();
                    this.tasks.set(idx, newTask);
                    Storage.writeToFile(this.filepath, Parser.parseToFile(this.tasks));
                    return "This task has been marked as done.\n" +
                        "  " + newTask;
                } else if (command.equals("unmark")) {
                    Task newTask = this.tasks.get(idx).unmark();
                    this.tasks.set(idx, newTask);
                    Storage.writeToFile(this.filepath, Parser.parseToFile(this.tasks));
                    return "This task has been unmarked.\n" +
                        "  " + newTask;
                } else { // delete
                    Task deletedTask = this.tasks.remove(idx);
                    Storage.writeToFile(this.filepath, Parser.parseToFile(this.tasks));
                    return "Following task has been removed.\n" +
                        "  " + deletedTask;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_INDEX);
            } catch (NumberFormatException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_NUMBER);
            } catch (IOException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_FILEPATH);
            }
        case "todo":
        case "deadline":
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
            this.tasks.add(task);
            try {
                Storage.writeToFile(this.filepath, Parser.parseToFile(this.tasks));
            } catch (IOException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_FILEPATH);
            }
            if (this.tasks.size() >= 10) {
                return "Wow you have so many things to do!\n" + 
                    "added: " + task;
            }
            return "added: " + task;
        case "help":
            return this.menu();
        default:
            return "Sorry it is too complicated.🥺\n" +
                "Want to see what I can do? Try `help`.";
        }
    }

    private String menu() {
        return "Here's the list of commands:\n" +
            "todo <task>" + "\n" +
            "deadline <task> /by <date>" + "\n" +
            "event <task> /from <date> /to <date>" + "\n" +
            "list" + "\n" +
            "mark <task index>" + "\n" +
            "unmark <task index>" + "\n" +
            "delete <task index>" + "\n" +
            "bye" + "\n" +
            "help";
    }
}
