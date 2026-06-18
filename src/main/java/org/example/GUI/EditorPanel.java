package org.example.GUI;

import org.example.AppWindow;
import org.example.math.Analysis;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        textArea = new JTextArea("\\exp{z}");
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

        // Add Document Listener
        textArea.getDocument().addDocumentListener(new LatexRenderer(textArea, previewArea));

        // TODO: Update only for valid previews, otherwise the lag is unendurable
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                a.updateMap(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                a.updateMap(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                a.updateMap(textArea.getText());
            }
        });
    }
}
