/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.notesequencer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Jon
 */
public class NoteTrack {

    FXShape baseShape = new FXShape();
    SGGroup group = new SGGroup();
    private double x = 0;
    private double y = 0;
    private double width = 100;
    private double height = 8;
    private final AdvancedNoteSequencer parent;
    private final int trackID;

    private int noteSize;
    ArrayList<FXShape> notes = new ArrayList<FXShape>();
    Color baseColor = Color.lightGray;
    private Note currentNote;

    public NoteTrack(AdvancedNoteSequencer ns, int id) {
        parent = ns;

        trackID = id;

        noteSize = parent.getNoteSize();

        createBaseShape();
    }

    private void createBaseShape() {

        baseShape.setShape(new Rectangle2D.Double(x, y, width, height));
        baseShape.setFillPaint(baseColor);
        baseShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        baseShape.setMode(SGShape.Mode.STROKE_FILL);
        baseShape.addMouseListener(new SGMouseListener() {

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
        });
    }

    public void setCurrentNoteOver(Note note) {
        currentNote = note;
    }

    public void addToGroup(FXShape note) {
        this.group.add(note);
    }

}
