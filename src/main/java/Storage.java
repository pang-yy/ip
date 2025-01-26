import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Storage {

    /**
     * Ensures the given file path and its parent directories exist
     * by checking for their presence and creating any missing directories
     * or the file itself if they do not exist.
     * The dirName argument must be in the form of '<path 1>/<path 2>/<file name>'
     *
     * @param dirName Relative file path to be checked.
     * @return The path to the file.
     */
    public static Path init(String dirName) throws IOException {
        String[] dirArray = dirName.split("/");
        String[] parentDir = Arrays.copyOfRange(dirArray, 0, dirArray.length - 1);
        try {
            Files.createDirectories(Path.of(String.join("/", parentDir)));
            return Files.createFile(Path.of(dirName));
        } catch (FileAlreadyExistsException e) {
            return Path.of(dirName);
        }
    }

    public static String readFromFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }

    public static void appendToFile(Path filePath, String line) throws IOException {
        Files.write(filePath, ("\n" + line).getBytes(), StandardOpenOption.APPEND);
    }

    public static void writeToFile(Path filePath, String lines) throws IOException {
        Files.write(filePath, lines.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
