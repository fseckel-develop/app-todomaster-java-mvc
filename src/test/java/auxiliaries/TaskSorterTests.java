package auxiliaries;

import model.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskSorterTests {

    @Test
    void sortByOldestFirst_shouldPutUndoneBeforeDoneAndThenByCreationDate() {
        Task olderUndone = createTask("Older", "b", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task newerUndone = createTask("Newer", "a", false, Task.Priority.HIGH, null,
                LocalDateTime.of(2026, 1, 2, 10, 0));
        Task doneTask = createTask("Done", "c", true, Task.Priority.LOW, null,
                LocalDateTime.of(2025, 12, 31, 10, 0));

        List<Task> sorted = TaskSorter.getTasksSortedBy(
                TaskSorter.Mode.OLDEST_FIRST,
                List.of(newerUndone, doneTask, olderUndone)
        );

        assertEquals(List.of(olderUndone, newerUndone, doneTask), sorted);
    }

    @Test
    void sortByPriority_shouldPutUndoneBeforeDoneAndSortByPriorityEnumOrder() {
        Task high = createTask("High", "", false, Task.Priority.HIGH, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task medium = createTask("Medium", "", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task low = createTask("Low", "", false, Task.Priority.LOW, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task doneHigh = createTask("Done High", "", true, Task.Priority.HIGH, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));

        List<Task> sorted = TaskSorter.getTasksSortedBy(
                TaskSorter.Mode.BY_PRIORITY,
                List.of(low, doneHigh, medium, high)
        );

        assertEquals(List.of(high, medium, low, doneHigh), sorted);
    }

    @Test
    void sortByDeadline_shouldPutEarlierDeadlinesFirstAndNullLast() {
        Task early = createTask("Early", "", false, Task.Priority.MEDIUM, LocalDate.of(2026, 4, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task late = createTask("Late", "", false, Task.Priority.MEDIUM, LocalDate.of(2026, 5, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task noDeadline = createTask("No deadline", "", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));

        List<Task> sorted = TaskSorter.getTasksSortedBy(
                TaskSorter.Mode.BY_DEADLINE,
                List.of(noDeadline, late, early)
        );

        assertEquals(List.of(early, late, noDeadline), sorted);
    }

    @Test
    void sortLexicographically_shouldSortByTitleThenDetailsIgnoringCase() {
        Task alphaA = createTask("alpha", "a", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task alphaB = createTask("Alpha", "b", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task beta = createTask("beta", "a", false, Task.Priority.MEDIUM, null,
                LocalDateTime.of(2026, 1, 1, 10, 0));

        List<Task> sorted = TaskSorter.getTasksSortedBy(
                TaskSorter.Mode.LEXICOGRAPHIC,
                List.of(beta, alphaB, alphaA)
        );

        assertEquals(List.of(alphaA, alphaB, beta), sorted);
    }

    @Test
    void sortByImportance_shouldSortByStatusThenDeadlineThenPriority() {
        Task urgentHigh = createTask("Urgent high", "", false, Task.Priority.HIGH, LocalDate.of(2026, 4, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task urgentLow = createTask("Urgent low", "", false, Task.Priority.LOW, LocalDate.of(2026, 4, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task laterHigh = createTask("Later high", "", false, Task.Priority.HIGH, LocalDate.of(2026, 5, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));
        Task doneTask = createTask("Done", "", true, Task.Priority.HIGH, LocalDate.of(2026, 3, 1),
                LocalDateTime.of(2026, 1, 1, 10, 0));

        List<Task> sorted = TaskSorter.getTasksSortedBy(
                TaskSorter.Mode.BY_IMPORTANCE,
                List.of(doneTask, laterHigh, urgentLow, urgentHigh)
        );

        assertEquals(List.of(urgentHigh, urgentLow, laterHigh, doneTask), sorted);
    }

    private Task createTask(String title,
                            String details,
                            boolean done,
                            Task.Priority priority,
                            LocalDate deadline,
                            LocalDateTime creationDate) {
        return new Task(new JSONObject()
                .put("title", title)
                .put("details", details)
                .put("isDone", done)
                .put("priority", priority.name())
                .put("deadline", deadline == null ? JSONObject.NULL : deadline.toString())
                .put("creationDate", creationDate.toString()));
    }
}
