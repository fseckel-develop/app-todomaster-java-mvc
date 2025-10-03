import auxiliaries.JsonFileStorage;
import controller.AppController;
import model.Workspace;
import auxiliaries.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


/**
 * The main application class that initializes the entire program. <p>
 * This class follows the Singleton pattern to ensure only one global instance exists.
 * It is responsible for:
 * <ul>
 *   <li>Loading the {@link Workspace} data at startup.</li>
 *   <li>Creating and managing the main {@link AppController}.</li>
 *   <li>Applying global UI configurations for look and feel.</li>
 *   <li>Starting the application by showing the workspace page.</li>
 * </ul>
 */
public class App
{
    /** The singleton instance of the application. */
    private static App instance;

    /** The top-level application controller that manages the main UI flow. */
    private final AppController controller;


    /**
     * Private constructor to enforce the Singleton pattern. <p>
     * Loads the workspace data from a file and constructs the controller.
     */
    private App() {
        var workspaceToControl = JsonFileStorage.loadWorkspaceData();
        controller = new AppController(workspaceToControl);
    }


    /**
     * Loads global UI configurations such as fonts and tooltip styles. <p>
     * Sets up the {@code FiraSans} font family, tooltip colors, borders, and font size.
     */
    private void loadUIConfiguration() {
        FontLoader.loadFamily("FiraSans");
        UIManager.put("ToolTip.background", Color.WHITE);
        UIManager.put("ToolTip.foreground", Color.BLACK);
        UIManager.put("ToolTip.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.getHSBColor(0.0f, 0.0f, 0.50f), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        UIManager.put("ToolTip.font", FontLoader.getFont("FiraSans", Font.PLAIN, 12));
    }


    /** Adds an event listener to the main window to save the data as JSON once closed. */
    private void ensureSaveOnClose() {
        controller.getWindow().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent onClose) {
                try {
                    JsonFileStorage.saveWorkspaceData(controller.getWorkspace());
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }


    /**
     * Retrieves the singleton instance of the App.
     * Creates the instance if it does not already exist.
     * @return the single {@code App} instance
     */
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }


    /**
     * Starts the application by applying UI configurations
     * and showing the main workspace page.
     */
    public void run() {
        loadUIConfiguration();
        ensureSaveOnClose();
        controller.showWorkspacePage();
    }
}
