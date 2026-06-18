package org.example.LaTeX;

import org.example.GUI.EditorListener;
import org.example.GUI.EditorPanel;
import org.example.math.Analysis;
import org.matheclipse.core.expression.ComplexNum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaTeXUpdater extends EditorListener {
    private static final Logger logger = LoggerFactory.getLogger(LaTeXUpdater.class);
    private Analysis a;

    public LaTeXUpdater(Analysis a, EditorPanel ep) {
        super(ep);
        this.a = a;
    }

    protected void update() {
        if(previewArea.getIcon() == null) return;

        String latex = textArea.getText();

        try {
            // Make a dry run by testing a semi-complicated number
            ComplexNum testValue = ComplexNum.valueOf(Math.PI+1,Math.PI-1);
            // This will throw an exception in the case of failure
            SymjaEvaluator.eval(LaTeXTranslator.translateLaTeX(latex),testValue);

            // The mapping will update only for valid maps
            a.updateLaTeX(latex);
        } catch (Exception e) {
            logger.debug("Evaluation failed for string: '{}'", latex);
        }
    }
}
