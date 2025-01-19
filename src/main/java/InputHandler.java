import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputHandler {
    private List<Task> tasks;

    InputHandler() {
        this.tasks = new ArrayList<>();
    }

    String action(String[] cmd) throws FidoException {
        switch(cmd[0].toLowerCase()) {
        case "list":
            return this.tasks.stream()
                .map(t -> (this.tasks.indexOf(t) + 1) + ". " + t.toString())
                .reduce("", (x, y) -> x + "\n" + y);
        case "mark":
            try {
                int idx = Integer.parseInt(cmd[1]) - 1;
                Task newTask = this.tasks.get(idx).mark();
                this.tasks.set(idx, newTask);
                return "This task has been marked as done.\n" +
                    "  " + newTask;
            } catch (IndexOutOfBoundsException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_INDEX);
                //return "Error: Please provide a valid number.";
            } catch (NumberFormatException e) {
                throw new FidoException(FidoException.ErrorType.NOT_VALID_NUMBER);
                //return "Error: Please provide number only.";
            }
        case "unmark":
            try {
                int idx = Integer.parseInt(cmd[1]) - 1;
                Task newTask = this.tasks.get(idx).unmark();
                this.tasks.set(idx, newTask);
                return "This task has been unmarked.\n" +
                    "  " + newTask;
            } catch (IndexOutOfBoundsException e) {
                return "Error: Please provide a valid number.";
            } catch (NumberFormatException e) {
                return "Error: Please provide number only.";
            }
        case "todo":
        case "deadline":
        case "event":
            if (cmd.length < 2) {
                throw new FidoException(FidoException.ErrorType.EMPTY_DESCRIPTION);
            }
            Task task;
            if (cmd[0].toLowerCase().equals("todo")) {
                task = new Todo(String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length)));
            } else if (cmd[0].toLowerCase().equals("deadline")) {
                task = Deadline.of(String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length)));
            } else {
                task = Event.of(String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length)));
            }
            this.tasks.add(task);
            if (this.tasks.size() >= 10) {
                return "Wow you have so many things to do!\n" + 
                    "added: " + task;
            }
            return "added: " + task;
        case "help":
            return this.menu();
        default:
            return "Sorry it is too complicated.🥺\n" +
                "Want to see what can I do? Try `help`.";
        }
    }

    private String menu() {
        return "todo <task>" + "\n" +
            "deadline <task> /by <date>" + "\n" +
            "event <task> /from <date> /to <date>" + "\n" +
            "list" + "\n" +
            "bye" + "\n" +
            "help";
    }
}
