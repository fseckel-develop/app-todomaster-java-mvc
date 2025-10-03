package view.panels;

import controller.ChecklistPageController;
import model.Task;
import auxiliaries.FontLoader;
import view.controls.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;


/**
 * Defines a panel that visually represents a {@link Task} in the application. <p>
 * This component displays:
 * <ul>
 *     <li>A checkbox indicator showing whether the task is completed.</li>
 *     <li>A color-coded priority banner to visually indicate task priority.</li>
 *     <li>The title and optional details of the task.</li>
 *     <li>The deadline status, including whether it's missed, due, or completed.</li>
 *     <li>An options menu for editing or deleting the task.</li>
 * </ul>
 * It updates its display automatically when the observed task is modified.
 * @see AbstractDataPanel
 * @see Task
 */
public class TaskCardPanel extends AbstractDataPanel<Task>
{
    /** A label showing a checkbox or checkmark to indicate the task's completion status. */
    private JLabel isDoneLabel;

    /** A visual banner that is color-coded to reflect the priority level of the task. */
    private JPanel priorityBanner;

    /** A label showing the task's optional details (description), if provided. */
    private JLabel detailsLabel;

    /** A label showing the task's deadline status, or "finished" if completed. */
    private JLabel deadlineLabel;


    /**
     * Constructs a new {@code TaskCardPanel}. <p>
     * Initializes the panel with the given task and controller,
     * then builds the UI and renders the current task state. </p>
     * @param dataToObserve the task to display
     * @param controller the controller to receive edit, delete, and select events
     * @throws IllegalArgumentException if the controller is null
     */
    public TaskCardPanel(Task dataToObserve, ChecklistPageController controller) {
        super(dataToObserve, controller);
        buildUI();
        refreshView();
    }


    /**
     * Builds all graphical components of the panel. <p>
     * Arranges the components in a {@link BorderLayout} and organizes subpanels for:
     * the checkbox icon, priority banner, title/details, and deadline/options. </p>
     */
    @Override
    protected void buildUI() {
        super.buildUI();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        {
            isDoneLabel = new JLabel();
            isDoneLabel.setFont(new Font("Apple Color Emoji", Font.PLAIN, 16));
            isDoneLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            add(isDoneLabel, BorderLayout.WEST);
        }
        {
            var taskCardPanel = new JPanel(new BorderLayout());
            taskCardPanel.setBackground(Color.WHITE);
            taskCardPanel.add(buildPriorityPanel(), BorderLayout.WEST);
            taskCardPanel.add(buildTitleDetailsPanel(), BorderLayout.CENTER);
            taskCardPanel.add(buildDeadlineOptionsPanel(), BorderLayout.EAST);
            add(taskCardPanel, BorderLayout.CENTER);
        }
    }


    /**
     * Refreshes all view elements to reflect the latest data in the observed task.
     * Updates the checkbox, title, details, priority banner, and deadline label.
     */
    @Override
    protected void refreshView() {
        refreshIsDoneLabelText();
        refreshPriorityBanner();
        titleLabel.setText(observedData.getTitle());
        titleLabel.setForeground(observedData.isDone() ? Color.GRAY : Color.BLACK);
        refreshDetailsLabel();
        refreshDeadlineLabel();
    }


    /** {@inheritDoc} */
    @Override
    protected String thisEditOptionText() {
        return "Edit Task";
    }


    /**
     * Creates a panel containing the priority banner.
     * @return a new panel with the priority banner attached
     */
    private JPanel buildPriorityPanel() {
        var priorityPanel = new JPanel(new BorderLayout()); {
            priorityPanel.setBackground(Color.WHITE);
            priorityPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            priorityBanner = buildStripePanel();
            priorityPanel.add(priorityBanner, BorderLayout.WEST);
        }
        return priorityPanel;
    }


    /**
     * Creates a panel containing the task's title and optional details.
     * @return a new panel containing title and details
     */
    private JPanel buildTitleDetailsPanel() {
        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.setBackground(Color.WHITE);
        {
            JPanel centeringContentPanel = new JPanel();
            centeringContentPanel.setLayout(new BoxLayout(centeringContentPanel, BoxLayout.Y_AXIS));
            centeringContentPanel.setBackground(Color.WHITE);
            centeringContentPanel.add(Box.createVerticalGlue());
            {
                titleLabel = new JLabel(observedData.getTitle());
                titleLabel.setFont(FontLoader.getFont("FiraSans", Font.BOLD, 15));
                titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
                titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                centeringContentPanel.add(titleLabel);
            }
            centeringContentPanel.add(Box.createVerticalStrut(5));
            {
                detailsLabel = new JLabel();
                detailsLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 12));
                detailsLabel.setForeground(Color.DARK_GRAY);
                detailsLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
                detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                centeringContentPanel.add(detailsLabel);
            }
            centeringContentPanel.add(Box.createVerticalGlue());
            titleDescriptionPanel.add(centeringContentPanel, BorderLayout.CENTER);
        }
        return titleDescriptionPanel;
    }


    /**
     * Creates a panel containing the task's deadline status and the options menu.
     * @return a new panel containing the deadline label and options button
     */
    private JPanel buildDeadlineOptionsPanel() {
        var deadlineOptionsPanel = new JPanel(); {
            deadlineOptionsPanel.setLayout(new BoxLayout(deadlineOptionsPanel, BoxLayout.X_AXIS));
            deadlineOptionsPanel.setBackground(Color.WHITE);
            deadlineOptionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            {
                deadlineLabel = new JLabel();
                deadlineLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 12));
                deadlineLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                refreshDeadlineLabel();
                deadlineOptionsPanel.add(deadlineLabel);
            }
            deadlineOptionsPanel.add(Box.createHorizontalStrut(20));
            {
                var optionsButton = new MenuButton("⋯", options);
                deadlineOptionsPanel.add(optionsButton);
            }
        }
        return deadlineOptionsPanel;
    }


    /** Updates the checkbox label to "✔" if completed, or "▢" if not completed. */
    private void refreshIsDoneLabelText() {
        isDoneLabel.setText(observedData.isDone() ? "✔" : "▢");
    }


    /**
     * Updates the priority banner's color and border according
     * to the task's completion status and priority level.
     */
    private void refreshPriorityBanner() {
        if (observedData.isDone()) {
            priorityBanner.setBackground(Color.getHSBColor(0.0f, 0.00f, 0.90f));
            priorityBanner.setBorder(BorderFactory.createMatteBorder(
                    0, 1, 0, 1, Color.getHSBColor(0.0f, 0.00f, 0.60f)
            ));
            return;
        }
        switch (observedData.getPriority()) {
            case HIGH:
                priorityBanner.setBackground(Color.getHSBColor(0.0f, 0.30f, 0.98f));
                priorityBanner.setBorder(BorderFactory.createMatteBorder(
                        0, 1, 0, 1, Color.getHSBColor(0.0f, 1.00f, 0.60f)
                ));
                break;
            case MEDIUM:
                priorityBanner.setBackground(Color.getHSBColor(45.0f / 360.0f, 0.30f, 0.95f));
                priorityBanner.setBorder(BorderFactory.createMatteBorder(
                        0, 1, 0, 1, Color.getHSBColor(45.0f / 360.0f, 1.00f, 0.60f)
                ));
                break;
            case LOW:
                priorityBanner.setBackground(Color.getHSBColor(115.0f / 360.0f, 0.30f, 0.90f));
                priorityBanner.setBorder(BorderFactory.createMatteBorder(
                        0, 1, 0, 1, Color.getHSBColor(115.0f / 360.0f, 1.00f, 0.60f)
                ));
                break;
        }
    }


    /**
     * Updates the details label with the task's description.
     * If the task has no details, the label is hidden.
     */
    private void refreshDetailsLabel() {
        String details = observedData.getDetails();
        boolean hasDescription = details != null && !details.trim().isEmpty();
        detailsLabel.setText(hasDescription ? "<html><i>" + details + "</i></html>" : "");
        detailsLabel.setVisible(hasDescription);
        detailsLabel.setForeground(observedData.isDone() ? Color.GRAY : Color.BLACK);
        revalidate();
        repaint();
    }


    /**
     * Updates the deadline label with the task's deadline status. <p>
     * Displays "finished" if completed, "due on ..." if the deadline
     * is upcoming, or "missed ..." if the deadline has passed.
     * </p>
     */
    private void refreshDeadlineLabel() {
        if (observedData.isDone()) {
            deadlineLabel.setText("finished");
            deadlineLabel.setForeground(Color.DARK_GRAY);
            return;
        }
        if (observedData.getDeadline() != null) {
            String deadlineAsString = observedData.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            if (observedData.deadlineMissed()) {
                deadlineLabel.setText("missed " + deadlineAsString);
                deadlineLabel.setForeground(Color.RED);
            } else {
                deadlineLabel.setText("due on " + deadlineAsString);
                deadlineLabel.setForeground(Color.DARK_GRAY);
            }
        }
    }
}
