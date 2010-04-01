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
public class StepMouseListener implements SGMouseListener {

    public Step parent;
    Point2D p;
    private boolean create = false;
    private boolean over = false;

    public StepMouseListener(Step c) {
        parent = c;
    }

    @Override
    public void mouseClicked(MouseEvent me, SGNode sgnode) {
    }

    @Override
    public void mousePressed(MouseEvent me, SGNode sgnode) {
        if (parent.isAlive()) {
            System.out.println("fwww");
            parent.getParent().setRemoveSteps(true);
            parent.getParent().setAddSteps(false);
            parent.setStepOff();

        } else if(!parent.isAlive()){
            parent.getParent().setRemoveSteps(false);
            parent.getParent().setAddSteps(true);
            parent.setStepOn();

        }

    }

    @Override
    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        parent.getParent().setAddSteps(false);
        parent.getParent().setRemoveSteps(false);
    }

    @Override
    public void mouseEntered(MouseEvent me, SGNode sgnode) {
        over = true;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.lightGray);

        System.out.println(parent.getParent().getRemoveSteps());
        if (parent.getParent().getAddSteps()) {
            parent.setStepOn();
        } else if (parent.getParent().getRemoveSteps()) {
            parent.setStepOff();
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
    }

    @Override
    public void mouseMoved(MouseEvent me, SGNode sgnode) {
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