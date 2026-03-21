package view.pages;

import controller.contracts.IWorkspacePageListener;
import model.Checklist;
import model.Workspace;
import view.controls.AddButton;
import view.controls.MenuButton;
import view.panels.AbstractDataPanel;
import view.panels.ChecklistPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Defines a page view that displays a {@link Workspace} and its associated list of {@link Checklist}s.
 * <p> This class extends {@link AbstractCollectionPage} to manage a collection of checklists inside
 * a workspace. It provides header and bottom control panels for workspace-level actions,
 * and renders each {@link Checklist} as a {@link ChecklistPanel}. </p>
 */
public class WorkspacePage extends AbstractCollectionPage<Workspace, Checklist>
{
    /** The controller that handles user interactions for the workspace page */
    private final IWorkspacePageListener controller;


    /**
     * Creates a new WorkspacePage bound to the given workspace data and controller.
     * Builds the UI and refreshes the view immediately.
     * @param dataToObserve the workspace model to observe and display
     * @param controller the controller handling actions triggered by this page's components
     */
    public WorkspacePage(Workspace dataToObserve, IWorkspacePageListener controller) {
        super(dataToObserve);
        this.controller = controller;
        buildUI();
        refreshView();
    }


    /**
     * Fills the top and bottom control panels with components and options specific to workspaces:
     * <ul>
     *   <li>Displays the workspace title aligned to the left.</li>
     *   <li>Provides a menu button in the header with options to rename the workspace and clear all items.</li>
     *   <li>Provides an add button in the bottom panel to create new checklists.</li>
     * </ul>
     */
    @Override
    protected void fillControlPanels() {
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headerControlPanel.add(titleLabel, BorderLayout.WEST);
        options.addOption("Rename Workspace", controller::onRenameTitleRequested);
        options.addOption("Delete all Items", controller::onClearRequested);
        headerControlPanel.add(new MenuButton("⌥", options), BorderLayout.EAST);
        bottomControlPanel.add(new AddButton(controller::onAddNewItemRequested), BorderLayout.EAST);
    }


    /**
     * Creates and returns a {@link ChecklistPanel} to display
     * the checklist and provide item-level interactions.
     * @param checklist the checklist data to visualize
     * @return a new {@link AbstractDataPanel} representing the checklist
     */
    @Override
    protected AbstractDataPanel<Checklist> buildDataPanel(Checklist checklist) {
        return new ChecklistPanel(checklist, controller);
    }
}
