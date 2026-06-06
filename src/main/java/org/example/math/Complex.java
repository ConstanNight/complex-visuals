package org.example.math;

public class Complex extends Number{
    private double real, imag;
    private double abs, arg;

    private Complex(double real, double imag, double abs, double arg) {
        this.real = real;
        this.imag = imag;
        this.abs = abs;
        this.arg = arg;
    }

    public static Complex fromCartesian(double real, double imag) {
        double abs = Math.sqrt(real * real + imag * imag);
        double arg = Math.atan2(imag, real) + Math.PI;
        return new Complex(real, imag, abs, arg);
    }

    public static Complex fromPolar(double magnitude, double angle) {
        if (magnitude == 0)
            return new Complex(0,0,0,0);
        if (angle < 0 || angle > 2*Math.PI) {
            angle -= 2*Math.PI * Math.floor(angle / (2*Math.PI));
        }

        double real = magnitude * Math.cos(angle);
        double imag = magnitude * Math.sin(angle);
        return new Complex(real, imag, magnitude, angle);
    }

    public Complex conj() {
        return new Complex(real, -imag, abs, -arg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Complex z = (Complex) obj;
        return Double.compare(z.real, real) == 0 && Double.compare(z.imag, imag) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(real, imag);
    }

    @Override
    public int intValue() {
        return (int) abs;
    }

    @Override
    public long longValue() {
        return (long) abs;
    }

    @Override
    public float floatValue() {
        return (float) abs;
    }

    @Override
    public double doubleValue() {
        return abs;
    }

    public String toString() {
        return real + ((imag < 0) ? "" : "+") + imag + 'i';
    }

    public double real() {
        return real;
    }

    public double imag() {
        return imag;
    }

    public double abs() {
        return abs;
    }

    public double arg() {
        return arg;
    }
}
