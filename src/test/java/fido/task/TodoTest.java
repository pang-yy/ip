package fido.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    @Test
    public void constructor_validInput_success() {
        String input = "drink water";
        String expectedOutput = "[T][ ] drink water";
        assertEquals(expectedOutput, new Todo(input).toString());
    }
    
    @Test
    public void constructor_emptyInput_success() {
        assertEquals("[T][ ] ", new Todo("").toString());
    }
    
    @Test
    public void isDue_todoTask_false() {
        assertEquals(false, new Todo("drink water").isDue());
    }
    
    @Test
    public void mark_todoTask_todoMarkAsDone() {
        String input = "drink water";
        assertTrue(new Todo(input).mark().toString().contains("[X]"));
    }
    
    @Test
    public void unmark_todoTask_todoMarkAsNotDone() {
        String input = "drink water";
        assertTrue(new Todo(input).mark().unmark().toString().contains("[ ]"));
    }
}
