package org.example.GUI;

import org.example.math.Analysis;
import org.example.math.ShapeType;

import javax.swing.*;
import java.awt.*;

public class PanelBuilder{
    private final static String DEFAULT_LATEX = "\\frac{\\exp{z}-1}{z}";

    public static JLayeredPane buildLayeredPane() {
        // Riemann sphere object
        Analysis a = new Analysis(ShapeType.SPHERE,DEFAULT_LATEX,100,100);
        Component canvas = (Component) a.getChart().getCanvas();

        // Build Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER);                // Add: analysis canvas
        layeredPane.add(new ControlPanel(a), JLayeredPane.PALETTE_LAYER);   // Add: control panel
        layeredPane.add(new EditorPanel(a), JLayeredPane.PALETTE_LAYER);     // Add: text editor

        // Resizes the canvas along with the pane
        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                canvas.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            }
        });

        return layeredPane;
    }
}
