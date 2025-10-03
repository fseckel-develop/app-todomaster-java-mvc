package view.dialogs;

import model.AbstractData;
import auxiliaries.FontLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * Abstract base class for creating modal dialogs that edit or manipulate a specific type of data.
 * This class provides a common structure for dialogs that:
 * <ul>
 *     <li>Wrap around a specific data object of type {@code DataT}</li>
 *     <li>Provide "Cancel" and "Confirm" buttons at the bottom of the dialog</li>
 *     <li>Have a content panel that can be customized by subclasses</li>
 *     <li>Store whether the user confirmed the dialog's action</li>
 * </ul>
 * @param <DataT> the type of data object being edited or manipulated, extending {@link AbstractData}
 */
public abstract class AbstractDialog<DataT extends AbstractData> extends JDialog
{
    /** The data object that this dialog will manipulate. */
    protected final DataT dataToWorkWith;

    /** Flag indicating whether the user confirmed the dialog's action. */
    protected boolean wasConfirmed;

    /** The "Cancel" button that closes the dialog without confirming. */
    protected JButton cancelButton;

    /** The "Confirm" button that commits the action associated with this dialog. */
    protected JButton confirmButton;


    /**
     * Constructs a new {@code AbstractDialog}.
     * @param headline the title displayed on the dialog's title bar
     * @param dataToWorkWith the data object to manipulate
     * @param owningWindow the parent window to center this dialog on
     */
    public AbstractDialog(String headline, DataT dataToWorkWith, JFrame owningWindow) {
        super(owningWindow, headline, true);
        this.dataToWorkWith = dataToWorkWith;
        this.wasConfirmed = false;
        setPreferredSize(new Dimension(370, 180));
    }


    /**
     * Builds the dialog's user interface. This method sets up the dialog's
     * content panel and button panel, sizes the dialog to its contents and
     * centers it relative to its owner. The "Cancel" button is set as the
     * default action.
     */
    protected void buildUI() {
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildContentPanel(), BorderLayout.NORTH);
        getContentPane().add(buildButtonPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        getRootPane().setDefaultButton(cancelButton);
    }


    /**
     * Creates the content panel for the dialog. Subclasses
     * can add their own components to this panel as needed.
     * A standard border with top padding and separator is applied.
     * @return the content panel
     */
    protected JPanel buildContentPanel() {
        var contentPanel = new JPanel(new BorderLayout()); {
            Border topBanner = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(10, 0, 0, 0, Color.getHSBColor(0.0f, 0.0f, 0.96f)),
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY)
            );
            contentPanel.setBorder(BorderFactory.createCompoundBorder(
                topBanner, BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            contentPanel.setBackground(null);
        }
        return contentPanel;
    }


    /**
     * Creates the panel containing the "Cancel" and "Confirm" buttons. The "Cancel"
     * button simply closes the dialog and sets {@link #wasConfirmed} to {@code false}.
     * The "Confirm" button closes the dialog and sets {@link #wasConfirmed} to {@code
     * true}, allowing subclasses to execute their own logic.
     * @return the panel containing the action buttons
     */
    private JPanel buildButtonPanel() {
        var buttonPanel = new JPanel(new BorderLayout()); {
            buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            buttonPanel.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.96f));
            cancelButton = new JButton("Cancel");
            cancelButton.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 13));
            cancelButton.addActionListener(onClick -> {
                wasConfirmed = false;
                setVisible(false);
            });
            buttonPanel.add(cancelButton, BorderLayout.WEST);
            confirmButton = new JButton(thisConfirmButtonText());
            confirmButton.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 13));
            confirmButton.addActionListener(onClick -> thisConfirmAction().run());
            buttonPanel.add(confirmButton, BorderLayout.EAST);
        }
        return buttonPanel;
    }


    /** Determines whether the user confirmed the dialog's action. */
    public boolean wasConfirmed() {
        return wasConfirmed;
    }


    /** Returns the {@link Runnable} action to be used when the Confirm button is pressed. */
    protected Runnable thisConfirmAction() {
        return () -> {
            wasConfirmed = true;
            setVisible(false);
        };
    }


    /** Provides the label text displayed on the "Confirm" button. */
    protected abstract String thisConfirmButtonText();
}
