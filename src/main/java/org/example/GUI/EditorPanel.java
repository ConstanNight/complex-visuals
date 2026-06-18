package org.example.GUI;

import org.example.AppWindow;
import org.example.LaTeX.LaTeXRenderer;
import org.example.LaTeX.LaTeXUpdater;
import org.example.math.Analysis;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends TranslucentPanel {
    private JTextArea textArea;
    private JLabel previewArea;

    public EditorPanel(Analysis a) {
        super();
        setBounds(20, 320, 300, 400);
        setLayout(new BorderLayout());

        buildComponents(a);
    }

    private void buildComponents(Analysis a) {
        // Build the label of the panel
        JLabel title = new JLabel("Latex Editor");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Bottom padding

        add(title, BorderLayout.NORTH);

        DragFunct.makeDraggable(this, title);

        // Build the text area
        textArea = new JTextArea(a.getLaTeX());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Build the preview area
        previewArea = new JLabel();
        previewArea.setHorizontalAlignment(SwingConstants.CENTER);
        previewArea.setOpaque(true);

        if(AppWindow.darkMode)
            previewArea.setBackground(new Color(40, 40, 40));
        else
            previewArea.setBackground(Color.WHITE);

        // Build split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(textArea), new JScrollPane(previewArea));
        splitPane.setDividerLocation(150);

        add(splitPane, BorderLayout.CENTER);

        // Add Document Listeners
        textArea.getDocument().addDocumentListener(new LaTeXRenderer(this));
        textArea.getDocument().addDocumentListener(new LaTeXUpdater(a,this));
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getPreviewArea() {
        return previewArea;
    }
}
