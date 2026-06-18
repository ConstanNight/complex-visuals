package org.example.LaTeX;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Translate LaTeX formatted strings into math expressions that can be used by {@link SymjaEvaluator}.
 */
public class LaTeXTranslator {
    /**
     * LaTeX to Symja dictionary.
     */
    private static final Map<String, String> TRANSLATION_DICT = new LinkedHashMap<>();

    static {
        // Formatting
        TRANSLATION_DICT.put("\\cdot", "*");
        TRANSLATION_DICT.put("\\times", "*");
        TRANSLATION_DICT.put("\\left(", "(");
        TRANSLATION_DICT.put("\\right)", ")");
        TRANSLATION_DICT.put("{", "(");
        TRANSLATION_DICT.put("}", ")");

        // Functions
        TRANSLATION_DICT.put("\\sinh", "Sinh");
        TRANSLATION_DICT.put("\\sin", "Sin");
        TRANSLATION_DICT.put("\\cosh", "Cosh");
        TRANSLATION_DICT.put("\\cos", "Cos");
        TRANSLATION_DICT.put("\\tan", "Tan");
        TRANSLATION_DICT.put("\\exp", "Exp");
    }

    /**
     * Translates the string {@code latexString} to a mathematical expression Symja can evaluate.
     *
     * @param latexString mathematical expression,
     */
    public static String translateLaTeX(String latexString) {
        // Strip all whitespaces and resolve all nested fractions
        String clean = latexString.replaceAll("\\s+", "");
        clean = resolveNestedFractions(clean);

        for (Map.Entry<String, String> entry : TRANSLATION_DICT.entrySet()) {
            clean = clean.replace(entry.getKey(), entry.getValue());
        }

        return clean;
    }

    /**
     * Recursively replaces LaTeX formated fractions '\frac{num}{dom}', with infix operator notation '((num)/(dom))', that {@link SymjaEvaluator} can read.
     * @param input input string
     * @return a new string in infix notation
     */
    private static String resolveNestedFractions(String input) {
        int fracIndex = input.indexOf("\\frac");
        /// Base case: no fractions left
        if (fracIndex == -1) return input;

        int [] currentIndex = {fracIndex + 5};

        // Get numerator
        String numerator = extractArgument(input,currentIndex);
        numerator = resolveNestedFractions(numerator);

        // Get denominator
        String denominator = extractArgument(input,currentIndex);
        denominator = resolveNestedFractions(denominator);

        // Get the before and after substrings
        String before = input.substring(0, fracIndex);
        String after = input.substring(currentIndex[0]);

        return resolveNestedFractions(before + "((" + numerator + ")/(" + denominator + "))" + after);
    }

    /**
     * This method is used in the extraction of the numerator and denominator of a LaTeX formatted fraction.
     * @param input string containing fraction.
     * @param index array of length 1 used to keep track of the position inside the string.
     * @return substring of {@code input} that is either the numerator or denominator of the fraction.
     */
    private static String extractArgument(String input, int[] index) {
        int start = index[0];

        if (start >= input.length()) return "1"; // To have "\\frac{num}\0" default to "\\frac{num}{1}\0"

        char first = input.charAt(start);

        return switch (first) {
            case '{' -> { // Braced group
                int end = matchingBrace(input, ++start);
                if (end < input.length())
                    index[0] = end + 1;
                else
                    index[0] = input.length();
                yield input.substring(start, end);
            }

            case '\\' -> { // LaTeX command
                int end = start + 1;
                while (end < input.length() && Character.isLetter(input.charAt(end))) ++end;
                yield input.substring(start, index[0] = end);
            }

            default -> { // Single character
                ++index[0];
                yield  String.valueOf(first);
            }
        };
    }


    private static int matchingBrace(String s, int start) {
        int count = 1;
        int i;
        for (i = start; i < s.length(); i++) {
            if (s.charAt(i) == '{') count++;
            else if (s.charAt(i) == '}') count--;

            if (count == 0) return i;
        }
        return i;
    }
}