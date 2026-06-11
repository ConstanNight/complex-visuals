package org.example;

import org.example.GUI.PanelBuilder;

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

        frame.add(PanelBuilder.buildLayeredPane(), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
