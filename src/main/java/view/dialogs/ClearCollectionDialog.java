package view.dialogs;

import model.AbstractCollection;
import model.AbstractData;

import javax.swing.*;


/**
 * Defines a confirmation dialog for clearing all contents of a collection. <p>
 * This dialog extends {@link DeleteDataDialog} and is specialized for {@link
 * AbstractCollection} types. It provides a message and confirmation button
 * allowing the user to clear the collection of all its items. </p>
 * @param <CollectionT> the type of collection being cleared,
 * which must extend {@link AbstractCollection}
 * @param <DataT> the type of data elements in the collection,
 * which must extend {@link AbstractData}
 */
public class ClearCollectionDialog<CollectionT extends AbstractCollection<DataT>, DataT extends AbstractData> extends DeleteDataDialog<CollectionT>
{
    /**
     * Constructs a new {@code ClearCollectionDialog}.
     * @param collectionToClear the collection whose contents will be cleared
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public ClearCollectionDialog(CollectionT collectionToClear, JFrame owningWindow) {
        super(collectionToClear, owningWindow);
        setTitle("Clearing " + collectionToClear.getClass().getSimpleName());
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Clear " + dataToWorkWith.getClass().getSimpleName();
    }
}
