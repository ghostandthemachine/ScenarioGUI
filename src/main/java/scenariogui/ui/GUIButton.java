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
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Jon
 */
public class GUIButton extends GUIComponent {

    private FXShape buttonShape = new FXShape();
    private Color fillColor = Color.lightGray;
    private Color onColor = Color.PINK;
    private String id;
    private boolean isOn = false;

    public GUIButton(double tx, double ty, double w, double h, String s) {
        super(tx,ty,w,h);
        id = s;
        buttonShape.setShape(new RoundRectangle2D.Double(tx, ty, w, h, 6, 6));
        buttonShape.setFillPaint(fillColor);
        buttonShape.setMode(SGShape.Mode.FILL);
        buttonShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        add(buttonShape);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn() {
        buttonShape.setFillPaint(onColor);
        isOn = true;
    }

    public void setOff() {
        buttonShape.setFillPaint(fillColor);
        isOn = false;
    }

    public void addMouseListener(SGMouseListener a) {
        buttonShape.addMouseListener(a);
    }

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

}


