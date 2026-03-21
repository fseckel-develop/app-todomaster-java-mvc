package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTests {

    @Test
    void defaultConstructor_shouldSetCreationDateTime() {
        Task task = new Task();

        assertNotNull(task.getCreationDateTime());
    }

    @Test
    void setTitle_shouldStoreTitle() {
        Task task = new Task();

        task.setTitle("Buy milk");

        assertEquals("Buy milk", task.getTitle());
    }

    @Test
    void setTitle_shouldThrowWhenTitleIsNull() {
        Task task = new Task();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTitle(null)
        );

        assertEquals("Data must have a title.", ex.getMessage());
    }

    @Test
    void setTitle_shouldThrowWhenTitleIsEmpty() {
        Task task = new Task();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTitle("")
        );

        assertEquals("Data must have a title.", ex.getMessage());
    }

    @Test
    void toggleDone_shouldFlipDoneState() {
        Task task = createTask("Task A", "details", Task.Priority.MEDIUM, null, false);

        assertFalse(task.isDone());

        task.toggleDone();
        assertTrue(task.isDone());

        task.toggleDone();
        assertFalse(task.isDone());
    }

    @Test
    void setPriority_shouldStorePriority() {
        Task task = new Task();

        task.setPriority(Task.Priority.HIGH);

        assertEquals(Task.Priority.HIGH, task.getPriority());
    }

    @Test
    void setPriority_shouldThrowWhenPriorityIsNull() {
        Task task = new Task();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> task.setPriority(null)
        );

        assertEquals("Task must have a priority.", ex.getMessage());
    }

    @Test
    void setDeadline_shouldStoreFutureDeadline() {
        Task task = new Task();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        task.setDeadline(tomorrow);

        assertEquals(tomorrow, task.getDeadline());
    }

    @Test
    void setDeadline_shouldAllowNull() {
        Task task = new Task();
        task.setDeadline(LocalDate.now().plusDays(3));

        task.setDeadline(null);

        assertNull(task.getDeadline());
    }

    @Test
    void setDeadline_shouldThrowWhenDeadlineIsBeforeCreationDate() {
        Task task = new Task();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> task.setDeadline(yesterday)
        );

        assertEquals("Deadline is in the past.", ex.getMessage());
    }

    @Test
    void deadlineMissed_shouldReturnTrueWhenNotDoneAndPastDeadline() {
        Task task = createTask(
                "Late task",
                "details",
                Task.Priority.MEDIUM,
                LocalDate.now().minusDays(1),
                false
        );

        assertTrue(task.deadlineMissed());
    }

    @Test
    void deadlineMissed_shouldReturnFalseWhenTaskIsDone() {
        Task task = createTask(
                "Finished late task",
                "details",
                Task.Priority.MEDIUM,
                LocalDate.now().minusDays(1),
                true
        );

        assertFalse(task.deadlineMissed());
    }

    @Test
    void deadlineMissed_shouldReturnFalseWhenDeadlineIsToday() {
        Task task = createTask(
                "Due today",
                "details",
                Task.Priority.MEDIUM,
                LocalDate.now(),
                false
        );

        assertFalse(task.deadlineMissed());
    }

    @Test
    void toJson_shouldSerializeAllFields() {
        JSONObject source = new JSONObject()
                .put("title", "Buy milk")
                .put("details", "2 liters")
                .put("isDone", false)
                .put("priority", "HIGH")
                .put("deadline", LocalDate.now().plusDays(3).toString())
                .put("creationDate", LocalDateTime.of(2026, 1, 10, 12, 30).toString());

        Task task = new Task(source);

        JSONObject json = task.toJSON();

        assertEquals("Buy milk", json.getString("title"));
        assertEquals("2 liters", json.getString("details"));
        assertFalse(json.getBoolean("isDone"));
        assertEquals("HIGH", json.getString("priority"));
        assertEquals(LocalDate.now().plusDays(3).toString(), json.getString("deadline"));
        assertEquals(LocalDateTime.of(2026, 1, 10, 12, 30).toString(), json.getString("creationDate"));
    }

    @Test
    void jsonConstructor_shouldReadAllFields() {
        JSONObject json = new JSONObject()
                .put("title", "Read book")
                .put("details", "Chapter 3")
                .put("isDone", true)
                .put("priority", "LOW")
                .put("deadline", LocalDate.now().plusDays(5).toString())
                .put("creationDate", "2026-02-01T08:15");

        Task task = new Task(json);

        assertEquals("Read book", task.getTitle());
        assertEquals("Chapter 3", task.getDetails());
        assertTrue(task.isDone());
        assertEquals(Task.Priority.LOW, task.getPriority());
        assertEquals(LocalDate.now().plusDays(5), task.getDeadline());
        assertEquals(LocalDateTime.parse("2026-02-01T08:15"), task.getCreationDateTime());
    }

    @Test
    void priorityToString_shouldReturnExpectedLabels() {
        assertEquals("high", Task.Priority.HIGH.toString());
        assertEquals("medium", Task.Priority.MEDIUM.toString());
        assertEquals("low", Task.Priority.LOW.toString());
    }

    private Task createTask(String title,
                            String details,
                            Task.Priority priority,
                            LocalDate deadline,
                            boolean done) {
        JSONObject json = new JSONObject()
                .put("title", title)
                .put("details", details)
                .put("isDone", done)
                .put("priority", priority.name())
                .put("deadline", deadline == null ? JSONObject.NULL : deadline.toString())
                .put("creationDate", LocalDateTime.now().minusDays(2).toString());

        return new Task(json);
    }
}
