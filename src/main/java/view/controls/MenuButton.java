package view.controls;


/**
 * A button that, when clicked, displays an {@link OptionsMenu} as a popup
 * anchored to the bottom-right of the button.
 * <p>
 * This class extends {@link AbstractButton} and overrides its click behavior
 * to show the associated options menu.
 * </p>
 */
public class MenuButton extends AbstractButton {

    /**
     * The popup menu that will be displayed when this button is clicked.
     */
    protected OptionsMenu menu;

    /**
     * Constructs a new {@code MenuButton} with the given label and an optional menu.
     * <p> If the provided {@code menu} is {@code null}, a new empty {@link OptionsMenu}
     * will be created automatically. </p>
     * @param label the label displayed on this button
     * @param menu  the options menu to show when this button is clicked, or {@code null}
     * to create a new empty menu
     */
    public MenuButton(String label, OptionsMenu menu) {
        super(label, null);
        this.menu = (menu == null) ? new OptionsMenu() : menu;
    }


    /** Returns the {@link OptionsMenu} attached to this {@code MenuButton} */
    public OptionsMenu getMenu() {
        return menu;
    }


    /**
     * Handles the click event for this button. <p>
     * Calculates the appropriate position for the popup so that it is
     * aligned to the bottom-right of the button, then displays the menu. </p>
     */
    @Override
    protected void onClick() {
        int popupX = getWidth() - menu.getPreferredSize().width;
        int popupY = getHeight();
        menu.show(this, popupX, popupY);
    }
}
