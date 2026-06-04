package org.example;

import javax.swing.*;
import java.awt.*;

public class PanelFactory{
    public static JPanel createPanel(JLabel label, Analysis a) {
        JPanel panel =  new JPanel();

        panel.add(label);

        panel.setBackground(new Color(240, 240, 240, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer

        // Adds CheckBox
        JCheckBox wireframeBox;
        {
            boolean defaultValue = true;
            wireframeBox = new JCheckBox("Show Wireframe Grid", defaultValue);
            a.setWireframeDisplayed(defaultValue);
        }
        wireframeBox.addActionListener(e -> {
            a.setWireframeDisplayed(wireframeBox.isSelected());
        });
        panel.add(wireframeBox);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Adds Slider: {Min: 10, Max: 200, Default: 100}
        panel.add(new JLabel("Mesh Resolution:"));
        JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        resolutionSlider.setMajorTickSpacing(50);
        resolutionSlider.setMinorTickSpacing(10);
        resolutionSlider.setPaintTicks(true);
        resolutionSlider.setPaintLabels(true);
        resolutionSlider.setOpaque(false);

        resolutionSlider.addChangeListener(e -> {
            // Only update the 3D model when the user STOPS dragging.
            // If we update on every single pixel drag, the app will freeze.
            if (!resolutionSlider.getValueIsAdjusting()) {
                a.updateResolution(resolutionSlider.getValue());
            }
        });
        panel.add(resolutionSlider);


        return panel;
    }
}
