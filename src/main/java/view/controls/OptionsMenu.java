package view.controls;

import javax.swing.*;
import java.awt.*;


/**
 * A simple popup menu that contains a list of selectable options,
 * displayed as {@link JMenuItem}s separated by vertical spacing. <p>
 * The menu can be populated with labeled options that invoke specified
 * actions when selected. </p>
 */
public class OptionsMenu extends JPopupMenu {

    /**
     * Constructs a new {@code OptionsMenu} with some top padding and a specific curser.
     * Sets a top border padding to improve visual spacing.
     */
    public OptionsMenu() {
        setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    /**
     * Adds a new labeled option to this menu. <p> Creates a {@link JMenuItem}
     * with the given label and binds it to the specified action. A vertical spacing
     * strut is inserted after the item for visual separation between options. </p>
     * @param label the text to display on the option
     * @param optionsAction the {@link Runnable} action to execute when this option is selected
     */
    public void addOption(String label, Runnable optionsAction) {
        JMenuItem option = new JMenuItem(label);
        option.addActionListener(onSelect -> optionsAction.run());
        add(option);
        add(Box.createVerticalStrut(5));
    }
}
