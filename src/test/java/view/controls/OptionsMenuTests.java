package view.controls;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class OptionsMenuTests {

    @Test
    void addOption_shouldAddMenuItemAndSpacer() {
        OptionsMenu menu = new OptionsMenu();

        menu.addOption("Delete", () -> {});

        assertEquals(2, menu.getComponentCount());
        assertInstanceOf(JMenuItem.class, menu.getComponent(0));
        assertInstanceOf(Box.Filler.class, menu.getComponent(1));
    }

    @Test
    void addedMenuItem_shouldRunActionWhenClicked() {
        OptionsMenu menu = new OptionsMenu();
        AtomicInteger counter = new AtomicInteger();

        menu.addOption("Delete", counter::incrementAndGet);

        JMenuItem item = (JMenuItem) menu.getComponent(0);
        item.doClick();

        assertEquals(1, counter.get());
    }

    @Test
    void constructor_shouldSetHandCursor() {
        OptionsMenu menu = new OptionsMenu();

        assertEquals(Cursor.HAND_CURSOR, menu.getCursor().getType());
    }
}
