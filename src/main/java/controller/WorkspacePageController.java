package controller;

import controller.contracts.IAppNavigator;
import controller.contracts.IUserDialogService;
import controller.contracts.IWorkspacePageListener;
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
    private final IAppNavigator appController;

    /** The dialog service used for user interactions */
    private final IUserDialogService dialogService;

    /** The page that displays the current workspace. */
    private final WorkspacePage workspacePage;


    /**
     * Constructs a new {@code WorkspacePageController}.
     * @param appController the main application controller responsible for navigation; must not be {@code null}
     * @param data the workspace data to be managed; must not be {@code null}
     * @param dialogService the dialog service used for user interactions; must not be {@code null}
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public WorkspacePageController(IAppNavigator appController, Workspace data, IUserDialogService dialogService) {
        if (appController == null) {
            throw new IllegalArgumentException("AppController cannot be null");
        } else if (data == null) {
            throw new IllegalArgumentException("Workspace data cannot be null");
        } else if (dialogService == null) {
            throw new IllegalArgumentException("DialogService cannot be null");
        }
        this.appController = appController;
        this.dialogService = dialogService;
        this.workspacePage = new WorkspacePage(data, this);
        this.appController.registerPage(workspacePage);
    }


    /**
     * Displays a dialog to rename the current workspace.
     * Updates the workspace title if the user confirms the action.
     */
    @Override
    public void onRenameTitleRequested() {
        Workspace edited = dialogService.requestWorkspaceRename(workspacePage.getObservedData());
        if (edited != null) {
            workspacePage.getObservedData().setTitle(edited.getTitle());
        }
    }


    /**
     * Displays a dialog to create a new checklist.
     * Adds the new checklist to the workspace if the user confirms the action.
     */
    @Override
    public void onAddNewItemRequested() {
        Checklist created = dialogService.requestChecklistCreation();
        if (created != null) {
            workspacePage.getObservedData().add(created);
        }
    }


    /**
     * Displays a confirmation dialog to clear all checklists in the workspace.
     * Clears the workspace if the user confirms the action.
     */
    @Override
    public void onClearRequested() {
        if (dialogService.confirmClearWorkspace(workspacePage.getObservedData())) {
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
        Checklist edited = dialogService.requestChecklistRename(itemToEdit);
        if (edited != null) {
            itemToEdit.setTitle(edited.getTitle());
        }
    }


    /**
     * Displays a confirmation dialog to delete the specified checklist.
     * Removes the checklist if the user confirms the action.
     * @param listToDelete the checklist to delete
     */
    @Override
    public void onDeleteItemRequested(Checklist listToDelete) {
        if (dialogService.confirmDeleteChecklist(listToDelete)) {
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