package model;

import model.contracts.*;
import view.contracts.IObserver;

import java.util.ArrayList;


/**
 * Abstract base class for data models that support observation and serialization.
 * Implements the {@link IObservable} and {@link ISerializable} interfaces.
 * Provides basic support for managing observers and handling a titled data element.
 */
public abstract class AbstractData implements IObservable, ISerializable
{
    /** The list of observers registered to receive notifications. */
    private final ArrayList<IObserver> observers = new ArrayList<>();

    /** The title of the data object. */
    protected String title = "";


    /** {@inheritDoc} */
    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }


    /** {@inheritDoc} */
    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }


    /** {@inheritDoc} */
    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.onNotify();
        }
    }


    /**
     * Sets the title of this data object and notifies observers.
     * @param title new title to set; must not be null or empty.
     * @throws IllegalArgumentException if the title is null or empty
     */
    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Data must have a title.");
        }
        this.title = title;
        notifyObservers();
    }


    /** Returns the title of this data object. */
    public String getTitle() {
        return title;
    }
}
