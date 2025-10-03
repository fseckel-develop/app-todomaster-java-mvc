package view.dialogs;

import model.Checklist;
import auxiliaries.FontLoader;

import javax.swing.*;
import java.awt.*;


/**
 * Defines a dialog for creating a new {@link Checklist}. <p>
 * This dialog provides input fields for the checklist title
 * and lets the user select the display mode (cards or flowing text).
 * Once confirmed, a configured {@link Checklist} can be retrieved
 * via {@link #getEditedData()}. </p>
 */
public class CreateChecklistDialog extends AbstractTitleEditDialog<Checklist>
{
    /** The group of buttons for selecting the checklists display mode */
    private ButtonGroup displayModeButtonGroup;


    /**
     * Constructs a {@code CreateChecklistDialog}.
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public CreateChecklistDialog(JFrame owningWindow) {
        super("Creating Checklist", new Checklist(), owningWindow);
        setPreferredSize(new Dimension(390, 230));
        buildUI();
    }


    /**
     * {@inheritDoc} <p>
     * Sets the title and display mode of the checklist based on user input. </p>
     * @return the configured {@link Checklist} instance
     */
    @Override
    public Checklist getEditedData() {
        dataToWorkWith.setTitle(titleInputField.getText());
        dataToWorkWith.setDisplayMode(getDisplayModeFromSelection());
        return dataToWorkWith;
    }


    /**
     * {@inheritDoc} <p>
     * Adds additional input for display mode selection below the title input. </p>
     * @return a panel containing the dialog's content components
     */
    @Override
    protected JPanel buildContentPanel() {
        var contentPanel = super.buildContentPanel();
        var displayModeInputPanel = buildDisplayModeInputPanel();
        contentPanel.add(displayModeInputPanel, BorderLayout.CENTER);
        return contentPanel;
    }


    /**
     * Provides the panel that allows the user to select the display mode
     * for the checklist (stacked cards or flowing text).
     */
    private JPanel buildDisplayModeInputPanel() {
        var displayModeInputPanel = new JPanel(); {
            displayModeInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            displayModeInputPanel.setBackground(null);
            displayModeInputPanel.setLayout(new BoxLayout(displayModeInputPanel, BoxLayout.X_AXIS));
            var displayModeLabel = new JLabel("Displayed as:"); {
                displayModeLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
                displayModeLabel.setBackground(null);
                displayModeInputPanel.add(displayModeLabel);
                displayModeInputPanel.add(Box.createHorizontalStrut(10));
            }
            var asCardsButton = new JToggleButton("stacked cards"); {
                asCardsButton.setActionCommand(Checklist.DisplayMode.CARDS.name());
                asCardsButton.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 13));
                asCardsButton.setSelected(true);
            }
            var asTextButton = new JToggleButton("flowing text"); {
                asTextButton.setActionCommand(Checklist.DisplayMode.TEXT.name());
                asTextButton.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 13));
            }
            displayModeButtonGroup = new ButtonGroup();
            for (var button : new JToggleButton[]{asCardsButton, asTextButton}) {
                button.setMaximumSize(new Dimension(170, 30));
                displayModeButtonGroup.add(button);
                displayModeInputPanel.add(button);
            }
        }
        return displayModeInputPanel;
    }


    /** Determines the selected {@link Checklist.DisplayMode} from the button group. */
    private Checklist.DisplayMode getDisplayModeFromSelection() {
        var selectedButton = displayModeButtonGroup.getSelection();
        if (selectedButton != null) {
            return Checklist.DisplayMode.valueOf(selectedButton.getActionCommand());
        }
        return Checklist.DisplayMode.CARDS;
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Create Checklist";
    }
}
