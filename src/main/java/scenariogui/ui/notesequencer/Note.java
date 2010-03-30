/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.notesequencer;

import com.sun.scenario.scenegraph.SGAbstractShape.Mode;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Jon
 */
public class Note {

    FXShape note = new FXShape();
    NoteTrack parent;
    double x;
    double y;
    double width;
    double height;
    double radius = 4;
    Color noteFillColor = Color.CYAN;

    public Note(NoteTrack p, double tx, double ty, double tw, double th) {
        parent = p;

        x = tx;
        y = ty;
        width = tw;
        height = th;


        note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        note.setMode(Mode.FILL);
        note.setFillPaint(noteFillColor);
        note.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        note.addMouseListener(new NoteListener(this));

      //  parent.addNote(note);
    }

    private class NoteListener implements SGMouseListener {

        Note note;

        public NoteListener(Note n) {
            note = n;
        }

        public void mouseClicked(MouseEvent me, SGNode sgnode) {
        }

        public void mousePressed(MouseEvent me, SGNode sgnode) {
        }

        public void mouseReleased(MouseEvent me, SGNode sgnode) {
        }

        public void mouseEntered(MouseEvent me, SGNode sgnode) {
            parent.setCurrentNoteOver(note);
        }

        public void mouseExited(MouseEvent me, SGNode sgnode) {
            parent.setCurrentNoteOver(null);
        }

        public void mouseDragged(MouseEvent me, SGNode sgnode) {
        }

        public void mouseMoved(MouseEvent me, SGNode sgnode) {
        }

        public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
        }
    }
}
