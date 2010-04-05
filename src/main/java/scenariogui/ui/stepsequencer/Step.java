package scenariogui.ui.stepsequencer;

import com.sun.scenario.animation.Clip;
import com.sun.scenario.scenegraph.SGGroup;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;
import scenariogui.Tools;

public class Step extends SGGroup {

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
    FXShape step = new FXShape();
    public FXShape velocityStep = new FXShape();
    FXShape hitStep = new FXShape();
    //Colors for base shape
    Color stepFillColor = new Color(150, 150, 150);
    Color stepCountColor = Color.ORANGE;
    Color currentFillColor = stepFillColor;
    //Colors for velocity rect
    Color vStepColor = new Color(200, 255, 200);
    Color vStepCurrentFillColor = stepFillColor;
    //Colors for velocity rect
    Color hitColor = Color.white;
    private final int trackID;
    private final int stepID;
    private boolean isOn = false;
    int delayTime;
    Clip hardFadeOut;
    Clip softFadeOut;

    public Step(StepSequencer s, double tx, double ty, float tw, float th, float tr, int ti, int si) {
        x = (float) tx;
        y = (float) ty;
        w = tw;
        h = th;
        r = tr;
        trackID = ti;
        stepID = si;
        parent = s;

        delayTime = parent.getDelay();

        //Set the animation fade out length to the same duration as a time between steps
        hardFadeOut = Clip.create(delayTime, hitStep, "opacity", 1f, 0f);
        softFadeOut = Clip.create(delayTime, hitStep, "opacity", 0.3f, 0f);

        add(step);
        add(velocityStep);
        add(hitStep);


        hitStep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        hitStep.setFillPaint(hitColor);
        hitStep.setMode(SGShape.Mode.FILL);
        hitStep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        hitStep.setOpacity(0);

        velocityStep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        velocityStep.setFillPaint(vStepColor);
        velocityStep.setMode(SGShape.Mode.FILL);
        velocityStep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);


        step.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        step.setFillPaint(stepFillColor);
        step.setMode(SGShape.Mode.STROKE_FILL);
        step.setDrawStroke(new BasicStroke(1.0f));
        step.setDrawPaint(Color.GRAY);
        step.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        step.addMouseListener(new StepMouseListener(this));

        //set the parent default step size for use with other gui elements
        parent.setStepShape(new RoundRectangle2D.Double(0, 0, w, h, r, r));

    }

    SGGroup getStepShape() {
        return this;
    }

    public void hitCount() {
        if (isAlive) {
            hardFadeOut.start();
        } else {
            softFadeOut.start();
        }
    }

    public void stepCountOff() {
    }

    public StepSequencer getParentSequencer() {
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
        velocityStep.setFillPaint(vStepColor);
//        velocityStep.setOpacity(Tools.map(velocity, 0, 127, 0.3f, 1.0f));

        velocity = Tools.map(ty, 0f, h, 127.0f, 0.0f);
        updateParentVelocityArray(velocity);
    }

    public void updateVelocityStepLevel() {
        velocityStep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        velocityStep.setFillPaint(vStepColor);
        velocityStep.setOpacity(Tools.constrain(Tools.map(velocity, 0, 127, 0.0f, 1.0f), 0.0f, 1.0f));   //sett opacity based on velocity
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

        updateVelocityStepLevel();

    }

    public SGGroup getGroup() {
        return this;
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
        velocityStep.setFillPaint(vStepColor);

        updateParentVelocityArray(velocity);
    }

    void setStepOff() {
        isAlive = false;
        velocity = 0;

        velocityStep.setOpacity(0);

        updateParentVelocityArray(velocity);
    }

    public void setAlive(boolean b) {
        isAlive = true;
    }

    public void setDelay(int d) {
        delayTime = d;
        //Set the animation fade out length to the same duration as a time between steps
        hardFadeOut = Clip.create(delayTime, hitStep, "opacity", 1f, 0f);
        softFadeOut = Clip.create(delayTime, hitStep, "opacity", 0.3f, 0f);
    }
}
