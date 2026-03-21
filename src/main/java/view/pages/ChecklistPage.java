package view.pages;

import controller.contracts.IChecklistPageListener;
import model.Checklist;
import model.Task;
import auxiliaries.TaskSorter;
import view.controls.*;
import view.panels.AbstractDataPanel;
import view.panels.TaskCardPanel;
import view.panels.TaskTextPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Defines a page that displays and manages a {@link Checklist}, showing its associated {@link Task}s
 * either as cards or as text. The page integrates controls for sorting, changing the checklist's
 * display mode, and options like renaming, clearing all items, or deleting the list. <p> It extends
 * {@link AbstractCollectionPage} to leverage common list-based view behaviors and UI structure. </p>
 */
public class ChecklistPage extends AbstractCollectionPage<Checklist, Task>
{
    /** Controller that handles user actions on this page. */
    private final IChecklistPageListener controller;

    /** The current sort mode for ordering displayed tasks. */
    private TaskSorter.Mode sortMode;


    /**
     * Creates a new ChecklistPage linked to the given controller. Initializes
     * the displayed list of tasks in {@link TaskSorter.Mode#OLDEST_FIRST} order.
     * @param controller the controller that processes user interactions on this page
     */
    public ChecklistPage(IChecklistPageListener controller) {
        super(new Checklist());
        this.controller = controller;
        this.sortMode = TaskSorter.Mode.OLDEST_FIRST;
        buildUI();
        refreshView();
    }


    /**
     * Updates the current sort mode and refreshes the list view.
     * @param sortMode the sort order to apply to displayed tasks
     */
    public void setSortMode(TaskSorter.Mode sortMode) {
        this.sortMode = sortMode;
        refreshView();
    }


    /**
     * Refreshes the title and displayed tasks.
     * Tasks are sorted according to the current sort mode.
     */
    @Override
    protected void refreshView() {
        titleLabel.setText(observedData.getTitle());
        refreshContent(TaskSorter.getTasksSortedBy(sortMode, observedData.getItems()));
    }


    /**
     * Builds the content area that displays tasks. Uses a scrolling panel
     * for card display mode and a flow layout inside a bordered panel for text display mode.
     * @return the content component for task display
     */
    @Override
    protected JComponent buildContentPanel() {
        if (observedData.getDisplayMode() == Checklist.DisplayMode.CARDS) {
            return super.buildContentPanel();
        }
        var contentPanel = new JPanel(new BorderLayout()); {
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.getHSBColor(0.0f, 0.0f, 0.30f)),
                    BorderFactory.createMatteBorder(30, 30, 30, 30, Color.WHITE)
                )
            );
            contentPanel.add(buildDataPanelContainer(new FlowLayout()), BorderLayout.CENTER);
        }
        return contentPanel;
    }


    /**
     * Populates the top and bottom control panels with:
     * <ul>
     *     <li>Navigation and action buttons in the header (back, menu options).</li>
     *     <li>Sorting and add-item controls in the bottom panel.</li>
     * </ul>
     * Options include renaming the checklist, clearing all tasks, toggling display mode,
     * and deleting the checklist.
     */
    @Override
    protected void fillControlPanels() {
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        options.addOption("Rename Checklist", controller::onRenameTitleRequested);
        options.addOption("Delete all Items", controller::onClearRequested);
        options.addOption("Change Display", controller::onDisplayModeToggled);
        options.addOption("Delete Checklist", () -> controller.onDeleteListRequested(observedData));
        headerControlPanel.add(new BackButton(controller::onBackButtonPressed), BorderLayout.WEST);
        headerControlPanel.add(titleLabel, BorderLayout.CENTER);
        headerControlPanel.add(new MenuButton("⌥", options), BorderLayout.EAST);
        bottomControlPanel.add(new EnumButton<>("Sort Items", TaskSorter.Mode.class, controller::onSortModeChosen), BorderLayout.WEST);
        bottomControlPanel.add(new AddButton(controller::onAddNewItemRequested), BorderLayout.EAST);
    }


    /**
     * Constructs a data panel for each displayed task based on the current display mode.
     * Returns either a {@link TaskCardPanel} or {@link TaskTextPanel}.
     * @param task the task to wrap in a panel
     * @return an appropriate panel for representing the task
     */
    @Override
    protected AbstractDataPanel<Task> buildDataPanel(Task task) {
        return switch (observedData.getDisplayMode()) {
            case CARDS -> new TaskCardPanel(task, controller);
            case TEXT -> new TaskTextPanel(task, controller);
        };
    }
}
