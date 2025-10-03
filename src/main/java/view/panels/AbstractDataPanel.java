package view.panels;

import controller.contracts.IDataPanelListener;
import model.AbstractData;
import view.AbstractView;
import view.controls.OptionsMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Abstract panel that displays and interacts with a specific {@link AbstractData} object.
 * <p> This panel observes a data object and allows the user to edit or delete it through a provided
 * {@link IDataPanelListener}. It also supports clicking the panel itself to select the observed data. </p>
 * @param <DataT> the concrete subclass of {@link AbstractData} that this panel observes
 */
public abstract class AbstractDataPanel<DataT extends AbstractData> extends AbstractView<DataT>
{
    /** The controller that handles user actions such as edit, delete, and selection events. */
    protected final IDataPanelListener<DataT> controller;


    /**
     * Constructs a new {@code AbstractDataPanel}, registering event options and handling null
     * checks on the provided controller.
     * <p>This constructor also adds default options to the panel's {@link OptionsMenu}:</p>
     * <ul>
     *   <li>Edit option with a label provided by {@link #thisEditOptionText()}</li>
     *   <li>Delete option for removing the current observed data item</li>
     * </ul>
     * @param dataToObserve the data object this panel will observe
     * @param controller the listener that will respond to user interactions
     * @throws IllegalArgumentException if {@code controller} is null
     */
    public AbstractDataPanel(DataT dataToObserve, IDataPanelListener<DataT> controller) {
        super(dataToObserve);
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        this.options.addOption(thisEditOptionText(), () -> controller.onEditItemRequested(this.observedData));
        this.options.addOption("Delete " + dataToObserve.getClass().getSimpleName(), () -> controller.onDeleteItemRequested(this.observedData));
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }


    /**
     * Sets up the visual layout and interactive behavior of this data panel.
     * The default implementation:
     * <ul>
     *   <li>Sets a light background color.</li>
     *   <li>Adds a compound border around the panel.</li>
     *   <li>Sets preferred and maximum size to a fixed height of 70 pixels.</li>
     *   <li>Adds a mouse listener that notifies the controller when the panel is left-clicked.</li>
     * </ul>
     */
    @Override
    protected void buildUI() {
        setBackground(Color.getHSBColor(0.0f, 0.0f, 0.99f));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2, 2, 2, 2, Color.getHSBColor(0.0f, 0.0f, 0.95f)),
                        BorderFactory.createLineBorder(Color.getHSBColor(0.0f, 0.0f, 0.50f), 1)
                )
        ));
        setPreferredSize(new Dimension(0, 70));
        setMaximumSize(new Dimension(0, 70));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent onClick) {
                if (SwingUtilities.isLeftMouseButton(onClick)) {
                    controller.onItemSelected(observedData);
                }
            }
        });
    }


    /**
     * Creates a narrow vertical stripe panel for visual accent or separation purposes.
     * @return a {@link JPanel} containing a border and background color to act as a vertical stripe
     */
    protected JPanel buildStripePanel() {
        var bannerStripe = new JPanel(new BorderLayout());
        bannerStripe.setPreferredSize(new Dimension(5, 0));
        bannerStripe.setMaximumSize(new Dimension(5, Integer.MAX_VALUE));
        bannerStripe.setBackground(Color.getHSBColor(0.0f, 0.00f, 0.90f));
        bannerStripe.setBorder(BorderFactory.createMatteBorder(
                0, 1, 0, 1, Color.getHSBColor(0.0f, 0.00f, 0.60f)
        ));
        bannerStripe.setOpaque(true);
        return bannerStripe;
    }


    /**
     * Provides the label text for the "Edit" option in the options menu.
     * <p> Concrete subclasses must implement this method to
     * return a context-specific label for the edit action. </p>
     * @return a {@link String} label for the edit option in the menu
     */
    protected abstract String thisEditOptionText();
}
