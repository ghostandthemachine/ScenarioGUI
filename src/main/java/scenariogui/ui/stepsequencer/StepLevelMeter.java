/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import scenariogui.Tools;

/**
 *
 * @author Jon Rose
 */
public class StepLevelMeter {

    private double x;
    private double y;
    private double width;
    private double height;
    private FXShape base;
    private FXShape levelMeter = new FXShape();
    private SGGroup group = new SGGroup();

    public StepLevelMeter(double tx, double ty, double tw, double th) {
        x = tx;
        y = ty;
        width = tw;
        height = th;

        group.add(createSlider());
    }

    private SGGroup createSlider() {
        SGGroup g = new SGGroup();

        base.setShape(new Rectangle2D.Double(x, y, width, height));
        base.setMode(SGShape.Mode.STROKE);
        base.setDrawStroke(new BasicStroke(1.0f));
        base.setDrawPaint(Color.white);
        base.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        levelMeter.setShape(new Rectangle2D.Double(x + 1, y + 1, width - 2, height - 2));
        levelMeter.setMode(SGShape.Mode.FILL);
        levelMeter.setDrawPaint(Color.green);
        levelMeter.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        g.add(base);
        g.add(levelMeter);

        return g;
    }

    public void setLevel(float level) {
        double newY = Tools.map(level, 0, 127, height, 0);
        levelMeter.setShape(new Rectangle2D.Double(x + 1, newY + 1, width - 2, height - 2));
    }
}
