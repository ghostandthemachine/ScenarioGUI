/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

/**
 *
 * @author Jon
 */
public class StepHighLightListener implements SGMouseListener {

    public Step parent;
    Point2D p;
    private boolean create = false;
    private boolean over = false;

    public StepHighLightListener(Step c) {
        parent = c;
    }

    @Override
    public void mouseClicked(MouseEvent me, SGNode sgnode) {
    }

    @Override
    public void mousePressed(MouseEvent me, SGNode sgnode) {
        parent.getParent().setRemoveSteps(true);
        parent.getParent().setAddSteps(false);
        parent.setStepOff();
    }

    @Override
    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        parent.getParent().setRemoveSteps(false);
    }

    @Override
    public void mouseEntered(MouseEvent me, SGNode sgnode) {
        if(parent.getParent().getRemoveSteps()){
            parent.setStepOff();
        }

    }

    @Override
    public void mouseExited(MouseEvent me, SGNode sgnode) {


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
