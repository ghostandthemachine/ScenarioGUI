package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGGroup;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import scenariogui.Tools;

public class FocusStep {

    float x;
    float y;
    float w;
    float h;
    float r;
    float defaultVelocity = 100;
    float velocity = 0;
    int counter = 0;
    private boolean alive = false;
    boolean isAlive;
    StepSequencer parent;
    SGGroup stepGroup = new SGGroup();
    FXShape step = new FXShape();
    FXShape velocityStep = new FXShape();
    //Colors for base shape
    Color stepOffFillColor = new Color(150, 150, 150);
    Color stepOnFillColor = new Color(200, 240, 255);
    Color stepCountColor = Color.ORANGE;
    Color currentFillColor = stepOffFillColor;
    //Colors for velocity rect
    Color vStepOffFillColor = Color.ORANGE;
    Color vStepOnFillColor = Color.red;
    Color vStepCurrentFillColor = stepOffFillColor;
    //gradient colors
    Color c1 = Color.yellow;
    Color c2 = new Color(0,255,0,150);
    GradientPaint gc;
    private int stepID;
    private int currentTrack;

    public FocusStep(StepSequencer s, double tx, double ty, double tw, double th, float tr, int i) {
        x = (float) tx;
        y = (float) ty;
        w = (float) tw;
        h = (float) th;
        r = tr;
        parent = s;
        stepID = i;

        gc = new GradientPaint(x + w / 2, y + h/4, c1, x + w / 2, y + h - h/8, c2);

        stepGroup.add(step);
        stepGroup.add(velocityStep);

        velocityStep.setShape(new Rectangle2D.Float(x, y, w, h));
        velocityStep.setFillPaint(gc);
        velocityStep.setMode(SGShape.Mode.FILL);
        velocityStep.setOpacity(0.8f);
        velocityStep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        step.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        step.setFillPaint(stepOffFillColor);
        step.setMode(SGShape.Mode.STROKE_FILL);
        step.setDrawStroke(new BasicStroke(1.0f));
        step.setDrawPaint(Color.GRAY);
        step.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        step.addMouseListener(new FocusStepMouseListener(this));

    }

    SGNode getStepShape() {
        return stepGroup;
    }

    public void stepCountOn() {
        Color newFillPaint;
        int[] colorValues = new int[3];
        colorValues[0] = Tools.constrain((currentFillColor.getRed() + stepCountColor.getRed()) / 2, 0, 255);
        colorValues[1] = Tools.constrain((currentFillColor.getGreen() + stepCountColor.getGreen()) / 2, 0, 255);
        colorValues[2] = Tools.constrain((currentFillColor.getBlue() + stepCountColor.getBlue()) / 2, 0, 255);
        newFillPaint = new Color(colorValues[0], colorValues[1], colorValues[2], 255);
        step.setFillPaint(newFillPaint);
    }

    public void stepCountOff() {
        step.setFillPaint(currentFillColor);
    }

    public StepSequencer getParent() {
        return parent;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setVelocityStepLevel(float ty) {
        float tx = x;
        float tw = w;
        float th = h - ty;
        velocityStep.setShape(new Rectangle2D.Float(tx, y + ty, tw, th));
        velocityStep.setFillPaint(gc);
        velocityStep.setOpacity(Tools.map(velocity, 0, 127, 0.4f, 0.8f));

        velocity = Tools.map(ty, 0, h, 127.0f, 0.0f);
        parent.updateVelocityArray(stepID, velocity);
    }

    public void setVelocityToZero() {
        velocity = 0;
        setVelocityStepLevel(h);
    }

    public void setVelocity(float f) {
        velocity = f;
        setVelocityStepLevel(Tools.map(f, 0, 127, h, 0));
    }

    public SGGroup getGroup() {
        return stepGroup;
    }

    public float getVelocity() {
        return velocity;
    }

    public float getVelocityToUpdate() {
        float newVelocity = Tools.map(velocity, 127.0f, 0.0f, 0f, h);
        return newVelocity;
    }

    public void setCurrentTrack(int track) {
        currentTrack = track;
    }
}
