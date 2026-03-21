package view.controls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuButtonTests {

    @Test
    void constructor_shouldCreateEmptyMenuWhenNullIsPassed() {
        MenuButton button = new MenuButton("Menu", null);

        assertNotNull(button.getMenu());
        assertEquals(0, button.getMenu().getComponentCount());
    }

    @Test
    void constructor_shouldUseProvidedMenu() {
        OptionsMenu menu = new OptionsMenu();
        MenuButton button = new MenuButton("Menu", menu);

        assertSame(menu, button.getMenu());
    }
}
