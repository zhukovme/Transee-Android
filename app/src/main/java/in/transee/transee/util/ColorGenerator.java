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
        int hash = 0;

        for (byte b : str.getBytes()) {
            hash = b + ((hash << 5) - hash);
        }

        StringBuilder color = new StringBuilder("#ff");
        for (int i = 0; i < 3; i++) {
            int value = (hash >> (i * 8)) & 0xFF;
            String tmp = "00" + Integer.toHexString(value);
            color.append(tmp.substring(tmp.length() - 2, tmp.length()));
        }
        return Color.parseColor(color.toString());
    }
}
