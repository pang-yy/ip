package fido.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import fido.exception.FidoException;

public class EventTest {

    @Test
    public void of_validInput_success() {
        String input = "sales /from 2025-01-01 /to 2025-02-01";
        String expectedOutput = "[E][ ] sales (from: Jan 01 2025 to: Feb 01 2025)";
        try {
            assertEquals(expectedOutput, Event.of(input).toString());
        } catch (FidoException e) {
            fail();
        }
    }

    @Test
    public void of_emptyInput_exceptionThrown() {
        assertThrows(FidoException.class, () -> Event.of(""));
    }
    
    @Test
    public void of_missingBothDates_exceptionThrown() {
        assertThrows(FidoException.class, () -> Event.of("sales"));
    }
    
    @Test
    public void of_missingStartDate_exceptionThrown() {
        assertThrows(FidoException.class, () -> Event.of("sales /to 2025-02-01"));
    }
    
    @Test
    public void of_missingEndDate_exceptionThrown() {
        assertThrows(FidoException.class, () -> Event.of("sales /from 2025-01-01"));
    }
    
    @Test
    public void of_invalidDateFormat_exceptionThrown() {
        assertThrows(FidoException.class, 
            () -> Event.of("sales /from 1/2/2025 /to 2025-02-01"));
    }
    
    @Test
    public void isDue_notDueEvent_false() {
        String startDate = LocalDate.now().plusDays(5).toString();
        String endDate = LocalDate.now().plusDays(10).toString();
        String input = "do assignment /from " + startDate + " /to " + endDate;
        try {
            assertEquals(false, Event.of(input).isDue());
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void isDue_dueEvent_true() {
        String startDate = LocalDate.now().minusDays(1).toString();
        String endDate = LocalDate.now().plusDays(10).toString();
        String input = "do assignment /from " + startDate + " /to " + endDate;
        try {
            assertEquals(true, Event.of(input).isDue());
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void mark_eventTask_eventMarkAsDone() {
        String input = "do assignment /from 2025-01-01 /to 2025-02-01";
        try {
            assertTrue(Event.of(input).mark().toString().contains("[X]"));
        } catch (FidoException e) {
            fail();
        }
    }
    
    @Test
    public void mark_eventTask_eventMarkAsNotDone() {
        String input = "do assignment /from 2025-01-01 /to 2025-02-01";
        try {
            assertTrue(Event.of(input).mark().unmark().toString().contains("[ ]"));
        } catch (FidoException e) {
            fail();
        }
    }
}
