package controller.contracts;

import model.Checklist;
import model.Workspace;

import javax.swing.*;

/**
 * Defines an abstraction for application-level navigation and UI coordination.
 * <p>
 * Controllers use this interface to interact with the application shell
 * (for example, switching pages, accessing the main window, and retrieving
 * global data) without depending on a concrete implementation such as
 * {@code AppController}.
 * </p>
 * <p>
 * This abstraction improves testability and reduces coupling between
 * page controllers and the Swing application shell.
 * </p>
 */
public interface IAppNavigator 
{
    /**
     * Registers a page with the application so it can be displayed later.
     * @param page the UI panel representing a page; must not be {@code null}
     */
    void registerPage(JPanel page);


    /**
     * Displays the workspace page in the application.
     */
    void showWorkspacePage();


    /**
     * Displays the checklist page for the given checklist.
     * @param checklist the checklist to display; must not be {@code null}
     */
    void showChecklistPage(Checklist checklist);


    /**
     * Returns the main application window.
     * @return the root {@link JFrame}, or {@code null} in test implementations
     */
    JFrame getWindow();


    /**
     * Returns the currently active workspace.
     * @return the workspace managed by the application
     */
    Workspace getWorkspace();
}