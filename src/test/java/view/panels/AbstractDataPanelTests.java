package view.panels;

import controller.contracts.IDataPanelListener;
import model.AbstractData;
import view.controls.OptionsMenu;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

class AbstractDataPanelTests {

    @Test
    void constructor_shouldThrowWhenControllerIsNull() {
        DummyData data = new DummyData("A");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new TestDataPanel(data, null)
        );

        assertEquals("Controller cannot be null", ex.getMessage());
    }

    @Test
    void leftClick_shouldNotifyControllerAboutSelection() {
        DummyData data = new DummyData("A");
        RecordingListener listener = new RecordingListener();
        TestDataPanel panel = new TestDataPanel(data, listener);

        MouseEvent event = new MouseEvent(
                panel,
                MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(),
                0,
                10,
                10,
                1,
                false,
                MouseEvent.BUTTON1
        );

        for (var mouseListener : panel.getMouseListeners()) {
            mouseListener.mouseClicked(event);
        }

        assertSame(data, listener.selected);
    }

    @Test
    void buildStripePanel_shouldCreateConfiguredStripe() {
        DummyData data = new DummyData("A");
        RecordingListener listener = new RecordingListener();
        TestDataPanel panel = new TestDataPanel(data, listener);

        JPanel stripe = panel.exposeStripe();

        assertEquals(new Dimension(5, 0), stripe.getPreferredSize());
        assertTrue(stripe.isOpaque());
    }

    @Test
    void editOption_shouldNotifyController() {
        DummyData data = new DummyData("A");
        RecordingListener listener = new RecordingListener();
        TestDataPanel panel = new TestDataPanel(data, listener);

        JMenuItem editItem = (JMenuItem) panel.exposeOptionsMenu().getComponent(0);
        editItem.doClick();

        assertSame(data, listener.edited);
    }

    @Test
    void deleteOption_shouldNotifyController() {
        DummyData data = new DummyData("A");
        RecordingListener listener = new RecordingListener();
        TestDataPanel panel = new TestDataPanel(data, listener);

        JMenuItem deleteItem = (JMenuItem) panel.exposeOptionsMenu().getComponent(2);
        deleteItem.doClick();

        assertSame(data, listener.deleted);
    }

    private static class TestDataPanel extends AbstractDataPanel<DummyData> {

        TestDataPanel(DummyData data, IDataPanelListener<DummyData> controller) {
            super(data, controller);
            buildUI();
        }

        JPanel exposeStripe() {
            return buildStripePanel();
        }

        @Override
        protected String thisEditOptionText() {
            return "Edit Dummy";
        }

        @Override
        protected void refreshView() {
        }

        OptionsMenu exposeOptionsMenu() {
            return options;
        }
    }

    private static class RecordingListener implements IDataPanelListener<DummyData> {
        DummyData edited;
        DummyData deleted;
        DummyData selected;

        @Override
        public void onEditItemRequested(DummyData itemToEdit) {
            edited = itemToEdit;
        }

        @Override
        public void onDeleteItemRequested(DummyData itemToDelete) {
            deleted = itemToDelete;
        }

        @Override
        public void onItemSelected(DummyData selectedItem) {
            selected = selectedItem;
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
