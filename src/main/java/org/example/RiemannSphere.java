package org.example;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.ArrayList;
import java.util.List;

public class RiemannSphere extends Shape{
    private final double SIZE;
    private final int STEPS;

    public RiemannSphere (double size, int steps) throws RuntimeException {
        super(RiemannSphere.constructWith(size, steps));
        SIZE = size;
        STEPS = steps;
    }

    private static List<Polygon> constructWith(double bounds, int steps) {
        if (bounds < 0)
            throw new RuntimeException("Bounds can't be negative!");
        if (bounds == 0)
            throw new RuntimeException("Bounds can't be zero!");

        List<Polygon> polygons = new ArrayList<>();
        double stepSize = 2 * bounds / steps;

        for (int i=0; i<steps; ++i) {
            for (int j = 0; j < steps; ++j) {
                double x0 = i * stepSize - bounds;
                double y0 = j * stepSize - bounds;
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

    public double getSize() {
        return SIZE;
    }

    public int getSteps() {
        return STEPS;
    }

}
