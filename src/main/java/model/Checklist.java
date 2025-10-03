package model;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Defines a checklist that can contain multiple {@link Task} items while extending
 * {@link AbstractCollection} to provide list tools, observability and serialization.
 */
public class Checklist extends AbstractCollection<Task>
{
    /** The preferred display mode for rendering the checklist. */
    private DisplayMode displayMode;


    /**
     * Creates a checklist from a JSON object.
     * @param checklistData {@link JSONObject} representing the checklist;
     * must contain a {@code title} and optionally an {@code items} array
     * of serialized {@link Task} objects.
     */
    public Checklist(JSONObject checklistData) {
        this.title = checklistData.getString("title");
        this.displayMode = Checklist.DisplayMode.valueOf(checklistData.getString("displayMode"));
        JSONArray itemArray = checklistData.optJSONArray("items");
        if (itemArray != null) {
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject taskData = itemArray.getJSONObject(i);
                Task task = new Task(taskData);
                this.items.add(task);
            }
        }
    }


    /** Creates an empty checklist. */
    public Checklist() {
        displayMode = DisplayMode.TEXT;
    }


    /**
     * Serializes this checklist into a JSON object.
     * @return {@link JSONObject} containing the collection title,
     * the preferred display mode and the JSON data of its items
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("displayMode", displayMode.name());
        JSONArray itemArray = new JSONArray();
        for (Task item : this.items) {
            itemArray.put(item.toJSON());
        }
        json.put("items", itemArray);
        return json;
    }


    /**
     * Sets the display mode for the checklist.
     * @param displayMode The new {@link DisplayMode} to use for this checklist.
     */
    public void setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
        notifyObservers();
    }


    /** Toggles the checklist display between {@link DisplayMode#CARDS} and {@link DisplayMode#TEXT}. */
    public void toggleDisplayMode() {
        displayMode = (displayMode == DisplayMode.CARDS) ? DisplayMode.TEXT : DisplayMode.CARDS;
        notifyObservers();
    }


    /** Returns the current display mode of the checklist. */
    public DisplayMode getDisplayMode() {
        return displayMode;
    }


    /** Counts the number of tasks in the checklist that are still unfinished. */
    public int getNumberOfOpenTasks() {
        int count = 0;
        for (Task task : items) {
            if (!task.isDone()) count++;
        }
        return count;
    }


    public enum DisplayMode {
        CARDS, TEXT;

        @Override
        public String toString() {
            return switch (this) {
                case CARDS -> "card stack";
                case TEXT -> "flowing text";
            };
        }
    }
}
