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
        shape = new Shape(constructWith(size, rez));
        this.size = size;
        this.rez = rez;
    }

    private static @NonNull List<Polygon> constructWith(double size, int rez) throws RuntimeException {

        if (size < 0)
            throw new RuntimeException("Size can't be negative!");
        if (size == 0)
            throw new RuntimeException("Size can't be zero!");

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

                quad.setColor(new Color(0, 150, 255, 200));
                quad.setWireframeColor(Color.WHITE);
                quad.setWireframeDisplayed(true);

                polygons.add(quad);
            }
        }
        return polygons;
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
        shape = new Shape(constructWith(size, rez));
        shape.setWireframeDisplayed(showWireframe);
    }

    @Override
    public void setWireframe(boolean status) {
        showWireframe = status;
        shape.setWireframeDisplayed(status);
    }
}
