package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTests {

    @Test
    void defaultConstructor_shouldUseDefaultTitle() {
        Workspace workspace = new Workspace();

        assertEquals("My Workspace", workspace.getTitle());
        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void add_shouldAppendChecklist() {
        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");

        workspace.add(checklist);

        assertEquals(1, workspace.getItemCount());
        assertSame(checklist, workspace.getItems().getFirst());
    }

    @Test
    void remove_shouldDeleteChecklist() {
        Workspace workspace = new Workspace();
        Checklist checklist = new Checklist();
        checklist.setTitle("Inbox");
        workspace.add(checklist);

        workspace.remove(checklist);

        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void clear_shouldRemoveAllChecklists() {
        Workspace workspace = new Workspace();

        Checklist a = new Checklist();
        a.setTitle("A");

        Checklist b = new Checklist();
        b.setTitle("B");

        workspace.add(a);
        workspace.add(b);

        workspace.clear();

        assertTrue(workspace.getItems().isEmpty());
        assertEquals(0, workspace.getItemCount());
    }

    @Test
    void toJson_shouldSerializeWorkspaceAndItems() {
        Workspace workspace = new Workspace();
        workspace.setTitle("Office");

        Checklist checklist = new Checklist();
        checklist.setTitle("Monday");
        checklist.setDisplayMode(Checklist.DisplayMode.TEXT);

        workspace.add(checklist);

        JSONObject json = workspace.toJSON();

        assertEquals("Office", json.getString("title"));
        assertEquals(1, json.getJSONArray("items").length());
        assertEquals("Monday", json.getJSONArray("items").getJSONObject(0).getString("title"));
    }

    @Test
    void jsonConstructor_shouldRestoreWorkspaceAndChecklists() {
        JSONObject checklistJson = new JSONObject()
                .put("title", "Inbox")
                .put("displayMode", "CARDS")
                .put("items", new JSONArray());

        JSONObject workspaceJson = new JSONObject()
                .put("title", "My Team")
                .put("items", new JSONArray().put(checklistJson));

        Workspace workspace = new Workspace(workspaceJson);

        assertEquals("My Team", workspace.getTitle());
        assertEquals(1, workspace.getItemCount());
        assertEquals("Inbox", workspace.getItems().getFirst().getTitle());
        assertEquals(Checklist.DisplayMode.CARDS, workspace.getItems().getFirst().getDisplayMode());
    }
}
