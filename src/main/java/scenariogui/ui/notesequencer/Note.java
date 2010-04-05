/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.notesequencer;

import com.sun.scenario.animation.Clip;
import com.sun.scenario.scenegraph.SGAbstractShape.Mode;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Jon
 */
public class Note extends SGGroup {

    FXShape note = new FXShape();
    NoteTrack parent;
    double x;
    double y;
    double width;
    double height;
    double radius = 4;
    Color noteFillColor = Color.CYAN;
    Color noteHighLightFillColor = Color.orange;
    GradientPaint gcFill;
    boolean isSelected = false;
    private double noteSize;
    private double lastMX;
    private SGTransform.Translate translation = SGTransform.createTranslation(0, 0, this);
    private double mx;
    private double pressX;
    private boolean translating = false;
    private boolean scalingLeft = false;
    private boolean scalingRight = false;
    private float opacity = 1.0f;
    Clip fader = Clip.create(300, note, "opacity", 0.5f, 1f);


    public Note(NoteTrack p, double tx, double ty, double tw, double th, double stepSize) {
        parent = p;

        noteSize = stepSize;

        x = tx;
        y = ty;
        width = tw;
        height = th;
        lastMX = x;
        mx = x;
        pressX = mx;
        this.add(note);

        gcFill = new GradientPaint(new Point(0, (int)y), Color.white, new Point(0, (int)(y + height)), Color.lightGray);

        note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        note.setMode(Mode.STROKE_FILL);
        note.setFillPaint(noteFillColor);
        note.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        note.addMouseListener(new NoteListener(this));
        note.setOpacity(1.0f);

        parent.add(this);
        fader.start();
    }

    private void setNewWidth(double nw) {
    }

    void setBoundries(double tx) {


        if (scalingRight) {    //if on the right side of the note
            width = tx - this.x;
            width = convertToStepSize(width);
            note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        } else if (scalingLeft) {          //if on the left end of the note

            x -= lastMX - tx;
            width += lastMX - tx;

            note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));

        } else if (translating) {                            // if in the middle
            x -= lastMX - tx;
            note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        }
        lastMX = tx;

    }

    private double convertToStepSize(double v) {
        double newVal = ((int) (v / noteSize) * noteSize);
        return newVal;
    }

    public void setPressX(double tx) {
        pressX = tx;
        lastMX = pressX;
        if (pressX > this.width - this.width / 16 + this.x) {
            scalingRight = true;
        } else if (tx < this.width / 16 + this.x) {          //if on the left end of the note
            scalingLeft = true;
        } else {                            // if in the middle
            translating = true;
        }
    }

    void setInitialBoundries(int tx) {
        width = tx - this.x;
        width = convertToStepSize(width);
        note.setShape(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
    }

    void select() {
        note.setFillPaint(gcFill);
        isSelected = true;
        parent.setNoteSelected(this);
    }

    void unselect() {
        note.setFillPaint(noteFillColor);
        isSelected = false;
    }

    boolean isSlected() {
        return isSelected;
    }

    void release() {
        translating = false;
        scalingLeft = false;
        scalingRight = false;
    }

    public NoteTrack getParentTrack() {
        return parent;
    }

    private class NoteListener implements SGMouseListener {

        Note note;

        public NoteListener(Note n) {
            note = n;
        }

        @Override
        public void mouseClicked(MouseEvent me, SGNode sgnode) {
            if (isSelected) {
                unselect();
            } else {
                select();
            }
        }

        @Override
        public void mousePressed(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseReleased(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseEntered(MouseEvent me, SGNode sgnode) {
            if (!parent.isOverNote()) {
                parent.setCurrentNoteOver(note);
            }
        }

        @Override
        public void mouseExited(MouseEvent me, SGNode sgnode) {
            parent.setOverNote(false);
        }

        @Override
        public void mouseDragged(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseMoved(MouseEvent me, SGNode sgnode) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
        }
    }
}
