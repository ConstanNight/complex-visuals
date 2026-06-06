package org.example.math;

public class CMath {
    public static final double TAU = 2 * Math.PI;

    public static Complex exp(Complex z) {
        return Complex.fromPolar(Math.exp(z.real()), z.imag());
    }

    public static Complex scale(Complex z, double s) {
        return Complex.fromPolar(z.abs()*s, z.arg());
    }

    public static Complex inverse(Complex z) {
        return Complex.fromPolar(1/z.abs(), -z.arg());
    }

    public static Complex sum(Complex a, Complex b) {
        return Complex.fromCartesian(a.real()+b.real(), a.imag()+b.imag());
    }

    public static Complex sum(Complex a, double b) {
        return Complex.fromCartesian(a.real()+b, a.imag());
    }

    public static Complex sum(double a, Complex b) {
        return Complex.fromCartesian(a+b.real(), b.imag());
    }
}
