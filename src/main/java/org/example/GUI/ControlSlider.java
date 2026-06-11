package org.example.GUI;

import org.example.math.Analysis;

import javax.swing.*;
import java.util.function.IntConsumer;

public class ControlSlider extends JSlider{
    private IntConsumer update;

    // TODO: Create a method for finding nice tick spacing. Could use smallest divisors of max-min
    public ControlSlider(Analysis a, SliderType type, int min, int max) {
        super(JSlider.HORIZONTAL, min, max, min);
        setMajorTickSpacing((max-min)/3);
        //resolutionSlider.setMinorTickSpacing((max-min)/9); // Doesn't look pretty enough
        setPaintTicks(true);
        setPaintLabels(true);
        setOpaque(false);

        setType(a,type);

        // Only update the 3D model when not dragging.
        addChangeListener(e -> {
            if (!getValueIsAdjusting()) update.accept(getValue());
        });
    }


    private void setType(Analysis a, SliderType type) {
        switch (type) {
            case REZ:
                setValue(a.getSurface().getResolution());
                update = a::updateResolution;
                break;
            case SIZE:
                setValue((int) a.getSurface().getSize());
                update = a::updateSize;
                break;
            default: // This should never happen!
                throw new IllegalArgumentException("Unsupported SliderType: " + type);
        }
    }
}
