package org.example.LaTeX;

import org.example.GUI.EditorListener;
import org.example.GUI.EditorPanel;
import org.scilab.forge.jlatexmath.*;

import java.awt.*;

public class LaTeXRenderer extends EditorListener {

    public LaTeXRenderer(EditorPanel ep) {
        super(ep);
        update();
    }

    @Override
    protected void update() {
        String latex = textArea.getText();

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