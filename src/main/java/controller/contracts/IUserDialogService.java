package controller.contracts;

import model.Checklist;
import model.Task;
import model.Workspace;

/**
 * Defines an abstraction for all user dialog interactions.
 * <p>
 * Controllers use this interface to request user input or confirmation
 * without depending on concrete UI implementations (e.g., Swing dialogs).
 * </p>
 * <p>
 * Implementations may use any UI technology (Swing, JavaFX, CLI, mocks for testing, etc.).
 * </p>
 */
public interface IUserDialogService
{
    /**
     * Requests a new title for the given workspace.
     * @param workspace the workspace to rename
     * @return the edited workspace if confirmed, or {@code null} if cancelled
     */
    Workspace requestWorkspaceRename(Workspace workspace);


    /**
     * Requests creation of a new checklist.
     * @return the created checklist if confirmed, or {@code null} if cancelled
     */
    Checklist requestChecklistCreation();


    /**
     * Requests a new title for the given checklist.
     * @param checklist the checklist to rename
     * @return the edited checklist if confirmed, or {@code null} if cancelled
     */
    Checklist requestChecklistRename(Checklist checklist);


    /**
     * Requests creation of a new task.
     * @return the created task if confirmed, or {@code null} if cancelled
     */
    Task requestTaskCreation();


    /**
     * Requests editing of an existing task.
     * @param task the task to edit
     * @return the edited task if confirmed, or {@code null} if cancelled
     */
    Task requestTaskEdit(Task task);


    /**
     * Requests confirmation to delete a workspace item.
     * @param workspace the workspace to delete
     * @return {@code true} if confirmed, otherwise {@code false}
     */
    boolean confirmDeleteWorkspaceItem(Workspace workspace);


    /**
     * Requests confirmation to delete a checklist.
     * @param checklist the checklist to delete
     * @return {@code true} if confirmed, otherwise {@code false}
     */
    boolean confirmDeleteChecklist(Checklist checklist);


    /**
     * Requests confirmation to delete a task.
     * @param task the task to delete
     * @return {@code true} if confirmed, otherwise {@code false}
     */
    boolean confirmDeleteTask(Task task);


    /**
     * Requests confirmation to clear all items in a workspace.
     * @param workspace the workspace to clear
     * @return {@code true} if confirmed, otherwise {@code false}
     */
    boolean confirmClearWorkspace(Workspace workspace);

    
    /**
     * Requests confirmation to clear all items in a checklist.
     * @param checklist the checklist to clear
     * @return {@code true} if confirmed, otherwise {@code false}
     */
    boolean confirmClearChecklist(Checklist checklist);
}