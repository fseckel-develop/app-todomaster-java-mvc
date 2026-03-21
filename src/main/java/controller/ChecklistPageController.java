package controller;

import controller.contracts.IAppNavigator;
import controller.contracts.IChecklistPageListener;
import controller.contracts.IUserDialogService;
import model.Checklist;
import model.Task;
import auxiliaries.TaskSorter;
import view.pages.ChecklistPage;


/**
 * Controller for the checklist page in the ToDo Master application
 * by implementing the {@link IChecklistPageListener} interface. <p>
 * It responds to user requests like renaming the checklist, creating and editing tasks,
 * sorting tasks, deleting lists and tasks, and switching between views. </p><p>
 * It delegates the navigation between pages to the {@link AppController} and relies
 * on dialog classes to gather user input or confirmation before modifying data.
 * </p>
 */
public class ChecklistPageController implements IChecklistPageListener
{
    /** The main application controller that manages view transitions. */
    private final IAppNavigator appController;

    /** The dialog service used for user interactions */
    private final IUserDialogService dialogService;

    /** The page that displays the current checklist. */
    private final ChecklistPage checklistPage;


    /**
     * Constructs a new ChecklistPageController.
     * @param appController the main application controller responsible for navigation; must not be {@code null}
     * @param dialogService the dialog service used for user interactions; must not be {@code null}
     * @throws IllegalArgumentException if {@code appController} or {@code dialogService} is {@code null}
     */
    public ChecklistPageController(IAppNavigator appController, IUserDialogService dialogService) {
        if (appController == null) {
            throw new IllegalArgumentException("AppController cannot be null");
        }
        if (dialogService == null) {
            throw new IllegalArgumentException("DialogService cannot be null");
        }
        this.appController = appController;
        this.dialogService = dialogService;
        this.checklistPage = new ChecklistPage(this);
        this.appController.registerPage(checklistPage);
    }


    /**
     * Displays a dialog to rename the checklist title.
     * Updates the checklist's title if the dialog is confirmed.
     */
    @Override
    public void onRenameTitleRequested() {
        Checklist edited = dialogService.requestChecklistRename(checklistPage.getObservedData());
        if (edited != null) {
            checklistPage.getObservedData().setTitle(edited.getTitle());
        }
    }


    /**
     * Displays a dialog to create a new task.
     * Adds the task to the checklist if the dialog is confirmed.
     */
    @Override
    public void onAddNewItemRequested() {
        Task created = dialogService.requestTaskCreation();
        if (created != null) {
            checklistPage.getObservedData().add(created);
        }
    }


    /**
     * Displays a confirmation dialog to clear all tasks in the checklist.
     * Clears the list if the dialog is confirmed.
     */
    @Override
    public void onClearRequested() {
        if (dialogService.confirmClearChecklist(checklistPage.getObservedData())) {
            checklistPage.getObservedData().clear();
        }
    }


    /**
     * Displays a confirmation dialog to delete the given checklist.
     * Removes the list from the workspace and returns to the workspace page if confirmed.
     * @param listToDelete the checklist to delete
     */
    @Override
    public void onDeleteListRequested(Checklist listToDelete) {
        if (dialogService.confirmDeleteChecklist(listToDelete)) {
            appController.getWorkspace().remove(listToDelete);
            appController.showWorkspacePage();
        }
    }


    /**
     * Sets the sort mode for the checklist's tasks.
     * @param sortMode the mode to use for sorting
     */
    @Override
    public void onSortModeChosen(TaskSorter.Mode sortMode) {
        checklistPage.setSortMode(sortMode);
    }


    /**
     * Toggles the checklist's display mode between
     * simple flowing text view and card panel view.
     * */
    @Override
    public void onDisplayModeToggled() {
        checklistPage.getObservedData().toggleDisplayMode();
    }


    /** Navigates back to the workspace page. */
    @Override
    public void onBackButtonPressed() {
        appController.showWorkspacePage();
    }


    /**
     * Displays a dialog to edit the specified task's details.
     * Updates the task's title, details, priority, and deadline if the dialog is confirmed.
     * @param itemToEdit the task to edit
     */
    @Override
    public void onEditItemRequested(Task itemToEdit) {
        Task edited = dialogService.requestTaskEdit(itemToEdit);
        if (edited != null) {
            itemToEdit.setTitle(edited.getTitle());
            itemToEdit.setDetails(edited.getDetails());
            itemToEdit.setPriority(edited.getPriority());
            itemToEdit.setDeadline(edited.getDeadline());
        }
    }


    /**
     * Displays a confirmation dialog to delete a specific task.
     * Removes the task if the dialog is confirmed.
     * @param taskToDelete the task to delete
     */
    @Override
    public void onDeleteItemRequested(Task taskToDelete) {
        if (dialogService.confirmDeleteTask(taskToDelete)) {
            checklistPage.getObservedData().remove(taskToDelete);
        }
    }


    /**
     * Toggles the completion status of the selected task and refreshes the checklist page.
     * @param selectedTask the task that was selected
     */
    @Override
    public void onItemSelected(Task selectedTask) {
        selectedTask.toggleDone();
        appController.showChecklistPage(checklistPage.getObservedData());
    }
}
