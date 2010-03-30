package scenegraphtest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class StepSequencer extends Component {

    Step[][] stepGroup;
    SGShape featuresBack = new SGShape();
    SGGroup steps;
    float sw;
    float sh;
    float sr;
    FXShape base = new FXShape();
    boolean overFeatures = false;
    float r;
    public String ADD_HIT = "addHit";
    public String REMOVE_HIT = "removeHit";
    private String stepState = ADD_HIT;

    public StepSequencer(float tx, float ty, float tw, float th, float tsteps, float ttracks) {
        super(tx, ty, tw, th);
        this.setBaseColor(new Color(200, 200, 200));
        r = ((tw - 4) / tsteps) / 4;
        setBaseColor(new Color(50, 50, 50));

        base.setShape(new RoundRectangle2D.Float(0, 0, tw, th, r, r));
        stepGroup = new Step[(int) tsteps][(int) ttracks];
        steps = new SGGroup();
        sw = ((tw - 4) / tsteps);
        sh = ((th - 4) / ttracks);
        sr = ((tw - 4) / tsteps) / 4;
        for (int i = 0; i < tsteps; i++) {
            for (int j = 0; j < ttracks; j++) {
                stepGroup[i][j] = new Step(this, 3.5 + (i * sw) + tx, 3.5 + (j * sh) + ty, sw - 3, sh - 3, sr);
                steps.add(stepGroup[i][j].callStep());
            }
        }

        this.addComponent(featuresBack);
        this.addComponent(steps);
    }

    public void nodeOver() {
    }

    public void nodeExited() {
    }

    public void setHit(String s){
        stepState = s;
    }

    public String getStepState() {
        return stepState;
    }
}

class Step {

    float x;
    float y;
    float w;
    float h;
    float r;
    float defaultVelocity = 100;
    float velocity = 0;
    int counter = 0;
    boolean isAlive;
    FXShape step;
    StepSequencer parent;

    Step(StepSequencer s, double tx, double ty, float tw, float th, float tr) {
        x = (float)tx;
        y = (float)ty;
        w = tw;
        h = th;
        r = tr;
        parent = s;
        FXShape tstep = new FXShape();
        tstep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
        tstep.setFillPaint(new Color(150, 150, 150));
        tstep.setMode(SGShape.Mode.STROKE_FILL);
        tstep.setDrawStroke(new BasicStroke(1.0f));
        tstep.setDrawPaint(Color.GRAY);
        tstep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        tstep.addMouseListener(new SGMouseAdapter() {

            Point pos;

            @Override
            public void mouseEntered(MouseEvent e, SGNode n) {
                FXShape node = (FXShape) n;
                node.setDrawPaint(Color.white);
                if (MasterPanel.isMouseDragged() && !parent.getStepState().equals(parent.ADD_HIT)) {
                    if (counter % 2 == 0) {
                        node.setDrawPaint(Color.orange);
                        node.setFillPaint(new Color(200, 240, 255));
                        counter++;
                        velocity = defaultVelocity;
                    } else if (counter % 2 == 1 && parent.getStepState().equals(parent.REMOVE_HIT)) {
                        node.setFillPaint(Color.GRAY);
                        counter++;
                        velocity = 0;
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e, SGNode n) {
                FXShape node = (FXShape) n;
                node.setDrawPaint(Color.GRAY);
            }

            @Override
            public void mousePressed(MouseEvent e, SGNode n) {
                FXShape node = (FXShape) n;
                pos = e.getPoint();

                if (counter % 2 == 0) {
                    node.setDrawPaint(Color.orange);
                    node.setFillPaint(new Color(200, 240, 255));
                    parent.setHit(parent.ADD_HIT);
                } else if (counter % 2 == 1) {
                    node.setFillPaint(Color.GRAY);
                    parent.setHit(parent.REMOVE_HIT);
                }
                counter++;
            }
        });
        step = tstep;
    }



    SGNode callStep() {
        return step;
    }
}
