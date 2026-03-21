package controller.mocking;

import controller.contracts.IUserDialogService;
import model.Checklist;
import model.Task;
import model.Workspace;

public class MockDialogService implements IUserDialogService {
    public Workspace workspaceRenameResult;
    public Checklist checklistCreationResult;
    public Checklist checklistRenameResult;
    public Task taskCreationResult;
    public Task taskEditResult;

    public boolean confirmDeleteWorkspaceItemResult;
    public boolean confirmDeleteChecklistResult;
    public boolean confirmDeleteTaskResult;
    public boolean confirmClearWorkspaceResult;
    public boolean confirmClearChecklistResult;

    @Override
    public Workspace requestWorkspaceRename(Workspace workspace) {
        return workspaceRenameResult;
    }

    @Override
    public Checklist requestChecklistCreation() {
        return checklistCreationResult;
    }

    @Override
    public Checklist requestChecklistRename(Checklist checklist) {
        return checklistRenameResult;
    }

    @Override
    public Task requestTaskCreation() {
        return taskCreationResult;
    }

    @Override
    public Task requestTaskEdit(Task task) {
        return taskEditResult;
    }

    @Override
    public boolean confirmDeleteWorkspaceItem(Workspace workspace) {
        return confirmDeleteWorkspaceItemResult;
    }

    @Override
    public boolean confirmDeleteChecklist(Checklist checklist) {
        return confirmDeleteChecklistResult;
    }

    @Override
    public boolean confirmDeleteTask(Task task) {
        return confirmDeleteTaskResult;
    }

    @Override
    public boolean confirmClearWorkspace(Workspace workspace) {
        return confirmClearWorkspaceResult;
    }

    @Override
    public boolean confirmClearChecklist(Checklist checklist) {
        return confirmClearChecklistResult;
    }
}
