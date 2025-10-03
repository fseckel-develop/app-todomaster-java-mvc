package view.dialogs;

import model.AbstractData;

import javax.swing.*;


/**
 * Defines a dialog for renaming an {@link AbstractData} object. <p>
 * This dialog allows the user to input a new title for the data object
 * and confirm or cancel the action. It extends {@link AbstractTitleEditDialog}
 * to leverage a standard title-editing UI. </p>
 * @param <DataT> the specific type of data being renamed,
 * which must extend {@link AbstractData}
 */
public class RenameDataDialog<DataT extends AbstractData> extends AbstractTitleEditDialog<DataT> {

    /**
     * Constructs a new {@code RenameDataDialog}.
     * @param dataToRename the data object to be renamed
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public RenameDataDialog(DataT dataToRename, JFrame owningWindow) {
        super("Renaming " + dataToRename.getClass().getSimpleName(), dataToRename, owningWindow);
        buildUI();
    }


    /** {@inheritDoc} */
    @Override
    protected String thisTitleLabelText() {
        return "New Title:";
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Rename " + dataToWorkWith.getClass().getSimpleName();
    }
}
