package controller.contracts;

import model.AbstractData;


/**
 * Defines a listener interface for responding to user actions on
 * a data panel that displays or manages {@link AbstractData} items.
 * @param <DataT> the type of data displayed in the panel, which must extend {@link AbstractData}
 */
public interface IDataPanelListener<DataT extends AbstractData>
{
    /**
     * Called when the user requests to edit the specified data item.
     * @param itemToEdit the data item that the user wants to edit
     */
    void onEditItemRequested(DataT itemToEdit);


    /**
     * Called when the user requests to delete the specified data item.
     * @param itemToDelete the data item that the user wants to delete
     */
    void onDeleteItemRequested(DataT itemToDelete);


    /**
     * Called when the user selects the specified data item.
     * @param selectedItem the data item that was selected by the user
     */
    void onItemSelected(DataT selectedItem);
}
