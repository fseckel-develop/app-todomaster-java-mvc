package view.controls;

import javax.swing.*;
import java.awt.*;


/**
 * Abstract base class for creating uniformly styled {@link JButton}s
 * with an optional action callback. <p>
 * This class simplifies the process of creating square-shaped, bold-font
 * buttons that invoke a provided {@link Runnable} action when clicked.
 * Subclasses can build on this class to implement custom behavior. </p>
 */
public abstract class AbstractButton extends JButton
{
    /** The action to invoke when the button is clicked. May be {@code null}. */
    protected Runnable action;


    /**
     * Constructs a new {@code AbstractButton} with the given label and action.
     * @param label  the text to display on the button
     * @param action the action to invoke when the button is clicked, or {@code null} for no action
     */
    public AbstractButton(String label, Runnable action) {
        super(label);
        this.action = action;
        buildUI();
    }


    /** Sets up the button's appearance and behavior. */
    protected void buildUI() {
        setPreferredSize(new Dimension(40, 40));
        setMaximumSize(new Dimension(40, 40));
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addActionListener(e -> onClick());
        setFocusable(false);
    }


    /** Handles the button's click event by invoking the assigned {@link Runnable} action if present. */
    protected void onClick() {
        if (action != null) {
            action.run();
        }
    }
}
