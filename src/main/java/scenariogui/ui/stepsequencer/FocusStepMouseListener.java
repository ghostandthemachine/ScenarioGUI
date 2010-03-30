/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import scenariogui.Tools;

/**
 *
 * @author Jon
 */
public class FocusStepMouseListener implements SGMouseListener {

    public FocusStep parent;
    Point2D p;
    private boolean create = false;
    private boolean over = false;

    public FocusStepMouseListener(FocusStep c) {
        parent = c;
    }

    public void mouseClicked(MouseEvent me, SGNode sgnode) {
        clicked(me, sgnode);

        FXShape node = (FXShape) sgnode;
        p = me.getPoint();
        if (parent.isAlive()) {
            node.setDrawPaint(Color.orange);
            node.setFillPaint(parent.stepOnFillColor);
            parent.currentFillColor = parent.stepOnFillColor;
        } else if (!parent.isAlive()) {
            node.setFillPaint(parent.stepOffFillColor);
            parent.currentFillColor = parent.stepOffFillColor;
        }
    }

    public void mousePressed(MouseEvent me, SGNode sgnode) {
        if (parent.isAlive()) {
            parent.isAlive = false;
            parent.getParent().setAddSteps(false);
        } else {
            parent.isAlive = true;
            parent.getParent().setAddSteps(true);
        }

        if (parent.getParent().getAddSteps()) {
            parent.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parent.y, 0, parent.h));
        }

        pressed(me, sgnode);
    }

    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        parent.getParent().setAddSteps(false);
    }

    public void mouseEntered(MouseEvent me, SGNode sgnode) {
        over = true;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.lightGray);

        if (parent.getParent().getAddSteps()) {
            parent.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parent.y, 0, parent.h));
        }

    }

    public void mouseExited(MouseEvent me, SGNode sgnode) {
        over = false;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.gray);

    }

    public void mouseDragged(MouseEvent me, SGNode sgnode) {
        if (parent.getParent().getAddSteps() && over) {
            parent.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parent.y, 0, parent.h));
        }

        dragged(me, sgnode);
    }

    public void mouseMoved(MouseEvent me, SGNode sgnode) {
        //if dragging activate a velocity
        if (parent.getParent().getDragging() && create) {
            parent.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parent.y, 0, parent.h));
        }
    }

    public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
    }

    public void clicked(MouseEvent me, SGNode sgnode) {
    }

    public void dragged(MouseEvent me, SGNode sgnode) {
    }

    public void pressed(MouseEvent me, SGNode sgnode) {
    }
}
