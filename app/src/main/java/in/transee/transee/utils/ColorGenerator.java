package in.transee.transee.utils;

import android.graphics.Color;

/**
 * @author Michael Zhukov
 */
public class ColorGenerator {

    private static final int COLOR_TRANSPARENCY = 190;

    private static int r, g, b;

    public static int fromString(String str) {
        switch (str) {
            case "trolleybus":
                return colorFromHex(0x4b0082);
            case "autobus":
                return colorFromHex(0x058205);
            case "tram":
                return colorFromHex(0xffa500);
            case "minibus_taxi":
                return colorFromHex(0x800000);
            default:
                hashAsArgb(str);
                return Color.argb(COLOR_TRANSPARENCY, r, g, b);
        }
    }

    private static int colorFromHex(int hex) {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        return Color.argb(COLOR_TRANSPARENCY, r, g, b);
    }

    private static void hashAsArgb(String str) {
        byte[] hash = str.getBytes();
        r = hash[1] << 2;
        g = hash[2] << 3;
        b = hash[3] << 4;
    }
}
