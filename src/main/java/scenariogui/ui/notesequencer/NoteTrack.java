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
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Jon
 */
public class NoteTrack extends SGGroup {

    FXShape baseShape = new FXShape();
    SGGroup group = new SGGroup();
    private double x = 0;
    private double y = 0;
    private double width = 200;
    private double height = 8;
    private final AdvancedNoteSequencer parent;
    private final int trackID;
    private int noteSize;
    ArrayList<Note> notes = new ArrayList<Note>();
    Color baseColor = Color.blue;
    private Note currentNote;
    private Note creatingNote;
    private boolean overNote = false;
    private boolean isEditing = false;
    private boolean noteSelected = false;
    private Note selectedNote;

    public NoteTrack(AdvancedNoteSequencer ns, double tx, double ty, int id) {
        x = tx;
        y = ty;

        parent = ns;

        trackID = id;

        noteSize = parent.getNoteSize();

        createBaseShape();
    }

    private void createBaseShape() {

        baseShape.setShape(new Rectangle2D.Double(x, y, width, height));
        baseShape.setFillPaint(baseColor);
        baseShape.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        baseShape.setMode(SGShape.Mode.FILL);
        baseShape.addMouseListener(new NoteTrackMouseListener(this));
        add(baseShape);
    }

    public void setCurrentNoteOver(Note note) {
        currentNote = note;
        overNote = true;
    }

    public boolean isOverNote() {
        return overNote;
    }

    public void setOverNote(boolean overNote) {
        this.overNote = overNote;
    }

    private void addNote(double tx) {
        Note newNote = new Note(this, tx, y, 2, 8, 8);
        notes.add(newNote);
        creatingNote = newNote;
        currentNote = newNote;
    }

    public Note getCreatingNote() {
        return creatingNote;
    }

    public void setCreatingNote(Note n) {
        creatingNote = n;
    }

    private Note getCurrentNote() {
        return currentNote;
    }

    private boolean isEditing() {
        return isEditing;
    }

    private void setEditing(boolean b) {
        isEditing = b;
    }

    void setNoteSelected(Note note) {
        noteSelected = true;
        selectedNote = note;
    }

    private void unselectAll() {
        if(selectedNote != null){
            selectedNote.unselect();
        }
        selectedNote = null;
    }

    private class NoteTrackMouseListener implements SGMouseListener {

        NoteTrack track;
        Note newNote;
        private boolean creating = false;

        private NoteTrackMouseListener(NoteTrack parentTrack) {
            track = parentTrack;
        }

        @Override
        public void mouseClicked(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mousePressed(MouseEvent me, SGNode sgnode) {
            if (!track.isOverNote()) {
                track.addNote(me.getX());
                this.creating = true;
                track.unselectAll();

            } else if (track.notes.size() > 0) {

                track.getCurrentNote().setPressX(me.getX());
                
                track.setEditing(true);
            }
        }

        @Override
        public void mouseReleased(MouseEvent me, SGNode sgnode) {
            this.creating = false;
            track.setEditing(false);
            track.getCurrentNote().release();
        }

        @Override
        public void mouseEntered(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseExited(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseDragged(MouseEvent me, SGNode sgnode) {
            if (creating) {
                track.getCreatingNote().setInitialBoundries(me.getX());
            }
            if (track.isEditing()) {
                track.getCurrentNote().setBoundries(me.getX());
            }
        }

        @Override
        public void mouseMoved(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
        }
    }
}
