package in.transee.transee.util;

import android.graphics.Color;

/**
 * @author Michael Zhukov
 */
public enum ColorGenerator {

    INSTANCE;

    public int fromString(String str) {
        String opacity = "#ff";
        String hexColor = String.format(
                opacity + "%06X", (0xeeeeee & str.hashCode()));

        return Color.parseColor(hexColor);
    }
}
