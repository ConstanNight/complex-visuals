package org.example;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.math.Analysis;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public class PanelFactory{
    public static @NonNull JPanel buildControls(JLabel label, Analysis a) {
        JPanel panel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //g.setColor(new Color(240, 240, 240, 220));
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);
        panel.add(label);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer

        // Adds CheckBox
        JCheckBox wireframeBox;
        {
            boolean defaultValue = false;
            wireframeBox = new JCheckBox("Show Wireframe Grid", defaultValue);
            wireframeBox.setOpaque(false);
            a.setWireframeDisplayed(defaultValue);
        }
        wireframeBox.addActionListener(e -> {
            a.setWireframeDisplayed(wireframeBox.isSelected());
        });
        panel.add(wireframeBox);

        // Add Resolution Slider
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        panel.add(new JLabel("Mesh Resolution:"));
        JSlider resolutionSlider = buildSlider(a, SliderType.REZ, 50, 200);
        panel.add(resolutionSlider);

        // Add Size Slider
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        panel.add(new JLabel("Plane Percentage:"));
        JSlider sizeSlider = buildSlider(a, SliderType.SIZE, 0, 100);
        panel.add(sizeSlider);

        return panel;
    }

    // TODO: Create a method for finding nice tick spacing. Could use smallest divisors of max-min
    private static @NonNull JSlider buildSlider(Analysis a, SliderType type, int min, int max) {
        int defaultValue;
        IntConsumer update;
        switch (type) {
            case REZ:
                defaultValue = a.getSurface().getResolution();
                update = a::updateResolution;
                break;
            case SIZE:
                defaultValue = (int) a.getSurface().getSize();
                update = a::updateSize;
                break;
            default: // This should never happen!
                throw new IllegalArgumentException("Unsupported SliderType: " + type);
        }
        JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL, min, max, defaultValue);
        resolutionSlider.setMajorTickSpacing((max-min)/3);
        //resolutionSlider.setMinorTickSpacing((max-min)/9); // Doesn't look pretty enough
        resolutionSlider.setPaintTicks(true);
        resolutionSlider.setPaintLabels(true);
        resolutionSlider.setOpaque(false);

        resolutionSlider.addChangeListener(e -> {
            // Only update the 3D model when the user STOPS dragging.
            if (!resolutionSlider.getValueIsAdjusting()) {
                update.accept(resolutionSlider.getValue());
            }
        });
        return resolutionSlider;
    }
}
