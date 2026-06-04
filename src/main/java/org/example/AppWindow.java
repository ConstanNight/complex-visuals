package org.example;

import org.example.math.Analysis;
import org.example.math.ShapeType;
import org.jzy3d.chart.Chart;

import javax.swing.*;
import java.awt.*;

public class AppWindow {
    private JFrame frame;
    private JPanel controlPanel;
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

        Analysis a = new Analysis(ShapeType.SPHERE, 3,100);
        Chart chart = a.getChart();

        // Create Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();
        frame.add(layeredPane, BorderLayout.CENTER);

        // Add canvas layer
        layeredPane.add((Component) chart.getCanvas(), JLayeredPane.DEFAULT_LAYER);

        // Create control panel
        JLabel title = new JLabel("Visual Controls");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        controlPanel = PanelFactory.createPanel(title, a);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBounds(20, 20, 250, 300);

        layeredPane.add(controlPanel, JLayeredPane.PALETTE_LAYER);

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = layeredPane.getWidth();
                int h = layeredPane.getHeight();

                // Stretch the 3D canvas to fill the entire window
                ((Component) chart.getCanvas()).setBounds(0, 0, w, h);
            }
        });

        frame.setVisible(true);
    }
}
