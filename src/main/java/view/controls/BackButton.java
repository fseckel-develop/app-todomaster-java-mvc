package view.controls;


/**
 * Specialized {@link AbstractButton} that displays a "back" arrow symbol.
 * Typically used for navigation purposes in the graphical user interface.
 */
public class BackButton extends AbstractButton
{
    /**
     * Constructs a new {@code BackButton} with the given action.
     * @param action the {@link Runnable} to invoke when this button is clicked,
     * or {@code null} for no action
     */
    public BackButton(Runnable action) {
        super("\uD83D\uDD19", action);
    }
}
