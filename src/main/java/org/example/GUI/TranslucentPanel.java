package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class TranslucentPanel extends JPanel {

    private static final int ALPHA = 200;
    public static final Color DEFAULT_BG_COLOR = new Color(220, 220, 220, ALPHA);

    public TranslucentPanel() {
        super();


        Color themeBg = UIManager.getColor("Panel.background");

        // Sets translucent, using the overwritten paint component
        setOpaque(false);
        if (themeBg != null)
            setBackground(new Color(themeBg.getRed(), themeBg.getGreen(), themeBg.getBlue(), ALPHA));
        else
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
