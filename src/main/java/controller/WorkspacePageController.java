package controller;

import controller.contracts.IWorkspacePageListener;
import view.dialogs.*;
import model.*;
import view.pages.WorkspacePage;


/**
 * Controller for the workspace page in the ToDo Master application
 * by implementing the {@link IWorkspacePageListener} interface. <p>
 * It manages operations on the current {@link Workspace}, such as renaming the
 * workspace, creating new checklists, clearing all checklists, and handling
 * checklist-specific actions like edit, delete, or selection. </p><p>
 * It delegates the navigation between pages to the {@link AppController} and relies
 * on dialog classes to gather user input or confirmation before modifying data. </p>
 */
public class WorkspacePageController implements IWorkspacePageListener
{
    /** The main application controller for navigating between pages. */
    private final AppController appController;

    /** The page that displays the current workspace. */
    private final WorkspacePage workspacePage;


    /**
     * Constructs a new {@code WorkspacePageController}.
     * @param appController the main application controller for view/page management
     * @param data the workspace data to be controlled
     * @throws IllegalArgumentException if the navigator or data is null
     */
    public WorkspacePageController(AppController appController, Workspace data) {
        if (appController == null) {
            throw new IllegalArgumentException("AppController cannot be null");
        } else if (data == null) {
            throw new IllegalArgumentException("Workspace data cannot be null");
        }
        this.appController = appController;
        this.workspacePage = new WorkspacePage(data, this);
        this.appController.registerPage(workspacePage);
    }


    /**
     * Displays a dialog to rename the current workspace.
     * Updates the workspace title if the user confirms the action.
     */
    @Override
    public void onRenameTitleRequested() {
        RenameDataDialog<Workspace> dialog =
                new RenameDataDialog<>(workspacePage.getObservedData(), appController.getWindow());
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            workspacePage.getObservedData().setTitle(dialog.getEditedData().getTitle());
        }
    }


    /**
     * Displays a dialog to create a new checklist.
     * Adds the new checklist to the workspace if the user confirms the action.
     */
    @Override
    public void onAddNewItemRequested() {
        CreateChecklistDialog dialog = new CreateChecklistDialog(appController.getWindow());
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            workspacePage.getObservedData().add(dialog.getEditedData());
        }
    }


    /**
     * Displays a confirmation dialog to clear all checklists in the workspace.
     * Clears the workspace if the user confirms the action.
     */
    @Override
    public void onClearRequested() {
        ClearCollectionDialog<Workspace, Checklist> dialog =
                new ClearCollectionDialog<>(workspacePage.getObservedData(), appController.getWindow());
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            workspacePage.getObservedData().clear();
        }
    }


    /**
     * Displays a dialog allowing the user to rename the given checklist.
     * Updates the checklist title if the user confirms the action.
     * @param itemToEdit the checklist to edit
     */
    @Override
    public void onEditItemRequested(Checklist itemToEdit) {
        RenameDataDialog<Checklist> dialog =
                new RenameDataDialog<>(itemToEdit, appController.getWindow());
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            itemToEdit.setTitle(dialog.getEditedData().getTitle());
        }
    }


    /**
     * Displays a confirmation dialog to delete the specified checklist.
     * Removes the checklist if the user confirms the action.
     * @param listToDelete the checklist to delete
     */
    @Override
    public void onDeleteItemRequested(Checklist listToDelete) {
        DeleteDataDialog<Checklist> dialog =
                new DeleteDataDialog<>(listToDelete, appController.getWindow());
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            workspacePage.getObservedData().remove(listToDelete);
        }
    }


    /**
     * Handles the event when a checklist is selected.
     * Navigates to the checklist page to display its contents.
     * @param selectedList the selected checklist
     */
    @Override
    public void onItemSelected(Checklist selectedList) {
        appController.showChecklistPage(selectedList);
    }
}