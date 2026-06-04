package org.example;


import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App complexVisuals = new App("Complex Analysis Visualizer", 1000, 700);
        });
    }
}