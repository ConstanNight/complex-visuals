package org.example.GUI;

import org.example.math.Analysis;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public ControlPanel(Analysis a) {
        super();
        // Sets translucent, using the overwritten paint component
        setOpaque(false);
        setBackground(new Color(220, 220, 220, 200));

        // Sets the panel's overall look
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(20, 20, 250, 280);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        buildComponents(a);
    }

    private void buildComponents(Analysis a) {
        // Build a label for this panel
        JLabel title = new JLabel("Visual Controls");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        add(title);

        DragFunct.makeDraggable(this, title);

        add(Box.createRigidArea(new Dimension(0, 15)),1); // Spacer

        addCheckBoxes(a);

        addSliders(a);
    }

    private void addCheckBoxes(Analysis a) {
        // Builds Check Box
        boolean defaultValue = false;
        JCheckBox wireframeBox = new JCheckBox("Show Wireframe Grid", defaultValue);
        wireframeBox.setOpaque(false);
        a.setWireframeDisplayed(defaultValue);

        wireframeBox.addActionListener(e -> {
            a.setWireframeDisplayed(wireframeBox.isSelected());
        });

        add(wireframeBox);
    }

    private void addSliders(Analysis a) {
        // Build Resolution Slider
        add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        add(new JLabel("Mesh Resolution:"));
        add(new ControlSlider(a, SliderType.REZ, 50, 200));

        // Build Size Slider
        add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        add(new JLabel("Plane Percentage:"));
        add(new ControlSlider(a, SliderType.SIZE, 0, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
