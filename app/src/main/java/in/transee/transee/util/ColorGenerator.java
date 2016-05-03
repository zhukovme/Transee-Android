package in.transee.transee.util;

import android.graphics.Color;

/**
 * @author Michael Zhukov
 */
public class ColorGenerator {

    private static ColorGenerator instance;

    private ColorGenerator() {
    }

    public static ColorGenerator getInstance() {
        if (instance == null) {
            instance = new ColorGenerator();
        }
        return instance;
    }

    public int fromString(String str) {
        String opacity = "#ff";
        String hexColor = String.format(
                opacity + "%06X", (0xeeeeee & str.hashCode()));

        return Color.parseColor(hexColor);
    }
}
