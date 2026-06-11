package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragFunct {
    public static void makeDraggable(JComponent draggable) {
        makeDraggable(draggable, draggable);
    }

    public static void makeDraggable(JComponent draggable, JComponent handle) {
        MouseAdapter dragListener = new MouseAdapter() {
            private int initX, initY;

            @Override
            public void mousePressed(MouseEvent e) {
                // Get initial panel location
                initX = e.getX();
                initY = e.getY();

                // Draws draggable above the other components
                draggable.getParent().setComponentZOrder(draggable, 0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // New panel Location
                int newX = draggable.getX() + e.getX() - initX;
                int newY = draggable.getY() + e.getY() - initY;

                draggable.setLocation(newX, newY);
            }
        };

        // Attach listeners to the handle
        handle.addMouseListener(dragListener);
        handle.addMouseMotionListener(dragListener);
    }
}
