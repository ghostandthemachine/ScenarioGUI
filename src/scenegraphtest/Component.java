/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenegraphtest;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author jon
 */
public class Component {

    private double x;
    private double y;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;
    private Color baseColor = new Color(210,210,210);
    private FXShape baseShape = new FXShape();
    private SGGroup componentRoot = new SGGroup();
    private double mouseX = 0;
    private double mouseY = 0;
    private double lastX = 0;
    private double lastY = 0;
    private double xVelocity = 0;
    private double yVelocity = 0;
    private boolean mousePressed = false;

    public Component(double tx, double ty, double w, double h) {
        x = tx;
        y = ty;
        xOffset = x;
        yOffset = y;
        width = w;
        height = h;

        baseShape.setShape(new RoundRectangle2D.Double(x, y, width, height,16,16));
        baseShape.setFillPaint(baseColor);
        baseShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        baseShape.setMode(SGShape.Mode.STROKE_FILL);
        baseShape.addMouseListener(new SGMouseListener() {

            public void mouseClicked(MouseEvent me, SGNode sgnode) {
            }

            public void mousePressed(MouseEvent me, SGNode sgnode) {
                mousePressed = true;
            }

            public void mouseReleased(MouseEvent me, SGNode sgnode) {
                mousePressed = false;
            }

            public void mouseEntered(MouseEvent me, SGNode sgnode) {
            }

            public void mouseExited(MouseEvent me, SGNode sgnode) {
            }

            public void mouseDragged(MouseEvent me, SGNode sgnode) {
                lastX = mouseX;
                lastY = mouseY;

                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();

                xVelocity = mouseX - lastX;
                yVelocity = mouseY - lastY;

                nodeDragged();
            }

            public void mouseMoved(MouseEvent me, SGNode sgnode) {
                lastX = mouseX;
                lastY = mouseY;

                xVelocity = lastX - mouseX;
                yVelocity = lastY - mouseY;

                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();
            }

            public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
            }
        });

        componentRoot.add(baseShape);

    }

    public void addComponent(SGNode node) {
        componentRoot.add(node);
    }

    public SGGroup getComponentGroup() {
        return componentRoot;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public FXShape getBaseShape() {
        return baseShape;
    }

    public void setBaseShape(FXShape baseShape) {
        this.baseShape = baseShape;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getLastX() {
        return lastX;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    public double getxVel() {
        return xVelocity;
    }

    public void setxVel(double xVel) {
        this.xVelocity = xVel;
    }

    public double getyVel() {
        return yVelocity;
    }

    public void setyVel(double yVel) {
        this.yVelocity = yVel;
    }

    public void nodeDragged() {
    }
}
