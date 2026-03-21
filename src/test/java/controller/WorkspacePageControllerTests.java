package controller;

import controller.mocking.*;
import model.Checklist;
import model.Workspace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkspacePageControllerTest {

    @Test
    void constructor_shouldThrowWhenAppControllerIsNull() {
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new WorkspacePageController(null, workspace, dialogs)
        );

        assertEquals("AppController cannot be null", ex.getMessage());
    }

    @Test
    void constructor_shouldThrowWhenWorkspaceIsNull() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new WorkspacePageController(navigator, null, dialogs)
        );

        assertEquals("Workspace data cannot be null", ex.getMessage());
    }

    @Test
    void constructor_shouldThrowWhenDialogServiceIsNull() {
        MockAppNavigator navigator = new MockAppNavigator();
        Workspace workspace = new Workspace();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new WorkspacePageController(navigator, workspace, null)
        );

        assertEquals("DialogService cannot be null", ex.getMessage());
    }

    @Test
    void constructor_shouldRegisterPage() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        new WorkspacePageController(navigator, workspace, dialogs);

        assertNotNull(navigator.registeredPage);
    }

    @Test
    void onRenameTitleRequested_shouldRenameWorkspaceWhenDialogReturnsEditedWorkspace() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        Workspace edited = new Workspace();
        edited.setTitle("Renamed Workspace");
        dialogs.workspaceRenameResult = edited;

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onRenameTitleRequested();

        assertEquals("Renamed Workspace", workspace.getTitle());
    }

    @Test
    void onRenameTitleRequested_shouldDoNothingWhenDialogReturnsNull() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();
        workspace.setTitle("Original");

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onRenameTitleRequested();

        assertEquals("Original", workspace.getTitle());
    }

    @Test
    void onAddNewItemRequested_shouldAddChecklistWhenDialogReturnsChecklist() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        dialogs.checklistCreationResult = checklist;

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onAddNewItemRequested();

        assertEquals(1, workspace.getItemCount());
        assertSame(checklist, workspace.getItems().getFirst());
    }

    @Test
    void onAddNewItemRequested_shouldDoNothingWhenDialogReturnsNull() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onAddNewItemRequested();

        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void onClearRequested_shouldClearWorkspaceWhenConfirmed() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmClearWorkspaceResult = true;

        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        workspace.add(checklist);

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onClearRequested();

        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void onClearRequested_shouldNotClearWorkspaceWhenRejected() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmClearWorkspaceResult = false;

        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        workspace.add(checklist);

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onClearRequested();

        assertEquals(1, workspace.getItemCount());
    }

    @Test
    void onEditItemRequested_shouldRenameChecklistWhenDialogReturnsEditedChecklist() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        Checklist checklist = new Checklist();
        checklist.setTitle("Old");

        Checklist edited = new Checklist();
        edited.setTitle("New");
        dialogs.checklistRenameResult = edited;

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onEditItemRequested(checklist);

        assertEquals("New", checklist.getTitle());
    }

    @Test
    void onEditItemRequested_shouldDoNothingWhenDialogReturnsNull() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        Checklist checklist = new Checklist();
        checklist.setTitle("Old");

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onEditItemRequested(checklist);

        assertEquals("Old", checklist.getTitle());
    }

    @Test
    void onDeleteItemRequested_shouldRemoveChecklistWhenConfirmed() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmDeleteChecklistResult = true;

        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        workspace.add(checklist);

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onDeleteItemRequested(checklist);

        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void onDeleteItemRequested_shouldNotRemoveChecklistWhenRejected() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        dialogs.confirmDeleteChecklistResult = false;

        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        workspace.add(checklist);

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onDeleteItemRequested(checklist);

        assertEquals(1, workspace.getItemCount());
    }

    @Test
    void onItemSelected_shouldShowChecklistPage() {
        MockAppNavigator navigator = new MockAppNavigator();
        MockDialogService dialogs = new MockDialogService();
        Workspace workspace = new Workspace();

        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");

        WorkspacePageController controller = new WorkspacePageController(navigator, workspace, dialogs);

        controller.onItemSelected(checklist);

        assertSame(checklist, navigator.shownChecklist);
    }
}
