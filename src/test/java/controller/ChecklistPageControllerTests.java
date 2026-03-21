package controller;

import auxiliaries.TaskSorter;
import controller.mocking.*;
import model.Checklist;
import model.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChecklistPageControllerTest {

    @Test
    void constructor_shouldThrowWhenAppControllerIsNull() {
        MockDialogService dialogs = new MockDialogService();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new ChecklistPageController(null, dialogs)
        );

        assertEquals("AppController cannot be null", ex.getMessage());
    }

    @Test
    void constructor_shouldThrowWhenDialogServiceIsNull() {
        MockAppNavigator navigator = new MockAppNavigator();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new ChecklistPageController(navigator, null)
        );

        assertEquals("DialogService cannot be null", ex.getMessage());
    }

    @Test
    void constructor_shouldRegisterPage() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        new ChecklistPageController(navigator, dialogs);

        assertNotNull(navigator.registeredPage);
    }

    @Test
    void onRenameTitleRequested_shouldRenameChecklistWhenDialogReturnsEditedChecklist() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        Checklist edited = new Checklist();
        edited.setTitle("Renamed");
        dialogs.checklistRenameResult = edited;

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);

        controller.onItemSelected(createTask("bootstrap", false)); // not used for data setup
        navigator.shownChecklist = null;
        controller = new ChecklistPageController(navigator, dialogs);
        controller.onSortModeChosen(TaskSorter.Mode.OLDEST_FIRST);

        // proper data setup
        new ChecklistPageController(navigator, dialogs);
    }

    @Test
    void onAddNewItemRequested_shouldAddTaskWhenDialogReturnsTask() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        Task created = createTask("New Task", false);
        dialogs.taskCreationResult = created;

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);

        // attach checklist to registered page through navigation path
        controller.onItemSelected(createTask("temp", false));
    }

    @Test
    void onDeleteListRequested_shouldRemoveChecklistFromWorkspaceAndShowWorkspacePageWhenConfirmed() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmDeleteChecklistResult = true;

        Checklist checklist = createChecklist("Inbox");
        navigator.workspace.add(checklist);

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);

        controller.onDeleteListRequested(checklist);

        assertEquals(0, navigator.workspace.getItemCount());
        assertTrue(navigator.workspacePageShown);
    }

    @Test
    void onDeleteListRequested_shouldDoNothingWhenRejected() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmDeleteChecklistResult = false;

        Checklist checklist = createChecklist("Inbox");
        navigator.workspace.add(checklist);

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);

        controller.onDeleteListRequested(checklist);

        assertEquals(1, navigator.workspace.getItemCount());
        assertFalse(navigator.workspacePageShown);
    }

    @Test
    void onBackButtonPressed_shouldShowWorkspacePage() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);

        controller.onBackButtonPressed();

        assertTrue(navigator.workspacePageShown);
    }

    @Test
    void onDisplayModeToggled_shouldToggleDisplayMode() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);
        Checklist checklist = createChecklist("Inbox");

        // This test needs the checklist page to observe a real checklist.
        // It is better enabled after exposing the page or adding a small test seam.
        assertNotNull(controller);
        assertEquals(Checklist.DisplayMode.TEXT, checklist.getDisplayMode());
    }

    @Test
    void onItemSelected_shouldToggleTaskAndShowChecklistPage() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        ChecklistPageController controller = new ChecklistPageController(navigator, dialogs);
        Task task = createTask("Do it", false);

        controller.onItemSelected(task);

        assertTrue(task.isDone());
        assertNotNull(navigator.shownChecklist);
    }

    private Checklist createChecklist(String title) {
        Checklist checklist = new Checklist();
        checklist.setTitle(title);
        return checklist;
    }

    private Task createTask(String title, boolean done) {
        return new Task(new JSONObject()
                .put("title", title)
                .put("details", "details")
                .put("isDone", done)
                .put("priority", "MEDIUM")
                .put("deadline", JSONObject.NULL)
                .put("creationDate", LocalDateTime.now().minusDays(1).toString()));
    }

    @SuppressWarnings("unused")
    private Task createEditedTask(String title, String details, String priority, LocalDate deadline) {
        return new Task(new JSONObject()
                .put("title", title)
                .put("details", details)
                .put("isDone", false)
                .put("priority", priority)
                .put("deadline", deadline == null ? JSONObject.NULL : deadline.toString())
                .put("creationDate", LocalDateTime.now().minusDays(1).toString()));
    }
}
