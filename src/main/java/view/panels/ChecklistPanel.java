package view.panels;

import controller.WorkspacePageController;
import model.Checklist;
import auxiliaries.FontLoader;
import view.controls.MenuButton;

import javax.swing.*;
import java.awt.*;


/**
 * Defines a panel that visually represents a {@link Checklist} object. <p>
 * This panel displays the title of the checklist, its completion status,
 * and provides an options menu for editing or deleting the checklist.
 * It updates dynamically when the observed checklist changes. </p>
 * @see AbstractDataPanel
 * @see Checklist
 */
public class ChecklistPanel extends AbstractDataPanel<Checklist>
{
    /**
     * Label that displays the completion status of the checklist
     * (e.g. number of unfinished tasks, empty, all tasks finished).
     */
    private JLabel completionLabel;


    /**
     * Constructs a new {@code ChecklistPanel}. <p>
     * Initializes the panel with a reference to the checklist and a controller.
     * It then builds the UI and refreshes the displayed data. </p>
     * @param dataToObserve  the checklist to observe
     * @param controller the controller that handles user actions for this panel
     * @throws IllegalArgumentException if {@code controller} is {@code null}
     */
    public ChecklistPanel(Checklist dataToObserve, WorkspacePageController controller) {
        super(dataToObserve, controller);
        buildUI();
        refreshView();
    }


    /**
     * Builds the graphical components of the panel, setting up a horizontal layout with:
     * <ul>
     *   <li>Stripe panels for visual decoration</li>
     *   <li>The checklist title label</li>
     *   <li>The completion label showing checklist status</li>
     *   <li>An options menu button for edit and delete actions</li>
     * </ul>
     */
    @Override
    protected void buildUI() {
        super.buildUI();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);
        add(Box.createHorizontalStrut(10));
        {
            add(buildStripePanel());
            add(Box.createHorizontalStrut(3));
            add(buildStripePanel());
        }
        add(Box.createHorizontalStrut(25));
        {
            titleLabel = new JLabel();
            titleLabel.setText(observedData.getTitle());
            titleLabel.setFont(FontLoader.getFont("FiraSans", Font.BOLD, 16));
            add(titleLabel);
        }
        add(Box.createHorizontalGlue());
        {
            completionLabel = new JLabel();
            completionLabel.setText(thisCompletionLabelText());
            completionLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 12));
            completionLabel.setForeground(Color.DARK_GRAY);
            add(completionLabel);
        }
        add(Box.createHorizontalStrut(20));
        {
            var optionsButton = new MenuButton("⋯", options);
            add(optionsButton);
        }
        add(Box.createHorizontalStrut(10));
    }


    /**
     * Refreshes the view to reflect the latest state of the observed checklist.
     * <p> Updates the title label and the completion status label. </p>
     */
    @Override
    protected void refreshView() {
        titleLabel.setText(observedData.getTitle());
        completionLabel.setText(thisCompletionLabelText());
    }


    /**
     * Provides the label text for the "Edit" option in the options menu.
     * @return a string describing the edit action, which is {@code "Rename Checklist"}
     */
    @Override
    protected String thisEditOptionText() {
        return "Rename Checklist";
    }


    /**
     * Computes a user-friendly string representing the completion status of the checklist.
     * Depending on the number of tasks, this returns:
     * <ul>
     *   <li>{@code "empty"} if there are no tasks</li>
     *   <li>{@code "all tasks finished"} if all tasks are completed</li>
     *   <li>{@code "contains N unfinished task(s)"} if some tasks are incomplete</li>
     * </ul>
     * @return string summarizing the checklist's completion status
     */
    private String thisCompletionLabelText() {
        if (observedData.getItemCount() == 0) {
            return "empty";
        }
        int numberOfOpenTasks = observedData.getNumberOfOpenTasks();
        if (numberOfOpenTasks == 0) {
            return "all tasks finished";
        } else if (numberOfOpenTasks == 1) {
            return "contains " + numberOfOpenTasks + " unfinished task";
        }
        return "contains " + numberOfOpenTasks + " unfinished tasks";
    }
}
