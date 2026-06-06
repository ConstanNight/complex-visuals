package org.example.math;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.ArrayList;
import java.util.List;

public class RiemannSphere implements MathSurface{
    private double size;
    private int rez;
    private Shape shape;
    private boolean showWireframe;

    public RiemannSphere (double size, int rez) {
        shape = buildShape(size, rez);
        this.size = size;
        this.rez = rez;
    }

    private static @NonNull Shape buildShape(double size, int rez) throws RuntimeException {
        if (size < 0 || size > 100)
            throw new RuntimeException("Size must be between 0 and 100!");

        // The polygons used for the shape
        List<Polygon> polygons = new ArrayList<>();

        double dTheta = (2 * Math.PI) / rez;
        double dPhi = size/(100 * rez) * Math.PI;

        // Go through each dTheta^dPhi and add it to the list
        for (int i = 0; i < rez; ++i) {
            for (int j = 0; j < rez; ++j) {
                // Calculate polar coordinates
                double theta0 = i * dTheta;
                double theta1 = (i + 1) * dTheta;
                double phi0 = j * dPhi;
                double phi1 = (j + 1) * dPhi;

                // Build colored quadrilateral
                Polygon quad = new Polygon();
                quad.add(createColoredPoint(theta0, phi0));
                quad.add(createColoredPoint(theta1, phi0));
                quad.add(createColoredPoint(theta1, phi1));
                quad.add(createColoredPoint(theta0, phi1));
                quad.setWireframeColor(Color.WHITE);

                polygons.add(quad);
            }
        }

        return new Shape(polygons);
    }

    private static Point createColoredPoint(double theta, double phi) {
        // Build Cartesian coordinates
        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = -Math.cos(phi);
        Coord3d coord = new Coord3d(x, y, z);

        // Find the appropriate color
        Complex w = CMath.exp(Complex.fromPolar(Math.tan(phi / 2.0), theta)) ;

        return new Point(coord, Painter.getColorForValue(w));
    }

    private static @NonNull Shape getStereoProj(double size, int rez) throws RuntimeException {

        if (size < 0)
            throw new RuntimeException("Size can't be negative!");

        List<Polygon> polygons = new ArrayList<>();
        double stepSize = 2 * size / rez;

        for (int i=0; i<rez; ++i) {
            for (int j = 0; j < rez; ++j) {
                double x0 = i * stepSize - size;
                double y0 = j * stepSize - size;
                double x1 = x0 + stepSize;
                double y1 = y0 + stepSize;

                Polygon quad = new Polygon();
                quad.add(getStereoProj(x0, y0));
                quad.add(getStereoProj(x1, y0));
                quad.add(getStereoProj(x1, y1));
                quad.add(getStereoProj(x0, y1));

                double centerX = (x0 + x1) / 2.0;
                double centerY = (y0 + y1) / 2.0;
                quad.setColor(new Color(0, 150, 255, 200)); // Plain blue color
                quad.setWireframeColor(Color.WHITE);
                quad.setWireframeDisplayed(true);

                polygons.add(quad);
            }
        }
        return new Shape(polygons);
    }

    private static Point getStereoProj(double X, double Y) {
        double d = 1 + X*X + Y*Y;
        double x = 2 * X / d;
        double y = 2 * Y / d;
        double z = 1 - 2 / d;

        return new Point(new Coord3d(x, y, z));
    }


    @Override
    public double getSize() {
        return size;
    }

    @Override
    public int getResolution() {
        return rez;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void update(double size, int rez) {
        this.size = size;
        this.rez = rez;
        shape = buildShape(size, rez);
        shape.setWireframeDisplayed(showWireframe);
    }

    @Override
    public void setWireframe(boolean status) {
        showWireframe = status;
        shape.setWireframeDisplayed(status);
    }
}
