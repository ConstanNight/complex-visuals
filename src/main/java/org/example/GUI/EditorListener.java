package org.example.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class EditorListener implements DocumentListener {
    protected JTextArea textArea;
    protected JLabel previewArea;

    public EditorListener(EditorPanel ep) {
        this.textArea = ep.getTextArea();
        this.previewArea = ep.getPreviewArea();
    }

    protected abstract void update();

    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }
}
