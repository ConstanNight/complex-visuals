package org.example.math;

import org.jzy3d.colors.Color;
import org.matheclipse.core.interfaces.IComplexNum;

public class Painter {

    public static Color getColorForValue(IComplexNum z) {
        if (z.isNaN()) return Color.MAGENTA;

        // Get hue using the angle of z
        double angle = Math.atan2(z.getImaginaryPart(), z.getRealPart());

        float hue = 0.5f * (float) (angle / Math.PI);
        // hue fix
        if (hue < 0) hue += 1.0f;
        if (hue >= 1.0f) hue -= 1.0f;


        // mappedMag will be 0 at origin, 1 at magnitude 1, and approach 2 at infinity
        double mappedMag = 4 * Math.atan(z.dabs()) / Math.PI;

        float saturation;
        float brightness;

        if (mappedMag < 1) {
            // Magnitude < 1: Keep full color, fade from Black to Pure Color
            saturation = 1.0f;
            brightness = (float) mappedMag;
        } else {
            // Magnitude > 1: Keep max brightness, fade from Pure Color to White
            brightness = 1.0f;
            saturation = (float) (2-mappedMag);
        }

        // Get RGB integer and return jzy3d color with alpha 200
        int rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
        return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 200);
    }
}