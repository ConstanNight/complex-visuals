package org.example.math;

import org.jzy3d.colors.Color;

public class Painter {

    public static Color getColorForValue(Complex z) {
        // Get hue
        float hue = (float) (z.arg() / CMath.TAU);

        // mappedMag will be 0 at origin, 1 at magnitude 1, and approach 2 at infinity
        double mappedMag = 4 * Math.atan(z.abs()) / Math.PI;

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