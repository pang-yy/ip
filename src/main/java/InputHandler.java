import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputHandler {
    private List<Task> tasks;

    InputHandler() {
        this.tasks = new ArrayList<>();
    }

    Optional<String> action(String cmd) {
        if (cmd.equalsIgnoreCase("list")) {
            return this.tasks.stream()
                .map(task -> (this.tasks.indexOf(task) + 1) + ". " + task)
                .reduce((x, y) -> x + "\n" + " ".repeat(Fido.OUTPUT_INDENT_LEVEL) + y);
        }
        this.tasks.add(new Task(cmd));
        return Optional.of("added: " + cmd);
    }
}
