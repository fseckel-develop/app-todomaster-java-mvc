package view.panels;

import controller.contracts.IDataPanelListener;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;


/**
 * Defines a data panel for displaying a single {@link Task} as text.
 * <ul>
 *     <li>Displays the task title as a label.</li>
 *     <li>Strikes through the title if the task is completed.</li>
 *     <li>Changes color if the task's deadline is missed.</li>
 *     <li>Displays detailed information (e.g. details, priority, deadline) as a rich tooltip.</li>
 *     <li>Allows to left-click to select the task and to right-click to show options.</li>
 * </ul>
 */
public class TaskTextPanel extends AbstractDataPanel<Task>
{
    /**
     * Creates a new task text panel with the specified task and controller.
     * @param dataToObserve the task to display
     * @param controller listener handling selection and other actions
     */
    public TaskTextPanel(Task dataToObserve, IDataPanelListener<Task> controller) {
        super(dataToObserve, controller);
        buildUI();
        refreshView();
    }


    /**
     * Constructs the user interface elements:
     * <ul>
     *     <li>Creates and styles the title label.</li>
     *     <li>Sets up tooltip and mouse listeners.</li>
     * </ul>
     */
    @Override
    protected void buildUI() {
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        titleLabel.setToolTipText(thisToolTipText());
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent onClick) {
                if (SwingUtilities.isLeftMouseButton(onClick)) {
                    controller.onItemSelected(observedData);
                } else if (SwingUtilities.isRightMouseButton(onClick)) {
                    options.show(onClick.getComponent(), onClick.getX(), onClick.getY());
                }
            }
        });
        add(titleLabel);
    }


    /**
     * Updates the title label's style and tooltip based on the current task state.
     * <ul>
     *     <li>Strikes through the title if the task is done.</li>
     *     <li>Colors the title red if the deadline is missed, or gray if completed.</li>
     *     <li>Refreshes the tooltip text.</li>
     * </ul>
     */
    @Override
    protected void refreshView() {
        if (observedData.isDone()) {
            titleLabel.setForeground(Color.getHSBColor(0.0f, 0.0f, 0.70f));
            titleLabel.setText("<html><strike>" + observedData.getTitle() + "</strike>,</html>");
        } else {
            titleLabel.setText(observedData.getTitle() + ",");
            titleLabel.setForeground(observedData.deadlineMissed() ? Color.RED : Color.BLACK);
        }
        titleLabel.setToolTipText(thisToolTipText());
    }


    /** {@inheritDoc} */
    @Override
    protected String thisEditOptionText() {
        return "Edit Task";
    }


    /**
     * Constructs a rich HTML tooltip text containing task details:
     * <ul>
     *     <li>Details text, if present.</li>
     *     <li>Priority level, if set.</li>
     *     <li>Deadline information, with color indicating a missed deadline.</li>
     * </ul>
     * @return the tooltip text as HTML
     */
    private String thisToolTipText() {
        StringBuilder hoverDetailText = new StringBuilder("<html><body style='line-height:1.4;'><table>");
        if (!observedData.getDetails().isEmpty()) {
            hoverDetailText.append("<tr><td><b>Details:</b></td>");
            hoverDetailText.append("<td>").append(observedData.getDetails()).append("</td></tr>");
        }
        if (observedData.getPriority() != null) {
            hoverDetailText.append("<tr><td><b>Priority:</b></td>");
            hoverDetailText.append("<td>").append(observedData.getPriority().toString()).append("</td></tr>");
        }
        if (observedData.getDeadline() != null) {
            String deadline = observedData.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            hoverDetailText.append("<tr><td><b>Deadline:</b></td><td>");
            if (observedData.deadlineMissed()) {
                hoverDetailText.append("<font color='red'>missed</font></td></tr>");
            } else {
                hoverDetailText.append(deadline).append("</td></tr>");
            }
        }
        hoverDetailText.append("</table></body></html>");
        return hoverDetailText.toString();
    }


    /** Returns a {@link JToolTip} with a custom border. */
    @Override
    public JToolTip createToolTip() {
        var tooltip = super.createToolTip(); {
            tooltip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.getHSBColor(0.0f, 0.00f, 0.60f), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        return tooltip;
    }
}
