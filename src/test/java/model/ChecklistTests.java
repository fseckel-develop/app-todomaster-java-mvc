package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChecklistTests {

    @Test
    void defaultConstructor_shouldUseTextDisplayMode() {
        Checklist checklist = new Checklist();

        assertEquals(Checklist.DisplayMode.TEXT, checklist.getDisplayMode());
    }

    @Test
    void setDisplayMode_shouldUpdateDisplayMode() {
        Checklist checklist = new Checklist();

        checklist.setDisplayMode(Checklist.DisplayMode.CARDS);

        assertEquals(Checklist.DisplayMode.CARDS, checklist.getDisplayMode());
    }

    @Test
    void toggleDisplayMode_shouldSwitchBetweenTextAndCards() {
        Checklist checklist = new Checklist();

        assertEquals(Checklist.DisplayMode.TEXT, checklist.getDisplayMode());

        checklist.toggleDisplayMode();
        assertEquals(Checklist.DisplayMode.CARDS, checklist.getDisplayMode());

        checklist.toggleDisplayMode();
        assertEquals(Checklist.DisplayMode.TEXT, checklist.getDisplayMode());
    }

    @Test
    void add_shouldIncreaseItemCount() {
        Checklist checklist = new Checklist();
        Task task = createTask("Task 1", false, Task.Priority.MEDIUM, null, LocalDateTime.now());

        checklist.add(task);

        assertEquals(1, checklist.getItemCount());
        assertSame(task, checklist.getItems().getFirst());
    }

    @Test
    void remove_shouldDecreaseItemCount() {
        Checklist checklist = new Checklist();
        Task task = createTask("Task 1", false, Task.Priority.MEDIUM, null, LocalDateTime.now());
        checklist.add(task);

        checklist.remove(task);

        assertEquals(0, checklist.getItemCount());
    }

    @Test
    void clear_shouldRemoveAllItems() {
        Checklist checklist = new Checklist();
        checklist.add(createTask("Task 1", false, Task.Priority.HIGH, null, LocalDateTime.now()));
        checklist.add(createTask("Task 2", true, Task.Priority.LOW, null, LocalDateTime.now()));

        checklist.clear();

        assertEquals(0, checklist.getItemCount());
        assertTrue(checklist.getItems().isEmpty());
    }

    @Test
    void getNumberOfOpenTasks_shouldCountOnlyUndoneTasks() {
        Checklist checklist = new Checklist();
        checklist.add(createTask("Open A", false, Task.Priority.HIGH, null, LocalDateTime.now()));
        checklist.add(createTask("Done B", true, Task.Priority.LOW, null, LocalDateTime.now()));
        checklist.add(createTask("Open C", false, Task.Priority.MEDIUM, null, LocalDateTime.now()));

        int openTasks = checklist.getNumberOfOpenTasks();

        assertEquals(2, openTasks);
    }

    @Test
    void toJson_shouldSerializeDisplayModeAndItems() {
        Checklist checklist = new Checklist();
        checklist.setTitle("Daily");
        checklist.setDisplayMode(Checklist.DisplayMode.CARDS);
        checklist.add(createTask("Task 1", false, Task.Priority.HIGH, LocalDate.now().plusDays(1), LocalDateTime.now()));

        JSONObject json = checklist.toJSON();

        assertEquals("Daily", json.getString("title"));
        assertEquals("CARDS", json.getString("displayMode"));
        assertEquals(1, json.getJSONArray("items").length());
    }

    @Test
    void jsonConstructor_shouldRestoreChecklistFields() {
        JSONObject json = new JSONObject()
                .put("title", "Work")
                .put("displayMode", "TEXT")
                .put("items", new org.json.JSONArray()
                        .put(createTaskJson("Task 1", false, "HIGH", null, "2026-03-01T10:00"))
                        .put(createTaskJson("Task 2", true, "LOW", LocalDate.now().plusDays(3).toString(), "2026-03-02T11:00")));

        Checklist checklist = new Checklist(json);

        assertEquals("Work", checklist.getTitle());
        assertEquals(Checklist.DisplayMode.TEXT, checklist.getDisplayMode());
        assertEquals(2, checklist.getItemCount());
    }

    @Test
    void displayModeToString_shouldReturnExpectedLabels() {
        assertEquals("card stack", Checklist.DisplayMode.CARDS.toString());
        assertEquals("flowing text", Checklist.DisplayMode.TEXT.toString());
    }

    private Task createTask(String title,
                            boolean done,
                            Task.Priority priority,
                            LocalDate deadline,
                            LocalDateTime creationDate) {
        return new Task(createTaskJson(
                title,
                done,
                priority.name(),
                deadline == null ? null : deadline.toString(),
                creationDate.toString()
        ));
    }

    private JSONObject createTaskJson(String title,
                                      boolean done,
                                      String priority,
                                      String deadline,
                                      String creationDate) {
        return new JSONObject()
                .put("title", title)
                .put("details", "details")
                .put("isDone", done)
                .put("priority", priority)
                .put("deadline", deadline == null ? JSONObject.NULL : deadline)
                .put("creationDate", creationDate);
    }
}
