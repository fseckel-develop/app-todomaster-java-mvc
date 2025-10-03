package controller;

import model.Checklist;
import model.Workspace;
import view.pages.WorkspacePage;
import view.pages.ChecklistPage;

import javax.swing.*;
import java.awt.*;


/**
 * The central controller for the ToDo Master application. <p>
 * This class manages the primary {@link JFrame} window and orchestrates the
 * interaction between the Workspace and Checklist pages. It also handles
 * application lifecycle behavior, such as saving data on close. </p>
 */
public class AppController
{
    /** The main application window. */
    private final JFrame window;

    /** The page that displays the current workspace. */
    private WorkspacePage workspacePage;

    /** The page that displays the current checklist. */
    private ChecklistPage checklistPage;


    /**
     * Constructs a new AppController to control the provided workspace. <p>
     * Sets up the main application window, links page controllers,
     * and prepares the workspace and checklist pages for use.
     * Also adds a listener to save data on close. </p>
     * @param data the workspace to manage
     */
    public AppController(Workspace data) {
        if (data == null) {
            throw new IllegalArgumentException("Workspace data cannot be null");
        }
        this.window = buildWindow();
        new WorkspacePageController(this, data);
        new ChecklistPageController(this);
    }


    /**
     * Constructs the main application window with a default
     * title, size, close behavior, and appearance.
     * @return a new {@link JFrame} set up for the application
     */
    private JFrame buildWindow() {
        var window = new JFrame();
        window.setSize(650, 830);
        window.setTitle("ToDo Master");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setBackground(Color.BLACK);
        window.setVisible(true);
        return window;
    }


    /**
     * Registers the given Panel as a page depending on its concrete type.
     * @param page the page to register
     */
    public void registerPage(JPanel page) {
        if (page instanceof WorkspacePage) {
            workspacePage = (WorkspacePage) page;
        } else if (page instanceof ChecklistPage) {
            checklistPage = (ChecklistPage) page;
        }
    }


    /** Displays the workspace page in the main window and updates its contents. */
    public void showWorkspacePage() {
        window.setContentPane(workspacePage);
        workspacePage.onNotify();
        window.revalidate();
        window.repaint();
    }


    /**
     * Displays the checklist page in the main window and updates its contents.
     * @param checklist the checklist to show
     */
    public void showChecklistPage(Checklist checklist) {
        window.setContentPane(checklistPage);
        checklistPage.setObservedData(checklist);
        checklistPage.onNotify();
        window.revalidate();
        window.repaint();
    }


    /** Returns the main application {@link JFrame} window. */
    public JFrame getWindow() {
        return window;
    }


    /** Returns the {@link Workspace} data observed by the Workspace Page. */
    public Workspace getWorkspace() {
        return workspacePage.getObservedData();
    }
}