package controller.contracts;


/**
 * Defines a listener interface for handling user actions on a collection page,
 * such as renaming the collection's title, adding new items, or clearing all items.
 * <p>
 * Implementations of this interface typically respond to user input
 * originating from view components like buttons, menus, or dialog confirmations.
 * </p>
 */
public interface ICollectionPageListener
{
    /**
     * Called when the user requests to rename the title of the current collection.
     * Implementing classes should show a dialog or other UI component to
     * accept the new title and apply the change.
     */
    void onRenameTitleRequested();


    /**
     * Called when the user requests to add a new item to the current collection.
     * Implementing classes should present an interface to create and add the new item.
     */
    void onAddNewItemRequested();


    /**
     * Called when the user requests to clear all items in the current collection.
     * Implementing classes typically prompt the user for confirmation before clearing.
     */
    void onClearRequested();
}