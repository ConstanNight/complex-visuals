package org.example;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.plot3d.rendering.view.modes.ViewBoundMode;

import java.awt.*;
import java.awt.event.MouseWheelListener;

public class Analysis {
    private Chart chart;
    private RiemannSphere sphere;
    private double bounds;
    private int steps;
    private boolean showWireframe;

    public Analysis(double bounds, int steps) {
        this.bounds = bounds;
        this.steps = steps;
        sphere = new RiemannSphere(bounds, steps);
        chart = new AWTChartFactory().newChart();

        chart.setAxeDisplayed(false);
        chart.getView().setBoundMode(ViewBoundMode.MANUAL);
        chart.getScene().getGraph().add(sphere);
        addMouse();
    }

    public void setWireframeDisplayed(boolean status) {
        showWireframe = status;
        sphere.setWireframeDisplayed(status);
        chart.render();  // Force the 3D chart to redraw when clicked
    }

    public void updateResolution(int steps) {
        chart.getScene().getGraph().remove(sphere);
        sphere = new RiemannSphere(bounds,steps);
        sphere.setWireframeDisplayed(showWireframe);
        chart.getScene().getGraph().add(sphere);
        chart.render();
    }

    private void addMouse() {
        chart.addMouse();
        Component canvas = (Component) chart.getCanvas();
        for (MouseWheelListener listener : canvas.getMouseWheelListeners())
            canvas.removeMouseWheelListener(listener);
        canvas.addMouseWheelListener(e -> {
            // Calculate zoom direction
            float factor = 1 + (e.getWheelRotation() / 10.0f);

            org.jzy3d.maths.BoundingBox3d b = chart.getView().getBounds();

            // Multiply all dimensions equally to scale the "glass box" uniformly
            // (Since our box is centered at 0, this creates a perfect camera zoom)
            chart.getView().setBoundsManual(new org.jzy3d.maths.BoundingBox3d(
                    b.getXmin() * factor, b.getXmax() * factor,
                    b.getYmin() * factor, b.getYmax() * factor,
                    b.getZmin() * factor, b.getZmax() * factor
            ));
        });
    }

    public Chart getChart() {
        return chart;
    }
    public RiemannSphere getSphere() {
        return sphere;
    }
}
