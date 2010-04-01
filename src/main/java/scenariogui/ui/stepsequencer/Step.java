package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGGroup;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;
import scenariogui.Tools;

public class Step {

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
    Color vStepOffFillColor = new Color(150, 150, 150);
    Color vStepOnFillColor = new Color(200, 240, 255);
    Color vStepCurrentFillColor = stepOffFillColor;
    private final int trackID;
    private final int stepID;
    private boolean isOn = false;

    public Step(StepSequencer s, double tx, double ty, float tw, float th, float tr, int ti, int si) {
        x = (float) tx;
        y = (float) ty;
        w = tw;
        h = th;
        r = tr;
        trackID = ti;
        stepID = si;
        parent = s;



        stepGroup.add(step);
        stepGroup.add(velocityStep);

        velocityStep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        velocityStep.setFillPaint(stepOnFillColor);
        velocityStep.setMode(SGShape.Mode.FILL);
        velocityStep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
         velocityStep.addMouseListener(new StepHighLightListener(this));


        step.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        step.setFillPaint(stepOffFillColor);
        step.setMode(SGShape.Mode.STROKE_FILL);
        step.setDrawStroke(new BasicStroke(1.0f));
        step.setDrawPaint(Color.GRAY);
        step.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        step.addMouseListener(new StepMouseListener(this));

        //set the parent default step size for use with other gui elements
        parent.setStepShape(new RoundRectangle2D.Double(0, 0, w, h, r, r));

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
        return isAlive;
    }

    public void setVelocityStepLevel(float ty) {

        float tx = x;
        float tw = w;
        float th = h - ty;

        velocityStep.setShape(new RoundRectangle2D.Float(tx, y + ty, tw, th, r, r));
        velocityStep.setFillPaint(stepOnFillColor);
        velocityStep.setOpacity(Tools.map(velocity, 0, 127, 0.3f, 1.0f));

        velocity = Tools.map(ty, 0f, h, 127.0f, 0.0f);
        updateParentVelocityArray(velocity);
    }

    public void updateVelocityStepLevel(float ty) {

        float tx = x;
        float tw = w;
        float th = h - ty;

        velocityStep.setShape(new RoundRectangle2D.Float(tx, y + ty, tw, th, r, r));
        velocityStep.setFillPaint(stepOnFillColor);
        velocityStep.setOpacity(Tools.map(velocity, 0, 127, 0.3f, 1.0f));

        velocity = Tools.map(ty, 0f, h, 127.0f, 0.0f);
    }

    public void setVelocityToZero() {
        velocity = 0;
        setVelocityStepLevel(h);
    }

    public void setVelocity(float f) {
        velocity = f;
        velocity = Tools.constrain(velocity, 0, 127);
        setVelocityStepLevel(Tools.map(f, 0, 127, h, 0));
    }

    //update differs from set in that it doesn't update the parent array to avoid overflow
    public void updateVelocity(float f) {
        velocity = f;
        velocity = Tools.constrain(velocity, 0, 127);
        updateVelocityStepLevel(Tools.map(f, 0, 127, h, 0));
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

    private void updateParentVelocityArray(float velocity) {
        parent.updateVelocityArray(trackID, stepID, velocity);
    }

    void setStepOn() {
        isAlive = true;
        velocity = parent.getCurrentDefaultVelocity();


        velocityStep.setOpacity((float) Tools.map(velocity, 0, 127, 0.0, 1.0));
        velocityStep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        velocityStep.setFillPaint(stepOnFillColor);

        updateParentVelocityArray(velocity);
    }

    void setStepOff() {
        isAlive = false;
        velocity = 0;

        velocityStep.setFillPaint(new Color(0,0,0,0));

        updateParentVelocityArray(velocity);
    }

}
