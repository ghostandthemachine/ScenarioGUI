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

    public FocusStep parentFocusStep;
    Point2D p;
    private boolean create = false;
    private boolean over = false;

    public FocusStepMouseListener(FocusStep c) {
        parentFocusStep = c;
    }

    @Override
    public void mouseClicked(MouseEvent me, SGNode sgnode) {
        clicked(me, sgnode);

        FXShape node = (FXShape) sgnode;
        p = me.getPoint();
//        if (parentFocusStep.isAlive()) {
//            node.setDrawPaint(Color.orange);
//            node.setFillPaint(parentFocusStep.stepOnFillColor);
//            parentFocusStep.currentFillColor = parentFocusStep.stepOnFillColor;
//        } else if (!parentFocusStep.isAlive()) {
//            node.setFillPaint(parentFocusStep.stepOffFillColor);
//            parentFocusStep.currentFillColor = parentFocusStep.stepOffFillColor;
//        }
    }

    @Override
    public void mousePressed(MouseEvent me, SGNode sgnode) {
        if (parentFocusStep.isAlive()) {
            parentFocusStep.isAlive = false;
            parentFocusStep.getParent().setAddSteps(false);
        } else {
            parentFocusStep.isAlive = true;
            parentFocusStep.getParent().setAddSteps(true);
        }

        if (parentFocusStep.getParent().getAddSteps()) {
            parentFocusStep.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parentFocusStep.y, 0, parentFocusStep.h));
        }

        pressed(me, sgnode);
    }

    @Override
    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        parentFocusStep.getParent().setAddSteps(false);
    }

    @Override
    public void mouseEntered(MouseEvent me, SGNode sgnode) {
        over = true;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.lightGray);

        if (parentFocusStep.getParent().getAddSteps()) {
            parentFocusStep.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parentFocusStep.y, 0, parentFocusStep.h));
        }

    }

    @Override
    public void mouseExited(MouseEvent me, SGNode sgnode) {
        over = false;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.gray);

    }

    @Override
    public void mouseDragged(MouseEvent me, SGNode sgnode) {
        if (parentFocusStep.getParent().getAddSteps() && over) {
            parentFocusStep.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parentFocusStep.y, 0, parentFocusStep.h));
        }

        dragged(me, sgnode);
    }

    @Override
    public void mouseMoved(MouseEvent me, SGNode sgnode) {
        //if dragging activate a velocity
        if (parentFocusStep.getParent().getDragging() && create) {
            parentFocusStep.setVelocityStepLevel(Tools.constrain(me.getPoint().y - parentFocusStep.y, 0, parentFocusStep.h));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
    }

    public void clicked(MouseEvent me, SGNode sgnode) {
    }

    public void dragged(MouseEvent me, SGNode sgnode) {
    }

    public void pressed(MouseEvent me, SGNode sgnode) {
    }
}
