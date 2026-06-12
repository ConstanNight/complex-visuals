package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App complexVisuals = new App("Complex Analysis Visualizer", 1500, 1000);
        });
    }
}