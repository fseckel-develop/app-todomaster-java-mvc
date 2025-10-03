package model.contracts;

import view.contracts.IObserver;


/**
 * Defines a contract for observable objects which can notify their registered
 * {@link IObserver}s about changes, following the Observer design pattern.
 */
public interface IObservable
{
    /**
     * Registers an observer to receive notifications of changes or events.
     * @param observer The observer to register.
     */
    void registerObserver(IObserver observer);


    /**
     * Removes a registered observer so it will no longer receive notifications.
     * @param observer The observer to remove.
     */
    void removeObserver(IObserver observer);


    /**
     * Notifies all registered observers of a change or event.
     */
    void notifyObservers();
}
