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
public class VelocityStepMouseListener implements SGMouseListener {

    public Step parent;
    Point2D p;


    public VelocityStepMouseListener(Step c) {
        parent = c;
    }

    public void mouseClicked(MouseEvent me, SGNode sgnode) {
    }

    public void mousePressed(MouseEvent me, SGNode sgnode) {
    }

    public void mouseReleased(MouseEvent me, SGNode sgnode) {
    }

    public void mouseEntered(MouseEvent me, SGNode sgnode) {
    }

    public void mouseExited(MouseEvent me, SGNode sgnode) {

    }

    public void mouseDragged(MouseEvent me, SGNode sgnode) {
    }

    public void mouseMoved(MouseEvent me, SGNode sgnode) {
    }

    public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
    }


}
