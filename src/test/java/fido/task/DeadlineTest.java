package fido.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import fido.exception.FidoException;

public class DeadlineTest {

    @Test
    public void of_validInput_success() {
        String input = "do assignment /by 2025-01-01";
        String expectedOutput = "[D][ ] do assignment (by: Jan 01 2025)";
        try {
            assertEquals(expectedOutput, Deadline.of(input).toString());
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void of_emptyInput_exceptionThrown() {
        assertThrows(FidoException.class, () -> Deadline.of(""));
    }
    
    @Test
    public void of_missingDate_exceptionThrown() {
        assertThrows(FidoException.class, () -> Deadline.of("do assignment"));
    }
    
    @Test
    public void of_invalidDateFormat_exceptionThrown() {
        String input = "do assignment /by 1/2/2025";
        assertThrows(FidoException.class, () -> Deadline.of(input));
    }

    @Test
    public void isDue_notDueDeadline_false() {
        String dateNow = LocalDate.now().plusDays(10).toString();
        String input = "do assignment /by " + dateNow;
        try {
            assertEquals(false, Deadline.of(input).isDue());
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void isDue_dueDeadline_true() {
        String dateNow = LocalDate.now().minusDays(1).toString();
        String input = "do assignment /by " + dateNow;
        try {
            assertEquals(true, Deadline.of(input).isDue());
        } catch (FidoException e) {
            fail();
        }
    }

    @Test
    public void mark_deadlineTask_deadlineMarkAsDone() {
        String input = "do assignment /by 2025-01-01";
        try {
            assertTrue(Deadline.of(input).mark().toString().contains("[X]"));
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void unmark_deadlineTask_deadlineMarkAsNotDone() {
        String input = "do assignment /by 2025-01-01";
        try {
            assertTrue(Deadline.of(input).mark().unmark().toString().contains("[ ]"));
        } catch (FidoException e) {
            fail();
        }
    }
}
