/*
 *
 * This GUI base Component is intended to shorten the amount of work needed
 * to be done in scenario to get up and running with a new widget. It handles
 * things like mouse data (position, velocity, and state booleans).
 *
 * The intention is to be able to just extend it, set a custom shape and color,
 * if called for, then just add some new components on top (or more GUIComponents)
 */
package scenariogui.ui;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXGroup;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author jon rose
 */
public class GUIComponent extends FXGroup {

    GUIComponent parent = null;
    private double x;
    private double y;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;
    private Color baseColor = new Color(210, 210, 210);
    private FXShape baseShape = new FXShape();
    private double mouseX = 0;
    private double mouseY = 0;
    private double lastX = 0;
    private double lastY = 0;
    private double xVelocity = 0;
    private double yVelocity = 0;
    private boolean mousePressed = false;
    private boolean dragging = false;
    private SGTransform.Translate translation = SGTransform.createTranslation(0, 0, this);

    /**
     *
     * @param x
     * @pa
     * ram y
     * @param width
     * @param height
     */
    public GUIComponent(double tx, double ty, double w, double h) {
        x = tx;
        y = ty;
        xOffset = x;
        yOffset = y;
        width = w;
        height = h;

        add(buildBaseShape(new RoundRectangle2D.Double(x, y, width, height, 16, 16)));

    }

    /**
     *
     * @param shape
     * @return Scenario Shape
     */
    public FXShape buildBaseShape(Shape s) {
        baseShape.setShape(s);
        baseShape.setFillPaint(baseColor);
        baseShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        baseShape.setMode(SGShape.Mode.FILL);
        baseShape.addMouseListener(baseMouseListener());
        return baseShape;
    }

    /**
     *
     * @return
     */
    public SGMouseListener baseMouseListener() {
        SGMouseListener ml = new SGMouseListener() {

            @Override
            public void mouseClicked(MouseEvent me, SGNode sgnode) {
                clicked();
            }

            @Override
            public void mousePressed(MouseEvent me, SGNode sgnode) {
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent me, SGNode sgnode) {
                released();
                mousePressed = false;
                dragging = false;
            }

            @Override
            public void mouseEntered(MouseEvent me, SGNode sgnode) {
            }

            @Override
            public void mouseExited(MouseEvent me, SGNode sgnode) {
            }

            @Override
            public void mouseDragged(MouseEvent me, SGNode sgnode) {
                dragging = true;

                lastX = mouseX;
                lastY = mouseY;

                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();

                xVelocity = mouseX - lastX;
                yVelocity = mouseY - lastY;

                dragged();
            }

            @Override
            public void mouseMoved(MouseEvent me, SGNode sgnode) {
                lastX = mouseX;
                lastY = mouseY;
                xVelocity = lastX - mouseX;
                yVelocity = lastY - mouseY;
                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
            }
        };
        return ml;
    }

    /**
     * Add a component to the main component group
     *
     * @param SGNode
     */
    public void addComponent(SGNode node) {
        add(node);
    }

    /**
     * Remove an SGNode from the component group.
     *
     * @param node
     */
    public void removeComponent(SGNode node) {
        remove(node);
    }

    /**
     * Get the main component group.
     *
     * @return
     */
    public FXGroup getComponentGroup() {

        return this;
    }

    /**
     * Get the baseShape fill Color
     *
     * @return
     */
    public Color getBaseColor() {
        return baseColor;
    }

    /**
     * Set the current base Color
     *
     * @param baseColor
     */
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        this.baseShape.setFillPaint(baseColor);
    }

    /**
     * Set the base shape opacity
     *
     * @param f
     */
    public void setBaseShapeOpacity(float f) {
        baseShape.setOpacity(f);
    }

    /**
     * Get the current base FXShape
     *
     * @return
     */
    public FXShape getBaseShape() {
        return baseShape;
    }

    /**
     *
     * @param bShape
     */
    public void setBaseShape(FXShape bShape) {
        Point2D p = getGlobalCoordinate();
        remove(baseShape);
        baseShape = bShape;
        baseShape.setTranslation(p);
        this.addComponent(baseShape);

        width = baseShape.getBounds().getWidth();
        height = baseShape.getBounds().getHeight();

    }

    /**
     *
     * @return
     */
    public Point2D getGlobalCoordinate() {
        Point2D.Double p = new Point2D.Double(x, y);
        return baseShape.localToGlobal(p, p);
    }

    /**
     *
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
        baseShape.setShape(new RoundRectangle2D.Double(x, y, width, height, 16, 16));
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {








        return y;
    }

    /**
     *
     * @param x
     */
    public void setTranslateX(double x) {
        translation.setTranslateX(
                x);
    }

    /**
     *
     * @param y
     */
    public void setTranslateY(double y) {
        translation.setTranslateY(y);
    }

    /**
     *
     * @param p
     */
    public void setTranslation(Point2D.Double p) {
        translation.setTranslation(p.getX(), p.getY());
    }

    /**
     *
     * @return
     */
    public double getXOffset() {
        return xOffset;
    }

    /**
     *
     * @param xOffset
     */
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    /**
     *
     * @return
     */
    public double getYOffset() {
        return yOffset;
    }

    /**
     *
     * @param yOffset
     */
    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    /**
     *
     * @return
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     *
     * @return
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     *
     * @return
     */
    public double getMouseY() {
        return mouseY;
    }

    /**
     *
     * @return
     */
    public double getLastX() {
        return lastX;
    }

    /**
     *
     * @param lastX
     */
    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    /**
     *
     * @return
     */
    public double getLastY() {
        return lastY;
    }

    /**
     *
     * @param lastY
     */
    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    /**
     *
     * @return
     */
    public double getXVelocity() {
        return xVelocity;
    }

    /**
     *
     * @param xVel
     */
    public void setXVelocity(double xVel) {
        this.xVelocity = xVel;
    }

    /**
     *
     * @return
     */
    public double getYVelocity() {
        return yVelocity;
    }

    /**
     *
     * @param yVel
     */
    public void setyVelocity(double yVel) {
        this.yVelocity = yVel;
    }

    /**
     *
     */
    public void dragged() {
    }

    public void released() {
    }

    public void clicked() {
    }

    /**
     *
     * @return
     */
    public boolean getDragging() {
        return dragging;
    }

    public void setBaseVisible(boolean b) {
        baseShape.setVisible(b);
    }

    public GUIComponent getGUIParent() {
        return parent;
    }
}
