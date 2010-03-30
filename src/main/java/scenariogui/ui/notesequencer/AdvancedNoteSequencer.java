/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scenariogui.ui.notesequencer;

import com.sun.scenario.scenegraph.fx.FXShape;

/**
 *
 * @author Jon
 */
public class AdvancedNoteSequencer {

    private double x;
    private double y;
    private double width;
    private double height;

    private int noteSize = 8;

    private FXShape baseShape = new FXShape();

        public AdvancedNoteSequencer(double tx, double ty) {
            x = tx;
            y = ty;


        }

    public int getNoteSize() {
        return noteSize;
    }

}
