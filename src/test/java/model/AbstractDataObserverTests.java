package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import view.contracts.IObserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractDataObserverTests {

    @Test
    void registerObserver_andNotifyObservers_shouldNotifyRegisteredObserver() {
        DummyData data = new DummyData();
        CountingObserver observer = new CountingObserver();

        data.registerObserver(observer);
        data.notifyObservers();

        assertEquals(1, observer.notifications);
    }

    @Test
    void removeObserver_shouldStopNotifications() {
        DummyData data = new DummyData();
        CountingObserver observer = new CountingObserver();

        data.registerObserver(observer);
        data.removeObserver(observer);
        data.notifyObservers();

        assertEquals(0, observer.notifications);
    }

    @Test
    void setTitle_shouldNotifyObservers() {
        DummyData data = new DummyData();
        CountingObserver observer = new CountingObserver();
        data.registerObserver(observer);

        data.setTitle("Updated");

        assertEquals(1, observer.notifications);
    }

    private static class CountingObserver implements IObserver {
        int notifications = 0;

        @Override
        public void onNotify() {
            notifications++;
        }
    }

    private static class DummyData extends AbstractData {
        @Override
        public JSONObject toJSON() {
            return new JSONObject().put("title", title);
        }
    }
}
