package view;

import model.AbstractData;
import view.contracts.IObserver;
import view.controls.OptionsMenu;

import javax.swing.*;


/**
 * An abstract view component that observes an {@link AbstractData} object.
 * <p> Subclasses must implement the {@link #buildUI()} method to set up their
 * user interface, and the {@link #refreshView()} method to update the view
 * whenever the observed data changes. </p>
 * @param <DataT> the specific subclass of {@link AbstractData} that this view observes
 */
public abstract class AbstractView<DataT extends AbstractData> extends JPanel implements IObserver
{
    /** The data object that this view is observing. */
    protected DataT observedData;

    /** Label used to display a title or other brief textual information. */
    protected JLabel titleLabel;

    /** Context or options menu associated with this view. */
    protected OptionsMenu options;


    /**
     * Constructs a new {@code AbstractView}, setting up its observer relationship
     * with the provided data. <p> Initializes the title label and options menu,
     * and sets the background to {@code null}. </p>
     * @param dataToObserve the data object this view will observe
     */
    public AbstractView(DataT dataToObserve) {
        setObservedData(dataToObserve);
        titleLabel = new JLabel();
        options = new OptionsMenu();
        setBackground(null);
    }


    /**
     * Sets the data object that this view will observe.
     * <p> This method unregisters the view from any previously observed data
     * and registers it as an observer on the new data object. </p>
     * @param dataToObserve the new data object to observe
     */
    public void setObservedData(DataT dataToObserve) {
        if (observedData != null) {
            observedData.removeObserver(this);
        }
        observedData = dataToObserve;
        if (observedData != null) {
            observedData.registerObserver(this);
        }
    }


    /**
     * Returns the data object this view is observing.
     * @return the currently observed data object
     */
    public DataT getObservedData() {
        return observedData;
    }


    /**
     * Called when the observed data notifies this view of a change. <p>
     * The default behavior is to call {@link #refreshView()} so that the view updates itself.
     * Subclasses can override {@link #refreshView()} to customize the update behavior. </p>
     */
    @Override
    public void onNotify() {
        refreshView();
    }


    /**
     * Builds the user interface components for this view.
     * <p> Subclasses must implement this to add their own components to the panel. </p>
     */
    protected abstract void buildUI();


    /**
     * Refreshes the view to reflect the current state of the observed data.
     * <p> Subclasses must implement this to update their displayed information
     * whenever the data changes. </p>
     */
    protected abstract void refreshView();
}