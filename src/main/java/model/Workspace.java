package model;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Defines a workspace that maintains multiple {@link Checklist} items while extending
 * {@link AbstractCollection} to provide list tools, observability and serialization.
 */
public class Workspace extends AbstractCollection<Checklist>
{
    /**
     * Creates a workspace from the JSON object.
     * @param workspaceData {@link JSONObject} representing the workspace data;
     * must contain a {@code title} and an {@code items} array of serialized checklists.
     */
    public Workspace(JSONObject workspaceData) {
        this.title = workspaceData.getString("title");
        JSONArray itemArray = workspaceData.getJSONArray("items");
        if (itemArray != null) {
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject checklistData = itemArray.getJSONObject(i);
                Checklist checklist = new Checklist(checklistData);
                this.items.add(checklist);
            }
        }
    }


    /** Creates a new workspace with the default title "My Workspace". */
    public Workspace() {
        title = "My Workspace";
    }
}
