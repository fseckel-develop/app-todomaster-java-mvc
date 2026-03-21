package view.panels;

import controller.contracts.IDataPanelListener;
import model.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTextPanelTests {

    @Test
    void constructor_shouldBuildWithoutThrowingForNormalTask() {
        Task task = task("Buy milk", "", false, LocalDate.now().plusDays(1));
        TestListener listener = new TestListener();

        assertDoesNotThrow(() -> new TaskTextPanel(task, listener));
    }

    @Test
    void createToolTip_shouldReturnTooltipWithBorder() {
        Task task = task("Buy milk", "2 liters", false, LocalDate.now().plusDays(1));
        TaskTextPanel panel = new TaskTextPanel(task, new TestListener());

        JToolTip toolTip = panel.createToolTip();

        assertNotNull(toolTip);
        assertNotNull(toolTip.getBorder());
    }

    @Test
    void refresh_shouldHandleDoneTask() {
        Task task = task("Done task", "details", true, LocalDate.now().minusDays(1));

        assertDoesNotThrow(() -> new TaskTextPanel(task, new TestListener()));
    }

    @Test
    void refresh_shouldHandleMissedDeadlineTask() {
        Task task = task("Late task", "details", false, LocalDate.now().minusDays(1));

        assertDoesNotThrow(() -> new TaskTextPanel(task, new TestListener()));
    }

    private Task task(String title, String details, boolean done, LocalDate deadline) {
        return new Task(new JSONObject()
                .put("title", title)
                .put("details", details)
                .put("isDone", done)
                .put("priority", "MEDIUM")
                .put("deadline", deadline == null ? JSONObject.NULL : deadline.toString())
                .put("creationDate", LocalDateTime.now().minusDays(2).toString()));
    }

    private static class TestListener implements IDataPanelListener<Task> {
        @Override
        public void onEditItemRequested(Task itemToEdit) {
        }

        @Override
        public void onDeleteItemRequested(Task itemToDelete) {
        }

        @Override
        public void onItemSelected(Task selectedItem) {
        }
    }
}
