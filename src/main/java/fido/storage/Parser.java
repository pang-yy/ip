package fido.storage;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import fido.exception.FidoException;
import fido.task.Deadline;
import fido.task.Event;
import fido.task.Task;
import fido.task.Todo;

/**
 * The {@code Parser} class is responsible for converting {@link Task} objects
 * to their string representations for file storage and
 * parsing stored strings back into {@link Task} objects.
 */
public class Parser {
    public static final String DIVIDER = ";;";
    public static final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Parses list of tasks into a string format suitable for file storage.
     *
     * @params tasks List of tasks.
     * @return A {@code String} representating the tasks for file storage.
     */
    public static String parseToFile(List<Task> tasks) {
        return tasks.stream()
                .map(task -> task.fileFormat())
                .reduce("", (x, y) -> x + "\n" + y)
                .trim();
    }

    /**
     * Parses file content into list of {@code Task}.
     *
     * @param content The content read from file.
     * @return List of {@code Task} objects.
     */
    public static List<Task> parseFromFile(String content) throws FidoException {
        if (content.isEmpty()) {
            return List.<Task>of();
        }
        return Arrays.asList(content.split("\n"))
                .stream()
                .map(line -> line.split(Parser.DIVIDER))
                .map(arr -> arr[0].equals("T")
                        // If is a TODO task
                        ? (arr[1].equals("false") ? (new Todo(arr[2])) : (new Todo(arr[2]).mark()))
                        // Otherwise:
                        : (arr[0].equals("D")
                                // if is a DEADLINE task
                                ? (arr[1].equals("false") ? (new Deadline(arr[2], arr[3]))
                                        : (new Deadline(arr[2], arr[3]).mark()))
                                // if is a EVENT task
                                : (arr[1].equals("false") ? (new Event(arr[2], arr[3], arr[4]))
                                        : (new Event(arr[2], arr[3], arr[4]).mark()))))
                .toList();
    }
}
