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

/**
 *
 * @author Jon
 */
public class StepMouseListener implements SGMouseListener {

    private Step parentStep;
    private StepSequencer parentSeq;
    Point2D p;
    private boolean create = false;
    private boolean over = false;

    public StepMouseListener(Step c) {
        parentStep = c;
        parentSeq = parentStep.getParentSequencer();
    }

    @Override
    public void mouseClicked(MouseEvent me, SGNode sgnode) {
    }

    @Override
    public void mousePressed(MouseEvent me, SGNode sgnode) {
        if (parentStep.isAlive()) {
            parentSeq.setRemoveSteps(true);
            parentSeq.setAddSteps(false);
            parentStep.setStepOff();

        } else if (!parentStep.isAlive()) {
            System.out.println("turn on this step");

            parentSeq.setRemoveSteps(false);
            parentSeq.setAddSteps(true);
            parentStep.setStepOn();

        }

    }

    @Override
    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        parentSeq.setAddSteps(false);
        parentSeq.setRemoveSteps(false);
    }

    @Override
    public void mouseEntered(MouseEvent me, SGNode sgnode) {
        over = true;
        FXShape node = (FXShape) sgnode;
        node.setDrawPaint(Color.lightGray);

        if (parentSeq.getAddSteps()) {
            parentStep.setStepOn();
        } else if (parentSeq.getRemoveSteps()) {
            parentStep.setStepOff();
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
