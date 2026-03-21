package view.controls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractButtonTests {

    @Test
    void doClick_shouldRunAssignedAction() {
        Counter counter = new Counter();
        TestButton button = new TestButton(counter::increment);

        button.doClick();

        assertEquals(1, counter.value);
    }

    @Test
    void doClick_shouldNotFailWhenActionIsNull() {
        TestButton button = new TestButton(null);

        assertDoesNotThrow(() -> button.doClick());
    }

    @Test
    void constructor_shouldApplyBasicUiProperties() {
        TestButton button = new TestButton(null);

        assertFalse(button.isFocusable());
        assertEquals("X", button.getText());
        assertNotNull(button.getCursor());
    }

    private static class TestButton extends AbstractButton {
        TestButton(Runnable action) {
            super("X", action);
        }
    }

    private static class Counter {
        int value = 0;

        void increment() {
            value++;
        }
    }
}
