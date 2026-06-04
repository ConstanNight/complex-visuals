package org.example.math;
import org.jzy3d.plot3d.primitives.Shape;

public interface MathSurface {
    void update(double size, int subDivisions);
    void setWireframe(boolean status);

    Shape getShape();
    double getSize();
    int getResolution();
}