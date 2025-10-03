package auxiliaries;

import model.Workspace;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Utility class for persisting and retrieving {@link
 * Workspace} data to and from a JSON file on disk.
 */
public class JsonFileStorage
{
    private static final Path SAVE_PATH = Path.of(
            System.getProperty("user.home"), "toDoMaster-data.json"
    );


    /**
     * Loads the workspace data either from an external file if present,
     * or from the bundled resource otherwise.
     * If any error occurs, returns a new empty Workspace.
     */
    public static Workspace loadWorkspaceData() {
        try {
            if (!Files.exists(SAVE_PATH)) {
                try (InputStream is = JsonFileStorage.class.getResourceAsStream("/data/exemplary.json")) {
                    if (is == null) {
                        throw new IOException("Bundled default resource not found: /data/exemplary.json");
                    }
                    Files.copy(is, SAVE_PATH);
                }
            }
            try (InputStream is = Files.newInputStream(SAVE_PATH)) {
                return parseJsonToWorkspace(is);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error loading workspace data: " + e.getMessage());
            return new Workspace();
        }
    }


    /**
     * Reads the JSON data from the given input stream and constructs a new {@link Workspace} object.
     * @param json the input stream to read the JSON data from
     * @return a new {@link Workspace} populated with the data
     * @throws JSONException if the data cannot be parsed into valid JSON
     * @throws IOException   if an I/O error occurs while reading the stream
     */
    private static Workspace parseJsonToWorkspace(InputStream json) throws JSONException, IOException {
        try (Reader reader = new InputStreamReader(json, StandardCharsets.UTF_8)) {
            char[] buffer = new char[8192];
            StringBuilder jsonStringBuilder = new StringBuilder();
            int read;
            while ((read = reader.read(buffer)) != -1) {
                jsonStringBuilder.append(buffer, 0, read);
            }
            JSONObject workspaceData = new JSONObject(jsonStringBuilder.toString());
            return new Workspace(workspaceData);
        }
    }


    /**
     * Serializes the given {@link Workspace} into a JSON representation
     * and writes it to the specified file path.
     * @param workspace the workspace data to save
     * @throws JSONException if an error occurs during JSON serialization
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void saveWorkspaceData(Workspace workspace) throws JSONException, IOException {
        JSONObject json = workspace.toJSON();
        Files.createDirectories(SAVE_PATH.getParent());
        System.out.println("Saving workspace to: " + SAVE_PATH.toAbsolutePath());
        Files.writeString(SAVE_PATH, json.toString(2), StandardCharsets.UTF_8);
    }
}
