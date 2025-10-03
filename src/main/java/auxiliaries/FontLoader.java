package auxiliaries;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;


/**
 * Utility class for loading and providing font families consisting of
 * regular, bold, italic, and bold-italic styles. Fonts must be located
 * on the classpath using the naming convention:
 * {@code <familyName>-Regular.ttf},
 * {@code <familyName>-Bold.ttf},
 * {@code <familyName>-Italic.ttf},
 * {@code <familyName>-BoldItalic.ttf}.
 * <p> Fonts are loaded once and cached for future retrieval. </p>
 */
public class FontLoader
{
    /** Represents a family of fonts including regular, bold, italic, and bold-italic variants. */
    private record FontFamily(Font regular, Font bold, Font italic, Font boldItalic)
    {
        /**
         * Retrieves the font corresponding to the specified style.
         * @param style one of the {@link Font} style constants:
         * {@link Font#PLAIN}, {@link Font#BOLD}, {@link Font#ITALIC},
         * or {@code Font.BOLD | Font.ITALIC}
         * @return the font variant matching the style
         */
        Font getFont(int style) {
            return switch (style) {
                case Font.BOLD | Font.ITALIC -> boldItalic;
                case Font.BOLD -> bold;
                case Font.ITALIC -> italic;
                default -> regular;
            };
        }
    }


    /** Cache for all font families that already had been loaded. */
    private static final HashMap<String, FontFamily> fontFamilies = new HashMap<>();


    /**
     * Loads a font file from the classpath as a TrueType font
     * and registers it with the local graphics environment.
     * @param path the classpath-relative path to the font file
     * @return the loaded {@link Font}
     * @throws Exception if the font cannot be loaded or registered
     */
    private static Font loadFont(String path) throws Exception {
        try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Missing font: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        }
    }


    /**
     * Loads all four variants (regular, bold, italic, bold-italic) for the specified font family name.
     * Fonts must follow the convention: <code>&lt;familyName&gt;-Regular.ttf</code>, etc.
     * This method is idempotent: subsequent calls with the same family name will do nothing.
     * @param name the base name of the font family
     */
    public static void loadFamily(String name) {
        if (fontFamilies.containsKey(name)) return;
        try {
            FontFamily family = new FontFamily(
                loadFont("/fonts/" + name + "-Regular.ttf"),
                loadFont("/fonts/" + name + "-Bold.ttf"),
                loadFont("/fonts/" + name + "-Italic.ttf"),
                loadFont("/fonts/" + name + "-BoldItalic.ttf")
            );
            fontFamilies.put(name, family);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    /**
     * Retrieves the specified font style and size for the named font family.
     * If the family is not yet loaded, this method attempts to load it.
     * Falls back to the platform's default SansSerif font if the requested
     * family cannot be loaded.
     * @param name  the font family name (as loaded with {@link #loadFamily(String)})
     * @param style the style constant ({@link Font#PLAIN}, {@link Font#BOLD},
     * {@link Font#ITALIC}, or {@code Font.BOLD | Font.ITALIC})
     * @param size  the desired font size
     * @return a derived {@link Font} instance of the requested style and size
     */
    public static Font getFont(String name, int style, float size) {
        FontFamily fontFamily = fontFamilies.get(name);
        if (style < 0 || style > 3) style = Font.PLAIN;
        if (fontFamily == null) {
            loadFamily(name);
            fontFamily = fontFamilies.get(name);
            if (fontFamily == null) return new Font("SansSerif", Font.PLAIN, (int) size);
        }
        return fontFamily.getFont(style).deriveFont(size);
    }
}