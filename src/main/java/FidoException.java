public class FidoException extends Exception {
    public enum ErrorType {
        NOT_VALID_INDEX,
        NOT_VALID_NUMBER,
        EMPTY_DESCRIPTION,
        DEADLINE_EMPTY_DESCRIPTION,
        DEADLINE_EMPTY_DATE,
        EVENT_EMPTY_DESCRIPTION,
        EVENT_EMPTY_DATE,
        EVENT_MISSING_DATE,
        NOT_VALID_DATE,

        NOT_VALID_FILEPATH,
        FILE_READ_ERROR,
        FILE_WRITE_ERROR,
    }

    private final ErrorType eType;

    FidoException(ErrorType eType) {
        this.eType = eType;
    }

    @Override
    public String getMessage() {
        switch(this.eType) {
        case NOT_VALID_INDEX:
            return "Error: The task is not on the list.";
        case NOT_VALID_NUMBER:
            return "Error: Unfortunately, that's not a number.";
        case EMPTY_DESCRIPTION:
            return "Error: No task provided.\nType `help` for helps.";
        case DEADLINE_EMPTY_DESCRIPTION:
            return "Error: No task provided.\nUsage: deadline <task> /by <date>";
        case DEADLINE_EMPTY_DATE:
            return "Error: No deadline provided.\nUsage: deadline <task> /by <date>";
        case EVENT_EMPTY_DESCRIPTION:
            return "Error: No task provided.\nUsage: event <task> /from <date> /to <date>";
        case EVENT_EMPTY_DATE:
            return "Error: No date provided.\nUsage: event <task> /from <date> /to <date>";
        case EVENT_MISSING_DATE:
            return "Error: Must provide start and end date.\nUsage: event <task> /from <date> /to <date>";
        case NOT_VALID_DATE:
            return "Error: Invalid date format.\nFormat: YYYY-MM-DD";
        case NOT_VALID_FILEPATH:
            return "Error: IOException";
        case FILE_READ_ERROR:
            return "Error: Failed to read from task file.";
        case FILE_WRITE_ERROR:
            return "Error: Failed to write to task file.";
        default:
            return "Unknown error encountered!";
        }
    }
}
