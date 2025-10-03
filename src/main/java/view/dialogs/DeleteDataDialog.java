package view.dialogs;

import model.AbstractData;
import auxiliaries.FontLoader;

import javax.swing.*;
import java.awt.*;


/**
 * Defines a confirmation dialog for deleting data entities. <p>
 * Presents a message asking the user to confirm the deletion of a specific {@link AbstractData} item.
 * This dialog is a subclass of {@link AbstractDialog} and adds a custom message label
 * warning the user that the data will be lost permanently if deleted. </p>
 * @param <DataT> the concrete type of data being deleted, extending {@link AbstractData}
 */
public class DeleteDataDialog<DataT extends AbstractData> extends AbstractDialog<DataT>
{
    /**
     * Constructs a new {@code DeleteDataDialog}.
     * @param dataToDelete the data object to delete
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public DeleteDataDialog(DataT dataToDelete, JFrame owningWindow) {
        super("Deleting " + dataToDelete.getClass().getSimpleName(), dataToDelete, owningWindow);
        buildUI();
    }


    /**
     * Builds the content panel for the dialog. <p>
     * This implementation adds a centered warning message label
     * to the top-level content panel created by the superclass. </p>
     * @return a populated {@link JPanel} containing the message label
     */
    @Override
    protected JPanel buildContentPanel() {
        var contentPanel = super.buildContentPanel(); {
            var messageLabel = buildMessageLabel();
            contentPanel.add(messageLabel, BorderLayout.CENTER);
        }
        return contentPanel;
    }


    /**
     * Creates the message label that warns the user that data will be lost forever.
     * @return a configured {@link JLabel} with a centered warning message
     */
    private JLabel buildMessageLabel() {
        var messageLabel = new JLabel(); {
            messageLabel.setText("Are you sure?  Data will be lost forever.");
            messageLabel.setFont(FontLoader.getFont("FiraSans", Font.PLAIN, 15));
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
            messageLabel.setBackground(null);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return messageLabel;
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Delete " + dataToWorkWith.getClass().getSimpleName();
    }
}
