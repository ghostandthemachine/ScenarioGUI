/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenegraphtest;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGAbstractShape.Mode;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jon
 */
public class MasterPanel extends JSGPanel {

    SGGroup root = new SGGroup();
    public static double mouseX;
    public static double mouseY;
    public static double lastX;
    public static double lastY;
    public static double xVel;
    public static double yVel;
    public static boolean mousePressed = false;
    public static boolean mouseDragged = false;
    private SGShape listenerShape = new SGShape();

    public MasterPanel() {
        BasicDial dial = new BasicDial(300, 300, 32);
        ClassicDial cDial = new ClassicDial(200, 400, 50);
        StepSequencer ss = new StepSequencer(50, 50, 350, 200, 16, 16);
        createSceneMouseListener();

        root.add(listenerShape);
        root.add(ss.getComponentGroup());
        root.add(cDial.getComponentGroup());
        root.add(dial.getComponentGroup());

        setScene(root);
        setBackground(Color.white);
    }

    public void createSceneMouseListener() {

        listenerShape.setShape(new Rectangle2D.Double(0, 0, 2000, 2000));
        listenerShape.setFillPaint(new Color(0,0,0,0));
        listenerShape.addMouseListener(new SGMouseListener() {

            public void mouseClicked(MouseEvent me, SGNode sgnode) {
            }

            public void mousePressed(MouseEvent me, SGNode sgnode) {
                mousePressed = true;
            }

            public void mouseReleased(MouseEvent me, SGNode sgnode) {
                mousePressed = false;
                mouseDragged = false;
            }

            public void mouseEntered(MouseEvent me, SGNode sgnode) {
            }

            public void mouseExited(MouseEvent me, SGNode sgnode) {
            }

            public void mouseDragged(MouseEvent me, SGNode sgnode) {
                mouseDragged = true;
                System.out.println(MasterPanel.isMouseDragged());
            }

            public void mouseMoved(MouseEvent me, SGNode sgnode) {
                lastX = mouseX;
                lastY = mouseY;

                xVel = lastX - mouseX;
                yVel = lastY - mouseY;

                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();
            }

            public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
            }
        });
    }

    public static double getLastX() {
        return lastX;
    }

    public static double getLastY() {
        return lastY;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public SGGroup getRoot() {
        return root;
    }

    public static double getxVel() {
        return xVel;
    }

    public static double getyVel() {
        return yVel;
    }

    public static boolean isMousePressed() {
        return mousePressed;
    }

    public static boolean isMouseDragged() {
        return mouseDragged;
    }
}
