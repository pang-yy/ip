import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Parser {
    public static final String DIVIDER = ";;";
    public static final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    static String parseToFile(List<Task> tasks) {
        return tasks.stream()
            .map(task -> task.fileFormat())
            .reduce("", (x, y) -> x + "\n" + y)
            .trim();
    }

    /**
     * Parse file content into list of {@code Task}.
     *
     * @param content The content read from file.
     * @return List of {@code Task} objects.
     */
    static List<Task> parseFromFile(String content) throws FidoException {
        if (content.isEmpty()) {
            return List.<Task>of();
        }
        return Arrays.asList(content.split("\n"))
            .stream()
            .map(line -> line.split(Parser.DIVIDER))
            .map(arr -> 
                arr[0].equals("T") ? 
                    (arr[1].equals("false") ? (new Todo(arr[2])) : (new Todo(arr[2]).mark())) :
                (arr[0].equals("D") ? 
                    (arr[1].equals("false") ? (new Deadline(arr[2], arr[3])) : (new Deadline(arr[2], arr[3]).mark())) : 
                (arr[1].equals("false") ? (new Event(arr[2], arr[3], arr[4])) : (new Event(arr[2], arr[3], arr[4]).mark()))))
            .toList();
    }
}
