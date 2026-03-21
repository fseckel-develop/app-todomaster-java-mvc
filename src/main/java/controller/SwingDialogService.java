package controller;

import controller.contracts.IUserDialogService;
import model.Checklist;
import model.Task;
import model.Workspace;
import view.dialogs.*;

import javax.swing.*;

/**
 * Swing-based implementation of {@link IUserDialogService}.
 * <p>
 * This class uses modal Swing dialogs to interact with the user.
 * It translates dialog results into return values expected by controllers.
 * </p>
 */
public class SwingDialogService implements IUserDialogService
{
    private final JFrame owner;

    /**
     * Constructs a new {@code SwingDialogService}.
     *
     * @param owner the parent frame for all dialogs; must not be {@code null}
     */
    public SwingDialogService(JFrame owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Workspace requestWorkspaceRename(Workspace workspace) {
        RenameDataDialog<Workspace> dialog = new RenameDataDialog<>(workspace, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed() ? dialog.getEditedData() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Checklist requestChecklistCreation() {
        CreateChecklistDialog dialog = new CreateChecklistDialog(owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed() ? dialog.getEditedData() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Checklist requestChecklistRename(Checklist checklist) {
        RenameDataDialog<Checklist> dialog = new RenameDataDialog<>(checklist, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed() ? dialog.getEditedData() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task requestTaskCreation() {
        CreateTaskDialog dialog = new CreateTaskDialog(owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed() ? dialog.getEditedData() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task requestTaskEdit(Task task) {
        EditTaskDialog dialog = new EditTaskDialog(task, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed() ? dialog.getEditedData() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmDeleteWorkspaceItem(Workspace workspace) {
        DeleteDataDialog<Workspace> dialog = new DeleteDataDialog<>(workspace, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmDeleteChecklist(Checklist checklist) {
        DeleteDataDialog<Checklist> dialog = new DeleteDataDialog<>(checklist, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmDeleteTask(Task task) {
        DeleteDataDialog<Task> dialog = new DeleteDataDialog<>(task, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmClearWorkspace(Workspace workspace) {
        ClearCollectionDialog<Workspace, Checklist> dialog =
                new ClearCollectionDialog<>(workspace, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmClearChecklist(Checklist checklist) {
        ClearCollectionDialog<Checklist, Task> dialog =
                new ClearCollectionDialog<>(checklist, owner);
        dialog.setVisible(true);
        return dialog.wasConfirmed();
    }
}