package view.controls;


/**
 * Specialized {@link AbstractButton} that displays a "plus" symbol.
 * Typically used to add new elements or items in the user interface.
 */
public class AddButton extends AbstractButton
{
    /**
     * Constructs a new {@code AddButton} with the given action.
     * @param action the {@link Runnable} to invoke when this button is clicked,
     * or {@code null} for no action
     */
    public AddButton(Runnable action) {
        super("➕", action);
    }
}
