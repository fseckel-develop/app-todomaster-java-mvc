package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract data model that provides the tools to manage a collection of
 * {@link AbstractData} items. It extends {@link AbstractData} as well and
 * notifies any registered observer whenever the collection has been modified.
 * @param <DataT> the type of {@link AbstractData} items in the collection
 */
public abstract class AbstractCollection<DataT extends AbstractData> extends AbstractData
{
    /** The list of items managed by this collection. */
    protected final List<DataT> items = new ArrayList<>();


    /** {@inheritDoc} */
    @Override
    public JSONObject toJSON() {
        JSONObject collectionData = new JSONObject();
        collectionData.put("title", title);
        JSONArray itemArray = new JSONArray();
        for (DataT item : items) {
            itemArray.put(item.toJSON());
        }
        collectionData.put("items", itemArray);
        return collectionData;
    }


    /**
     * Adds an item to the collection and notifies observers.
     * @param item the item to add
     */
    public void add(DataT item) {
        items.add(item);
        notifyObservers();
    }


    /**
     * Removes an item from the collection and notifies observers.
     * @param item the item to remove
     */
    public void remove(DataT item) {
        items.remove(item);
        notifyObservers();
    }


    /** Removes all items from the collection and notifies observers. */
    public void clear() {
        items.clear();
        notifyObservers();
    }


    /** Returns the number of items in the collection. */
    public int getItemCount() {
        return items.size();
    }


    /** Returns the list of items managed by this collection. */
    public List<DataT> getItems() {
        return items;
    }
}
