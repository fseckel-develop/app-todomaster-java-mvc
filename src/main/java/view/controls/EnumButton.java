package view.controls;

import auxiliaries.FontLoader;

import java.awt.*;
import java.util.function.Consumer;


/**
 * Specialized {@link MenuButton} that displays all constants of a given {@link Enum}
 * in its {@link OptionsMenu}. <p> When an option is selected, the button's label is
 * updated to show the selected value, and a callback {@link Consumer} is invoked with
 * the chosen value. </p>
 * @param <OptionT> the type of the enumeration
 */
public class EnumButton<OptionT extends Enum<OptionT>> extends MenuButton
{
    /** The enumeration class whose constants will populate the options menu. */
    private final Class<OptionT> enumType;

    /** The action to invoke when a value is selected. */
    private final Consumer<OptionT> action;


    /**
     * Constructs a new {@code EnumButton} with the given label, enumeration type, and action.
     * <p> The options menu will be populated with all constants of the provided enum,
     * and clicking the button will show a popup with those options. </p>
     * @param label the label displayed on this button initially
     * @param enumType the {@link Enum} class to list as options
     * @param action a callback invoked with the selected enum value when an option is chosen
     */
    public EnumButton(String label, Class<OptionT> enumType, Consumer<OptionT> action) {
        super(label, new OptionsMenu());
        this.enumType = enumType;
        this.action = action;
        registerEnumValues();
        buildUI();
    }


    /** {@inheritDoc} */
    @Override
    protected void buildUI() {
        super.buildUI();
        setPreferredSize(new Dimension(120, 30));
        setMaximumSize(new Dimension(120, 30));
        setFont(FontLoader.getFont("FiraSans", Font.BOLD, 12));
    }


    /** Displays the options menu as a popup positioned directly above this button. */
    @Override
    protected void onClick() {
        int popupX = 0;
        int popupY = -menu.getPreferredSize().height;
        menu.show(this, popupX, popupY);
    }


    /**
     * Populates the {@link OptionsMenu} with each constant of the {@link Enum} type. <p>
     * When an option is selected, the button's label is updated to match the constant's
     * name, and the {@code action} callback is invoked with the selected value. </p>
     */
    private void registerEnumValues() {
        for (OptionT value : enumType.getEnumConstants()) {
            menu.addOption(value.toString(), () -> {
                setText(value.toString());
                if (action != null) action.accept(value);
            });
        }
    }
}
