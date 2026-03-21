package view;

import model.AbstractData;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractViewTests {

    @Test
    void setObservedData_shouldRegisterOnNewData() {
        DummyData data = new DummyData();
        TestView view = new TestView(data);

        data.notifyObservers();

        assertEquals(1, view.refreshCount);
    }

    @Test
    void setObservedData_shouldUnregisterFromPreviousData() {
        DummyData first = new DummyData();
        DummyData second = new DummyData();
        TestView view = new TestView(first);

        view.setObservedData(second);

        first.notifyObservers();
        assertEquals(0, view.refreshCount);

        second.notifyObservers();
        assertEquals(1, view.refreshCount);
    }

    @Test
    void onNotify_shouldCallRefreshView() {
        DummyData data = new DummyData();
        TestView view = new TestView(data);

        view.onNotify();

        assertEquals(1, view.refreshCount);
    }

    @Test
    void getObservedData_shouldReturnObservedData() {
        DummyData data = new DummyData();
        TestView view = new TestView(data);

        assertSame(data, view.getObservedData());
    }

    private static class TestView extends AbstractView<DummyData> {
        int refreshCount = 0;

        TestView(DummyData data) {
            super(data);
        }

        @Override
        protected void buildUI() {
        }

        @Override
        protected void refreshView() {
            refreshCount++;
        }
    }

    private static class DummyData extends AbstractData {
        @Override
        public JSONObject toJSON() {
            return new JSONObject().put("title", title);
        }
    }
}
