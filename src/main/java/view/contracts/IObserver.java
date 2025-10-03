package view.contracts;


/**
 * Defines a contract for observer classes that wish to be notified when
 * the observed model has changed, following the Observer design pattern.
 */
public interface IObserver
{
    /**
     * Defines all actions to react with when the observed object notifies.
     */
    void onNotify();
}
