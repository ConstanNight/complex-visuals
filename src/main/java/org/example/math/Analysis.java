package org.example.math;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.plot3d.rendering.view.modes.ViewBoundMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelListener;

public class Analysis {
    private Chart chart;
    private MathSurface currentSurface;

    public Analysis(ShapeType shape, double size, int rez) {

        currentSurface = switch (shape) {
            case SPHERE -> new RiemannSphere(size, rez);
            default -> null; // This should never happen!
        };

        chart = new AWTChartFactory().newChart();
        chart.setAxeDisplayed(false);
        chart.getView().setBoundMode(ViewBoundMode.MANUAL);
        chart.getScene().getGraph().add(currentSurface.getShape());

        Color themeBg = UIManager.getColor("Panel.background");

        org.jzy3d.colors.Color jzyBgColor = new org.jzy3d.colors.Color(
                themeBg.getRed(),
                themeBg.getGreen(),
                themeBg.getBlue()
        );

        chart.getView().setBackgroundColor(jzyBgColor);

        addMouse();
    }

    public void setWireframeDisplayed(boolean status) {
        currentSurface.setWireframe(status);
        chart.render(); // Force the 3D chart to redraw when clicked
    }

    public void update(double size, int rez) {
        chart.getScene().getGraph().remove(currentSurface.getShape());  // Remove the current shape
        currentSurface.update(size, rez);                  // Create a new shape
        chart.getScene().getGraph().add(currentSurface.getShape());     // Add the new shape
        chart.render();
    }

    public void updateResolution(int subDivisions) {
        update(currentSurface.getSize(), subDivisions);
    }

    public void updateSize(int size) {
        update(size, currentSurface.getResolution());
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
    public MathSurface getSurface() {
        return currentSurface;
    }
}
