package org.example;

import org.example.math.Analysis;
import org.example.math.ShapeType;

import javax.swing.*;
import java.awt.*;

public class AppWindow {
    private JFrame frame;
    private String title;
    private int width;
    private int height;

    public AppWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        frame = null;
    }

    public void buildWindow() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.add(buildLayeredPane(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public JLayeredPane buildLayeredPane() {
        // Riemann sphere object
        Analysis a = new Analysis(ShapeType.SPHERE, 100,100);
        Component canvas = (Component) a.getChart().getCanvas();

        // Create Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();

        // Add canvas of sphere
        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER);

        // Add control panel
        layeredPane.add(buildControlPanel(a), JLayeredPane.PALETTE_LAYER);

        // Resizes the canvas along with the pane
        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                canvas.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            }
        });

        return layeredPane;
    }

    public JPanel buildControlPanel(Analysis a) {
        JLabel title = new JLabel("Visual Controls");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel controlPanel = PanelFactory.buildControls(title, a);

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBounds(20, 20, 250, 300);
        return controlPanel;
    }
}
