package view.dialogs;

import model.Task;
import auxiliaries.FontLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;


/**
 * Defines a dialog for editing an existing {@link Task} in the application. <p>
 * This dialog allows the user to modify the task's title, details, priority,
 * and optional deadline. It validates the inputs before allowing the user
 * to confirm their changes. </p>
 */
public class EditTaskDialog extends AbstractTitleEditDialog<Task>
{
    /** Text area where the user can edit the task's details. */
    private JTextArea detailsInputArea;

    /** Button group containing radio buttons for choosing the task's priority. */
    private ButtonGroup priorityButtonGroup;

    /** Label for the deadline field, which may turn red if the deadline is invalid. */
    private JLabel deadlineLabel;

    /** Spinner allowing the user to select the task's deadline. */
    private JSpinner deadlineSpinner;

    /** Checkbox allowing the user to indicate that the task has no deadline. */
    private JCheckBox noDeadlineCheckBox;


    /**
     * Constructs a new {@code EditTaskDialog} for the given task.
     * @param taskToEdit the task that is being edited
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public EditTaskDialog(Task taskToEdit, JFrame owningWindow) {
        super("Editing Task", taskToEdit, owningWindow);
        setPreferredSize(new Dimension(370, 330));
        buildUI();
    }


    /**
     * Fills the interactive UI components with the task's
     * existing title, details, priority, and deadline.
     */
    @Override
    protected void fillInInitialData() {
        titleInputField.setText(dataToWorkWith.getTitle());
        selectInitialPriorityFromData();
        detailsInputArea.setText(dataToWorkWith.getDetails());
        selectInitialDeadlineFromData();
    }


    /**
     * {@inheritDoc}
     * @return the modified {@link Task} with updated fields
     */
    @Override
    public Task getEditedData() {
        dataToWorkWith.setTitle(titleInputField.getText());
        dataToWorkWith.setDetails(detailsInputArea.getText());
        dataToWorkWith.setPriority(getPriorityFromSelection());
        dataToWorkWith.setDeadline(getDeadlineFromSelection());
        return dataToWorkWith;
    }


    /**
     * Builds the content panel by assembling the title input,
     * priority options, details panel, and deadline panel.
     */
    @Override
    protected JPanel buildContentPanel() {
        var contentPanel = super.buildContentPanel(); {
            {
                JPanel priorityInputPanel = buildPriorityInputPanel();
                priorityInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                priorityInputPanel.setBackground(null);
                contentPanel.add(priorityInputPanel, BorderLayout.CENTER);
            }
            JPanel descriptionDeadlinePanel = new JPanel(new BorderLayout());
            descriptionDeadlinePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            descriptionDeadlinePanel.setBackground(null);
            {
                JPanel detailsPanel = buildDetailsPanel();
                detailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
                detailsPanel.setBackground(null);
                descriptionDeadlinePanel.add(detailsPanel, BorderLayout.CENTER);
            }
            {
                JPanel deadlinePanel = buildDeadlinePanel();
                deadlinePanel.setBackground(null);
                descriptionDeadlinePanel.add(deadlinePanel, BorderLayout.EAST);
            }
            contentPanel.add(descriptionDeadlinePanel, BorderLayout.SOUTH);
        }
        return contentPanel;
    }


    /**
     * Creates a priority selection panel with radio-style toggle buttons for {@link
     * Task.Priority#LOW}, {@link Task.Priority#MEDIUM}, and {@link Task.Priority#HIGH}.
     */
    private JPanel buildPriorityInputPanel() {
        JPanel priorityInputPanel = new JPanel();
        priorityInputPanel.setLayout(new BoxLayout(priorityInputPanel, BoxLayout.X_AXIS));
        {
            JLabel priorityLabel = new JLabel("Priority:");
            priorityLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            priorityLabel.setBackground(null);
            priorityInputPanel.add(priorityLabel);
        }
        priorityInputPanel.add(Box.createHorizontalStrut(10));
        {
            JToggleButton lowButton = new JToggleButton(Task.Priority.LOW.toString());
            lowButton.setActionCommand(Task.Priority.LOW.name());
            JToggleButton mediumButton = new JToggleButton(Task.Priority.MEDIUM.toString());
            mediumButton.setActionCommand(Task.Priority.MEDIUM.name());
            JToggleButton highButton = new JToggleButton(Task.Priority.HIGH.toString());
            highButton.setActionCommand(Task.Priority.HIGH.name());
            lowButton.setSelected(true);
            priorityButtonGroup = new ButtonGroup();
            for (var button : new JToggleButton[]{lowButton, mediumButton, highButton}) {
                button.setMaximumSize(new Dimension(90, 30));
                button.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
                priorityButtonGroup.add(button);
                priorityInputPanel.add(button);
            }
        }
        return priorityInputPanel;
    }


    /** Creates a panel containing an editable text area for the task details. */
    private JPanel buildDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        {
            var detailsLabel = new JLabel("Details:");
            detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            detailsLabel.setBackground(null);
            detailsPanel.add(detailsLabel);
        }
        detailsPanel.add(Box.createVerticalStrut(10));
        {
            detailsInputArea = new JTextArea(2, 15);
            detailsInputArea.setLineWrap(true);
            detailsInputArea.setWrapStyleWord(true);
            detailsInputArea.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            detailsInputArea.setMargin(new Insets(5, 5, 5, 5));
            detailsInputArea.setMinimumSize(detailsInputArea.getPreferredSize());
            detailsInputArea.setMaximumSize(detailsInputArea.getPreferredSize());
            detailsInputArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            Border frame = BorderFactory.createLineBorder(Color.getHSBColor(0.0f, 0.0f, 0.80f), 1);
            Border innerPadding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            detailsInputArea.setBorder(BorderFactory.createCompoundBorder(frame, innerPadding));
            InputMap inputMap = detailsInputArea.getInputMap();
            inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
            setMaximalInputLength(detailsInputArea, 40);
            detailsPanel.add(detailsInputArea);
        }
        return detailsPanel;
    }


    /**
     * Creates a panel containing the deadline selector
     * components: label, spinner and "no deadline" checkbox.
     */
    private JPanel buildDeadlinePanel() {
        JPanel deadlinePanel = new JPanel();
        deadlinePanel.setLayout(new BoxLayout(deadlinePanel, BoxLayout.Y_AXIS));
        {
            deadlineLabel = new JLabel("Deadline:");
            deadlineLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            deadlineLabel.setBackground(null);
            deadlineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            deadlinePanel.add(deadlineLabel);
        }
        deadlinePanel.add(Box.createVerticalStrut(5));
        {
            deadlineSpinner = new JSpinner(new SpinnerDateModel());
            deadlineSpinner.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            deadlineSpinner.setEditor(new JSpinner.DateEditor(deadlineSpinner, " dd.MM.yyyy"));
            deadlineSpinner.addChangeListener(onChange -> refreshDeadlineLabel());
            deadlineSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
            deadlinePanel.add(deadlineSpinner);
        }
        deadlinePanel.add(Box.createVerticalStrut(5));
        {
            noDeadlineCheckBox = new JCheckBox(" no deadline  ");
            noDeadlineCheckBox.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
            noDeadlineCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
            noDeadlineCheckBox.setIconTextGap(1);
            noDeadlineCheckBox.addActionListener(onClick -> {
                deadlineSpinner.setEnabled(!noDeadlineCheckBox.isSelected());
                refreshDeadlineLabel();
            });
            noDeadlineCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            deadlinePanel.add(noDeadlineCheckBox);
        }
        return deadlinePanel;
    }


    /** Updates the deadline display to indicate when a chosen deadline is past. */
    private void refreshDeadlineLabel() {
        if (!deadlineInputIsValid() && !noDeadlineCheckBox.isSelected()) {
            deadlineLabel.setForeground(Color.RED);
            deadlineLabel.setText("Deadline missed.");
        } else {
            deadlineLabel.setForeground(Color.BLACK);
            deadlineLabel.setText("Deadline:");
        }
    }


    /** Sets the initially selected priority radio button according to the task's current priority. */
    private void selectInitialPriorityFromData() {
        var priority = dataToWorkWith.getPriority();
        Enumeration<AbstractButton> buttons = priorityButtonGroup.getElements();
        while (priority != null && buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.getActionCommand().equals(priority.name())) {
                button.setSelected(true);
                break;
            }
        }
    }


    /** Sets the deadline spinner and checkbox according to the task's current deadline. */
    private void selectInitialDeadlineFromData() {
        var deadline = dataToWorkWith.getDeadline();
        if (deadline != null) {
            Date date = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant());
            deadlineSpinner.setValue(date);
            deadlineSpinner.setEnabled(true);
            noDeadlineCheckBox.setSelected(false);
        } else {
            noDeadlineCheckBox.setSelected(true);
            deadlineSpinner.setEnabled(false);
        }
    }


    /** Provides the selected priority value from the radio button group. */
    private Task.Priority getPriorityFromSelection() {
        ButtonModel selectedButton = priorityButtonGroup.getSelection();
        if (selectedButton != null) {
            return Task.Priority.valueOf(selectedButton.getActionCommand());
        }
        return Task.Priority.MEDIUM;
    }


    /**
     * Provides the selected deadline as a {@link LocalDate},
     * or {@code null} if no deadline is noted by the user.
     */
    private LocalDate getDeadlineFromSelection() {
        if (noDeadlineCheckBox.isSelected()) {
            return null;
        }
        Date selectedDate = (Date) deadlineSpinner.getValue();
        return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    /** {@inheritDoc} */
    @Override
    protected Runnable thisConfirmAction() {
        return () -> {
            if (titleInputIsValid() && deadlineInputIsValid()) {
                wasConfirmed = true;
                setVisible(false);
            } else {
                if (!titleInputIsValid()) {
                    titleInputField.setForeground(Color.RED);
                    titleInputField.setText("Title cannot be empty.");
                }
                if (!deadlineInputIsValid()) {
                    deadlineLabel.setForeground(Color.RED);
                    deadlineLabel.setText("Deadline missed.");
                }
            }
        };
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Save Changes";
    }


    /** Determines if the selected deadline is valid. */
    private boolean deadlineInputIsValid() {
        if (noDeadlineCheckBox.isSelected()) return true;
        LocalDate chosenDeadline = getDeadlineFromSelection();
        return chosenDeadline != null && !chosenDeadline.isBefore(LocalDate.now());
    }
}
