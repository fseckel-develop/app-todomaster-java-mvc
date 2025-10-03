package view.dialogs;

import model.AbstractData;
import auxiliaries.FontLoader;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Abstract base class for dialogs that allow the user to edit an {@link AbstractData} object.
 * Provides a standard UI for editing the title of the data object. Includes:
 * <ul>
 *     <li>A text input for the title with a customizable label</li>
 *     <li>Input validation to ensure the title is not empty and under a maximal length</li>
 *     <li>Visual feedback (e.g. red text) when validation fails</li>
 * </ul>
 * @param <DataT> the type of data object this dialog will edit
 */
public abstract class AbstractTitleEditDialog<DataT extends AbstractData> extends AbstractDialog<DataT>
{
    /** The editable text field for the data's title. */
    protected JTextField titleInputField;


    /**
     * Constructs a new edit dialog for the specified data.
     * @param headline     the title displayed on the dialog's title bar
     * @param dataToEdit   the data object to be edited
     * @param owningWindow the parent window for this dialog
     */
    public AbstractTitleEditDialog(String headline, DataT dataToEdit, JFrame owningWindow) {
        super(headline, dataToEdit, owningWindow);
    }


    /**
     * Builds the user interface and pre-fills the title input field with the current data's title.
     * <p> Also sets the "Confirm" button as the default action for the dialog. </p>
     */
    @Override
    protected void buildUI() {
        super.buildUI();
        fillInInitialData();
        getRootPane().setDefaultButton(confirmButton);
    }


    /** Sets the title input field's text to the current title of the data object. */
    protected void fillInInitialData() {
        titleInputField.setText(dataToWorkWith.getTitle());
    }


    /**
     * Applies any edits made in the dialog back to the data object and returns it.
     * @return the updated data object
     */
    public DataT getEditedData() {
        dataToWorkWith.setTitle(titleInputField.getText());
        return dataToWorkWith;
    }


    /**
     * Creates the main content panel, which contains a title input panel.
     * @return a panel allowing title input and any additional UI components
     */
    @Override
    protected JPanel buildContentPanel() {
        var contentPanel = super.buildContentPanel();
        var titleInputPanel = buildTitleInputPanel();
        contentPanel.add(titleInputPanel, BorderLayout.NORTH);
        return contentPanel;
    }


    /**
     * Constructs the panel containing the label and text input for the title.
     * @return a panel for editing the title
     */
    private JPanel buildTitleInputPanel() {
        var titleInputPanel = new JPanel();
        titleInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleInputPanel.setBackground(null);
        titleInputPanel.setLayout(new BoxLayout(titleInputPanel, BoxLayout.X_AXIS));
        titleInputPanel.add(buildTitleLabel());
        titleInputPanel.add(Box.createHorizontalStrut(10));
        titleInputField = buildTitleInputField();
        titleInputPanel.add(titleInputField);
        return titleInputPanel;
    }


    /**
     * Constructs the label displayed next to the title input.
     * @return a styled JLabel for the title input
     */
    private JLabel buildTitleLabel() {
        var titleLabel = new JLabel();
        titleLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
        titleLabel.setBackground(null);
        titleLabel.setText(thisTitleLabelText());
        return titleLabel;
    }


    /**
     * Constructs the text input for the title, with validation behavior.
     * Clears red error text on focus and limits the title length to a set
     * number of characters
     * @return a styled JTextField for editing the title
     */
    private JTextField buildTitleInputField() {
        var titleInputField = new JTextField();
        titleInputField.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 14));
        titleInputField.setBackground(null);
        titleInputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                if (titleInputField.getForeground().equals(Color.RED)) {
                    titleInputField.setText("");
                    titleInputField.setForeground(Color.BLACK);
                }
            }
        });
        setMaximalInputLength(titleInputField, 25);
        return titleInputField;
    }


    /** Provides the label text displayed before the title input. */
    protected String thisTitleLabelText() {
        return "Title:";
    }


    /**
     * Returns the {@link Runnable} action to be used when the Confirm button is pressed.
     * <p> Sets the dialog's {@link #wasConfirmed} to true and closes the dialog if all
     * the input is valid. Otherwise, shows the validity error using visual cues. </p>
     */
    @Override
    protected Runnable thisConfirmAction() {
        return () -> {
            if (titleInputIsValid()) {
                wasConfirmed = true;
                setVisible(false);
            } else {
                titleInputField.setForeground(Color.RED);
                titleInputField.setText("Title cannot be empty.");
            }
        };
    }


    /**
     * Checks whether the current title input is valid.
     * <p> A valid title is non-blank and not currently showing the error color. </p>
     * @return true if the title input is valid; false otherwise
     */
    protected boolean titleInputIsValid() {
        return titleInputField.getForeground() != Color.RED && !titleInputField.getText().trim().isEmpty();
    }


    /**
     * Sets a key listener that restricts the maximal number of characters allowed in a text component.
     * @param component the text component to restrict
     * @param maxLength the maximal number of characters
     */
    protected void setMaximalInputLength(JTextComponent component, int maxLength) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String titleInput = component.getText();
                if (titleInput.length() > maxLength) {
                    component.setText(titleInput.substring(0, maxLength));
                }
            }
        });
    }
}
