package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class TranslucentPanel extends JPanel {

    public static final Color DEFAULT_BG_COLOR = new Color(220, 220, 220, 200);

    public TranslucentPanel() {
        super();

        // Sets translucent, using the overwritten paint component
        setOpaque(false);
        setBackground(DEFAULT_BG_COLOR);

        // Sets a thin gray border along with an empty space
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
