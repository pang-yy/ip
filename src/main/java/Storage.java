import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Storage {
    private final Path filePath;

    Storage(String dirName) throws IOException {
        this.filePath = Storage.init(dirName);
    }

    /**
     * Ensures the given file path and its parent directories exist
     * by checking for their presence and creating any missing directories
     * or the file itself if they do not exist.
     * The dirName argument must be in the form of '<path 1>/<path 2>/<file name>'
     *
     * @param dirName Relative file path to be checked.
     * @return The path to the file.
     */
    static Path init(String dirName) throws IOException {
        String[] dirArray = dirName.split("/");
        String[] parentDir = Arrays.copyOfRange(dirArray, 0, dirArray.length - 1);
        try {
            Files.createDirectories(Path.of(String.join("/", parentDir)));
            return Files.createFile(Path.of(dirName));
        } catch (FileAlreadyExistsException e) {
            return Path.of(dirName);
        }
    }

    String readFromFile() throws FidoException {
        try {
            return Files.readString(this.filePath);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_READ_ERROR);
        }
    }

    void appendToFile(String line) throws FidoException {
        try {
            Files.write(this.filePath, ("\n" + line).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_WRITE_ERROR);
        }
    }

    void writeToFile(String lines) throws FidoException {
        try {
            Files.write(this.filePath, lines.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_WRITE_ERROR);
        }
    }
}
