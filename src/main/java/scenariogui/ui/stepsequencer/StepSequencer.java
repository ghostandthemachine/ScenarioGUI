package scenariogui.ui.stepsequencer;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.Timer;
import scenariogui.Tools;
import scenariogui.ui.GUIComponent;

public class StepSequencer extends GUIComponent {

    float[][][] velocities;
    Step[][] stepGroup;
    FocusStep[] focusStepGroup;
    SGGroup steps = new SGGroup();
    SGGroup focusTracks = new SGGroup();
    double[][] trackLocations;
    float sw;
    float sh;
    float sr;
    FXShape base = new FXShape();
    boolean overFeatures = false;
    float r;
    public String ADD_HIT = "addHit";
    public String REMOVE_HIT = "removeHit";
    private String stepState = ADD_HIT;
    private float xStepOffset = 0;
    private float yStepOffset = 0;
    private float plusWidth = 0;
    private float plusHeight = 0;
    private RoundRectangle2D.Double stepRect;
    private int nTracks;
    private int nCounts;
    private int count = 0;
    private int lastCount = 0;
    private boolean addSteps = false;
    Timer timer = new Timer(500, new SequencerTimerListener(this));
    private int currentFocussedTrack;
    private final float seqWidth;
    private final float seqHeight;
    private int delay = 0;
    private float bpm = 0;
    private int lastFocussedTrack;
    private int numPresets = 8;
    private int currentPreset = 0;
    public static int THIRTYSECOND_NOTE = 4;
    public static int SIXTEENTH_NOTE = 2;
    public static int QUARTER_NOTE = 1;
    public static int HALF_NOTE = 2;
    public static int WHOLE_NOTE = 4;
    private float softVelocity = 60;
    private float mediumVelocity = 90;
    private float hardVelocity = 120;
    private float currentDefaultVelocity = mediumVelocity;
    private boolean removeSteps = false;
    private final int nSteps;
    private double focusTrackHeight;
    private boolean focusMode = false;

    public StepSequencer(float tx, float ty, float tw, float th, float tsteps, float ttracks, float xstep, float ystep, float plusw, float plush) {
        super(tx, ty, tw, th);
        this.setBaseColor(new Color(200, 200, 200));
        r = ((tw - 4) / tsteps) / 4;
        setBaseColor(new Color(50, 50, 50));

        nCounts = (int) tsteps;
        nTracks = (int) ttracks;
        nSteps = nCounts * nTracks;

        seqWidth = tw;
        seqHeight = th;

        xStepOffset = xstep;
        yStepOffset = ystep;
        plusWidth = plusw;
        plusHeight = plush;

        currentFocussedTrack = 0;

        velocities = new float[numPresets][nTracks][nCounts];

        base.setShape(new RoundRectangle2D.Float(0, 0, tw + plusWidth, th + plusHeight, r, r));
        base.setFillPaint(this.getBaseColor());
        base.setMode(SGShape.Mode.FILL);
        this.setBaseShape(base);

        trackLocations = new double[nTracks][2];

        stepGroup = new Step[nTracks][nCounts];

        sw = ((tw - 4) / nCounts) + yStepOffset - 3;
        sh = ((th - 4) / nTracks) - 3;
        sr = ((tw - 4) / nCounts) / 4;

        for (int track = 0; track < nTracks; track++) {
            for (int step = 0; step < nCounts; step++) {

                double sx = 3.5 + (step * sw) + tx + xStepOffset + (step * 3);
                double sy = 3.5 + (track * sh) + ty + (track * 3);
                int stepId = step;
                int trackId = track;

                stepGroup[track][step] = new Step(this, sx, sy, sw, sh, sr, trackId, stepId);
                stepGroup[track][step].setVelocityToZero();
                steps.add(stepGroup[track][step]);


            }
        }
        stepRect = new RoundRectangle2D.Double(0, 0, sw - 3 + yStepOffset, sh - 3, sr, sr);
        this.addComponent(steps);

        createFocusTracks();
        createRulerLines();

        setBpm(120);

    }

    public void createFocusTracks() {
        focusStepGroup = new FocusStep[nCounts];
        float tempW = (float) ((this.getWidth()) / nCounts);
        for (int i = 0; i < nCounts; i++) {
            double fsx = 3.5 + (i * sw) + this.getX() + xStepOffset + (i * 3);
            double fsy = 3.5 + this.getY();
            double fsw = sw;
            double fsh = this.getHeight() - 6;
            focusTrackHeight = fsh;

            focusStepGroup[i] = new FocusStep(this, fsx, fsy, fsw, fsh, sr, i);
            focusTracks.add(focusStepGroup[i].getStepShape());
        }
    }

    private void createRulerLines() {
        SGGroup g = new SGGroup();

        for (int i = 0; i <= 10; i++) {
            double x1 = this.getX() + xStepOffset;
            double y1 = this.getY() + yStepOffset + (i * (seqHeight / 10));
            double x2 = this.getX() + xStepOffset + seqWidth;
            double y2 = y1;

            FXShape line = new FXShape();
            line.setShape(new Line2D.Double(x1, y1, x2, y2));
            line.setDrawPaint(Color.lightGray);
            line.setMode(SGShape.Mode.STROKE);
            line.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
            line.setOpacity(0.3f);

            g.add(line);
        }
        focusTracks.add(g);
    }

    public void stepCountOn(int count) {
        for (int i = 0; i < nTracks; i++) {
            stepGroup[i][count].hitCount();
            focusStepGroup[count].stepCountOn();
        }
    }

    public void stepCountOff(int count) {
        for (int i = 0; i < nTracks; i++) {
            stepGroup[i][count].stepCountOff();
            focusStepGroup[count].stepCountOff();
        }
    }

    public void setStepShape(RoundRectangle2D.Double r) {
        stepRect = r;
    }

    public RoundRectangle2D.Double getStepShape() {
        return stepRect;
    }

    public void nodeOver() {
    }

    public void nodeExited() {
    }

    public void setHit(String s) {
        stepState = s;
    }

    public void setNumPresets(int i) {
        numPresets = i;
    }

    public int getNumPresets() {
        return numPresets;
    }

    public void setCurrentPreset(int i) {
        currentPreset = i;
    }

    public int getCurrentPreset() {
        return currentPreset;
    }

    public String getStepState() {
        return stepState;
    }

    public float getxStepOffset() {
        return xStepOffset;
    }

    public void setxStepOffset(float xStepOffset) {
        this.xStepOffset = xStepOffset;
    }

    public float getyStepOffset() {
        return yStepOffset;
    }

    public void setyStepOffset(float yStepOffset) {
        this.yStepOffset = yStepOffset;
    }

    public boolean isFocusMode() {
        return focusMode;
    }

    public void setIsFocusMode(boolean isFocusMode) {
        this.focusMode = isFocusMode;
    }



    public void setCurrentFocussedTrack(int i) {
        currentFocussedTrack = i;
        System.out.println(i);
    }

    public void increaseCount() {
        lastCount = count;
        count++;
        stepCountOff(lastCount % nCounts);
        stepCountOn(count % nCounts);
        count = count % nCounts;
    }

    public void setCount(int i) {
        lastCount = count;
        count = i;
        stepCountOff(lastCount % nCounts);
        stepCountOn(count % nCounts);
        count = count % nCounts;
    }

    public int getCount() {
        return count;
    }

    public void setDelay(int d) {
        delay = d;
        timer.setDelay(delay);

        for (int i = 0; i < nTracks; i++) {
            for (int j = 0; j < nCounts; j++) {
                stepGroup[i][j].setDelay(delay);
            }
        }
    }

    public int getDelay() {
        return delay;
    }

    public void setBpm(float i) {
        delay = (int) ((1 / i) * 60000);
        bpm = i;
        this.setDelay(delay);
    }

    public void setResolution(int i) {
        if (i < 0) {
            timer.setDelay((int) (this.getDelay() * Math.abs(i * 2)));
        } else if (i > 0) {
            timer.setDelay((int) (this.getDelay() / Math.abs(i * 2)));
        } else {
            setBpm(bpm);
        }
    }

    public void start() {
        count = 0;
        timer.start();
    }

    public void stop() {
        timer.stop();
        stepCountOff(count);
    }

    public void setAddSteps(boolean b) {
        addSteps = b;
    }

    public boolean getAddSteps() {
        return addSteps;
    }

    public void selectFocusTrack(int i) {
        this.removeComponent(steps);
        this.setFocussedTrack(i);
        this.showFocusTracks(i);

    }

    void setLastFocussedTrack(int id) {
        lastFocussedTrack = id;
    }

    void setFocussedTrack(int i) {
        lastFocussedTrack = currentFocussedTrack;
        currentFocussedTrack = i;
    }

    public int getLastFocussedTrack() {
        return lastFocussedTrack;
    }

    private void showFocusTracks(int i) {
        updateFocussedTrack(i);
        this.add(focusTracks);
    }

    public void unfocusTrack(int i) {
        this.remove(focusTracks);
        this.updateStepVelocities();
        this.add(steps);
    }

    public void updateFocussedTrack(int track) {
        for (int i = 0; i < nCounts; i++) {
            focusStepGroup[i].setVelocityStepLevel(Tools.map(stepGroup[track][i].getVelocity(), 0f, 127f, (float) focusStepGroup[i].getStepShape().getBounds().getHeight(), 0f));
        }
    }

    public void updateStepVelocities() {
        for (int track = 0; track < nTracks; track++) {
            for (int step = 0; step < nCounts; step++) {
                stepGroup[track][step].updateVelocity(velocities[currentPreset][track][step]);
                if(velocities[currentPreset][track][step] > 0) {
                    stepGroup[track][step].setAlive(true);
                }
            }
        }
    }

    public float getBpm() {
        return bpm;
    }

    void updateVelocityArray(int trackID, int stepID, float velocity) {
        velocities[currentPreset][trackID][stepID] = velocity;
    }

    void updateVelocityArray(int stepID, float velocity) {
        velocities[currentPreset][currentFocussedTrack][stepID] = velocity;
    }

    private void printVelocityArray() {
        System.out.println("the current track is :" + currentFocussedTrack);
        for (int i = 0; i < nTracks; i++) {
            for (int j = 0; j < nCounts; j++) {
                System.out.print("[ " + velocities[currentPreset][i][j] + " - " + stepGroup[i][j].getVelocity() + "  -  o = " + stepGroup[i][j].velocityStep.getOpacity() +" ]");
            }
            System.out.println();
        }
    }

    public float getCurrentDefaultVelocity() {
        return currentDefaultVelocity;
    }

    boolean getRemoveSteps() {
        return removeSteps;
    }

    void setRemoveSteps(boolean b) {
        removeSteps = b;
    }

    void setCurrentTrack(int id) {
        currentFocussedTrack = id;
    }

    float getVelocty(int i, int trackID, int stepID) {
        return velocities[i][trackID][stepID];
    }
}


