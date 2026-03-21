package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import view.contracts.IObserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractCollectionObserverTests {

    @Test
    void add_shouldNotifyObservers() {
        DummyCollection collection = new DummyCollection();
        CountingObserver observer = new CountingObserver();
        collection.registerObserver(observer);

        collection.add(new DummyData("A"));

        assertEquals(1, observer.notifications);
    }

    @Test
    void remove_shouldNotifyObservers() {
        DummyCollection collection = new DummyCollection();
        CountingObserver observer = new CountingObserver();
        DummyData item = new DummyData("A");
        collection.add(item);
        collection.registerObserver(observer);

        collection.remove(item);

        assertEquals(1, observer.notifications);
    }

    @Test
    void clear_shouldNotifyObservers() {
        DummyCollection collection = new DummyCollection();
        CountingObserver observer = new CountingObserver();
        collection.add(new DummyData("A"));
        collection.add(new DummyData("B"));
        collection.registerObserver(observer);

        collection.clear();

        assertEquals(1, observer.notifications);
    }

    private static class CountingObserver implements IObserver {
        int notifications = 0;

        @Override
        public void onNotify() {
            notifications++;
        }
    }

    private static class DummyCollection extends AbstractCollection<DummyData> {
        @Override
        public JSONObject toJSON() {
            return super.toJSON();
        }
    }

    private static class DummyData extends AbstractData {
        DummyData(String title) {
            this.title = title;
        }

        @Override
        public JSONObject toJSON() {
            return new JSONObject().put("title", title);
        }
    }
}
