import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputHandler {
    private List<Task> tasks;

    InputHandler() {
        this.tasks = new ArrayList<>();
    }

    Optional<String> action(String[] cmd) {
        switch(cmd[0]) {
        case "list":
            return this.multiLine(this.tasks.stream()
                .map(t -> (this.tasks.indexOf(t) + 1)  + ". " + t.toString())
                .toList());
        case "mark":
            try {
                int idx = Integer.parseInt(cmd[1]) - 1;
                Task newTask = this.tasks.get(idx).mark();
                this.tasks.set(idx, newTask);
                return multiLine(List.of("This task has been marked as done.",
                    "  " + newTask));
            } catch (IndexOutOfBoundsException e) {
                return Optional.of("Error: Please provide a valid number.");
            } catch (NumberFormatException e) {
                return Optional.of("Error: Please provide number only.");
            }
        case "unmark":
            try {
                int idx = Integer.parseInt(cmd[1]) - 1;
                Task newTask = this.tasks.get(idx).unmark();
                this.tasks.set(idx, newTask);
                return multiLine(List.of("This task has been unmarked",
                    "  " + newTask));
            } catch (IndexOutOfBoundsException e) {
                return Optional.of("Error: Please provide a valid number.");
            } catch (NumberFormatException e) {
                return Optional.of("Error: Please provide number only.");
            }
        default:
            Task task = new Task(String.join(" ", cmd));
            this.tasks.add(task);
            if (this.tasks.size() >= 10) {
                return multiLine(List.of("Wow you have so many things to do!",
                    "added: " + task));
            }
            return Optional.of("added: " + task);
        }
    }

    private Optional<String> multiLine(List<String> strs) {
        return strs.stream()
            .reduce((x, y) -> x + 
                "\n" + " ".repeat(Fido.OUTPUT_INDENT_LEVEL) + 
                y);
    }
}
