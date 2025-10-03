package controller.contracts;

import auxiliaries.TaskSorter;
import model.Checklist;
import model.Task;

/**
 * Defines a listener interface for handling user interactions on a checklist page.
 * <p>
 * This interface extends {@link ICollectionPageListener} to support actions on a checklist's title and its items,
 * and {@link IDataPanelListener} to support edit, delete, and selection of individual {@link Task}s.
 * </p>
 * <p>
 * Implementing classes respond to user input in the checklist page's UI,
 * including list-level and task-level operations as well as view changes.
 * </p>
 */
public interface IChecklistPageListener extends ICollectionPageListener, IDataPanelListener<Task> {


    /**
     * Called when the user requests to delete the entire checklist.
     * Implementing classes should prompt for confirmation and
     * remove the list from its containing {@link model.Workspace}.
     * @param listToDelete the {@link Checklist} that the user intends to delete
     */
    void onDeleteListRequested(Checklist listToDelete);


    /**
     * Called when the user chooses a new sort mode for the checklist's tasks.
     * Implementing classes should apply the specified {@link TaskSorter.Mode}
     * to reorder the displayed tasks.
     * @param sortMode the sort mode selected by the user
     */
    void onSortModeChosen(TaskSorter.Mode sortMode);


    /**
     * Called when the user toggles the checklist's display mode
     * (e.g. showing all tasks vs. only incomplete tasks).
     * Implementing classes should switch between the display modes.
     */
    void onDisplayModeToggled();


    /**
     * Called when the user presses a "back" button on the checklist page,
     * indicating that they want to navigate back to the workspace page.
     */
    void onBackButtonPressed();
}
