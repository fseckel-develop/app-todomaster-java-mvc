package view.panels;

import controller.contracts.IDataPanelListener;
import model.Checklist;
import model.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChecklistPanelTest {

    @Test
    void constructor_shouldBuildForEmptyChecklist() {
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");

        assertDoesNotThrow(() -> new ChecklistPanel(checklist, new TestListener()));
    }

    @Test
    void refresh_shouldHandleAllTasksFinishedCase() {
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        checklist.add(task("Done", true));

        assertDoesNotThrow(() -> new ChecklistPanel(checklist, new TestListener()));
    }

    @Test
    void refresh_shouldHandleOpenTasksCase() {
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        checklist.add(task("Open 1", false));
        checklist.add(task("Done 2", true));

        assertDoesNotThrow(() -> new ChecklistPanel(checklist, new TestListener()));
    }

    private Task task(String title, boolean done) {
        return new Task(new JSONObject()
                .put("title", title)
                .put("details", "")
                .put("isDone", done)
                .put("priority", "MEDIUM")
                .put("deadline", JSONObject.NULL)
                .put("creationDate", LocalDateTime.now().minusDays(1).toString()));
    }

    private static class TestListener implements IDataPanelListener<Checklist> {
        @Override
        public void onEditItemRequested(Checklist itemToEdit) {
        }

        @Override
        public void onDeleteItemRequested(Checklist itemToDelete) {
        }

        @Override
        public void onItemSelected(Checklist selectedItem) {
        }
    }
}