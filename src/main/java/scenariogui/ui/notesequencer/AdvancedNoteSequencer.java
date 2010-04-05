/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scenariogui.ui.notesequencer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.geom.Rectangle2D;
import scenariogui.ui.GUIComponent;

/**
 *
 * @author Jon
 */
public class AdvancedNoteSequencer extends GUIComponent{

    private double x;
    private double y;
    private double width;
    private double height;

    private int noteSize = 8;

    private FXShape baseShape = new FXShape();
    NoteTrack track;

        public AdvancedNoteSequencer(double tx, double ty) {
            super(tx,ty,400,200);
            x = tx;
            y = ty;

            track = new NoteTrack(this, x + 10, y + 10, 0);
            this.add(track);


        }

    public int getNoteSize() {
        return noteSize;
    }

}
