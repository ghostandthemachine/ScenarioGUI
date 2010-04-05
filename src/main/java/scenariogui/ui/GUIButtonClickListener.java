/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import scenariogui.ui.stepsequencer.AdvancedStepSequencer;

/**
 *
 * @author Jon
 */
public class GUIButtonClickListener implements SGMouseListener {

    public GUIButton parent;
    public AdvancedStepSequencer advSeq;
    private int row;
    private int column;
    private int ID;

    public GUIButtonClickListener(GUIButton c, int col, int r) {
        parent = c;
        column = col;
        row = r;
    }

    public GUIButtonClickListener(AdvancedStepSequencer c, int col, int r) {
        advSeq = c;
        column = col;
        row = r;
    }

    public void mouseClicked(MouseEvent me, SGNode sgnode) {
        clicked();
    }

    public void mousePressed(MouseEvent me, SGNode sgnode) {
        pressed();
    }

    public void mouseReleased(MouseEvent me, SGNode sgnode) {
        released();
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

    public void clicked() {
    }

    public void pressed() {
    }

    public void released() {
    }

    public GUIButton getParent() {
        return parent;
    }

    public AdvancedStepSequencer getSequencer() {
        return advSeq;
    }

    public int getID() {
        return ID;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}

