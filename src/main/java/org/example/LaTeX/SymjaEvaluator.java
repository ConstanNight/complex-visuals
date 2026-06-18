package org.example.LaTeX;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.ComplexNum;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;

import java.util.InputMismatchException;

/**
 * Evaluate math expressions involving complex numbers using Symja.
 * @see SymjaEvaluator#eval(String,IComplexNum)
 */
public class SymjaEvaluator {
    private static final ExprEvaluator EVALUATOR = new ExprEvaluator();
    /**
     * Evaluates a mathematical expression at the complex value of {@code z}.
     * @param map mathematical expression,
     * @param z complex value to be evaluated.
     * @throws InputMismatchException if the expression can't be evaluated to a complex number.
     * @see LaTeXTranslator#translateLaTeX(String)
     */
    public static IComplexNum eval(String map, IComplexNum z) throws InputMismatchException {
        EVALUATOR.defineVariable("z", z);

        IExpr result = EVALUATOR.eval("Quiet[N(" + map + ")]");

        // Ensure that IComplexNumber type is returned
        if (result instanceof IComplexNum)
            return (IComplexNum) result;
        else if (result.isRealResult())
            return ComplexNum.valueOf(result.evalf(), 0.0);
        else if (result.isDirectedInfinity() || result.isInfinity())
            return ComplexNum.INF;
        else
            throw new InputMismatchException("Result could not be resolved to a numeric value.");
    }

    // --- Quick Test ---
    public static void main(String[] args) {
        String complexLatex = "\\frac{\\frac{1}{z}}{0.05}";

        System.out.println("Original: " + complexLatex);
        System.out.println("Translated: " + LaTeXTranslator.translateLaTeX(complexLatex));

        // Evaluate at complex coordinate: z = 1 + 3i
        IComplexNum z = ComplexNum.valueOf(1,3);
        System.out.println("Result at " + z + " : " + eval(complexLatex, z));
    }
}