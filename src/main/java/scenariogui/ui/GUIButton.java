/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import scenariogui.Triangle;

/**
 *
 * @author Jon
 */
public class GUIButton extends GUIComponent {

    private FXShape triangle = new FXShape();
    private FXShape buttonShape = new FXShape();
    private Color fillColor = Color.lightGray;
    private Color onColor = Color.PINK;
    private String id;
    private boolean isOn = false;
    private Color triangleOnColor = Color.white;
    private Color triangleColor = Color.white;

    public GUIButton(double tx, double ty, double w, double h, String s) {
        super(tx, ty, w, h);
        id = s;
        buttonShape.setShape(new RoundRectangle2D.Double(tx, ty, w, h, 6, 6));
        buttonShape.setFillPaint(fillColor);
        buttonShape.setMode(SGShape.Mode.FILL);
        buttonShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        add(buttonShape);


        triangle.setShape(createTriangle());
        triangle.setFillPaint(triangleColor);
        triangle.setMode(SGShape.Mode.FILL);
        triangle.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        add(triangle);

    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn() {
        buttonShape.setFillPaint(onColor);
        isOn = true;

        triangle.setFillPaint(triangleOnColor);
    }

    public void setOff() {
        buttonShape.setFillPaint(fillColor);
        isOn = false;
        triangle.setFillPaint(triangleColor);
    }

    @Override
    public void addMouseListener(SGMouseListener a) {
        buttonShape.addMouseListener(a);
    }

    @Override
    public SGGroup getComponentGroup() {
        return this;
    }

    public FXShape getButtonShape() {
        return buttonShape;
    }

    public void setButtonShape(FXShape buttonShape) {
        this.buttonShape = buttonShape;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getOnColor() {
        return onColor;
    }

    public void setOnColor(Color onColor) {
        this.onColor = onColor;
    }

    private Shape createTriangle() {
        Point p1 = new Point((int) (5 + this.getX()), (int) (5 + this.getY()));
        Point p2 = new Point((int) (this.getWidth() - 5 + this.getX()), (int) (this.getY() + this.getHeight() / 2));
        Point p3 = new Point((int) (5 + this.getX()), (int) (this.getY() + this.getHeight() - 5));

        Triangle triangle = new Triangle(p1, p2, p3);

        return triangle;
    }

    public void setTriangleOnColor(Color color) {
        triangleOnColor = color;
    }

    public void setTriangleColor(Color color) {
        triangleColor = color;
    }
}


