package org.example.GUI;

import org.scilab.forge.jlatexmath.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LatexRenderer implements DocumentListener {

    private JTextArea inputArea;
    private JLabel previewArea;

    public LatexRenderer(JTextArea inputArea, JLabel previewArea) {
        this.inputArea = inputArea;
        this.previewArea = previewArea;
        updatePreview();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updatePreview();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updatePreview();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updatePreview();
    }

    private void updatePreview() {
        String latex = inputArea.getText();

        try {
            // Check for empty string
            if (latex.trim().isEmpty())
                throw new EmptyFormulaException();

            // Render the LaTeX string using JLaTeXMath
            TeXFormula formula = new TeXFormula(latex);
            TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
            icon.setInsets(new Insets(10, 10, 10, 10));

            previewArea.setIcon(icon);
            previewArea.setText("");
        } catch (ParseException | EmptyFormulaException ex) {
            // Gracefully handle partial/invalid LaTeX during typing
            previewArea.setIcon(null);
            previewArea.setText("Waiting for valid TeX...");
        }
    }
}