package view.controls;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnumButtonTests {

    @Test
    void constructor_shouldPopulateMenuWithEnumOptions() {
        EnumButton<SampleMode> button = new EnumButton<>("Sort", SampleMode.class, null);

        assertNotNull(button.getMenu());
        assertEquals(4, button.getMenu().getComponentCount()); // 2 items + 2 spacers
    }

    @Test
    void selectingMenuItem_shouldUpdateButtonTextAndInvokeAction() {
        AtomicReference<SampleMode> selected = new AtomicReference<>();
        EnumButton<SampleMode> button = new EnumButton<>("Sort", SampleMode.class, selected::set);

        var item = (javax.swing.JMenuItem) button.getMenu().getComponent(0);
        item.doClick();

        assertEquals("first", button.getText());
        assertEquals(SampleMode.FIRST, selected.get());
    }

    private enum SampleMode {
        FIRST, SECOND;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
