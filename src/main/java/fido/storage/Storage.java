package fido.storage;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import fido.exception.FidoException;

public class Storage {
    private final Path filePath;

    public Storage(String dirName) throws IOException {
        this.filePath = Storage.init(dirName);
    }

    /**
     * Ensures the given file path and its parent directories exist
     * by checking for their presence and creating any missing directories
     * or the file itself if they do not exist.
     * The dirName argument must be in the form of '[path_1]/[path_2]/[file_name]'
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

    public String readFromFile() throws FidoException {
        try {
            return Files.readString(this.filePath);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_READ_ERROR);
        }
    }

    public void appendToFile(String line) throws FidoException {
        try {
            Files.write(this.filePath, ("\n" + line).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_WRITE_ERROR);
        }
    }

    public void writeToFile(String lines) throws FidoException {
        try {
            Files.write(this.filePath, lines.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new FidoException(FidoException.ErrorType.FILE_WRITE_ERROR);
        }
    }
}
