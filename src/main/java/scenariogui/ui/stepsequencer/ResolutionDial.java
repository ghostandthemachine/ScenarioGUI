package scenariogui.ui.stepsequencer;

import scenariogui.ui.GUIComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import scenariogui.Tools;

public class ResolutionDial extends GUIComponent {

    FXShape baseEllipse = new FXShape();
    SGGroup subGroup = new SGGroup();
    DropShadow iShadow = new DropShadow();
    double theta = 0;
    private float dashSize = 4;
    private float radius;
    SGTransform.Affine dialAffine;
    float cx, cy, px, py;
    double rotationStep = 10.0;
    private Color fillColor;
    private float value = 0;
    private int numTicks;
    private int stepSize;
    double dx;
    double dy;
    private int lastValue = 0;
    private int selectionValue;
    private StepSequencer parent;

    public ResolutionDial(double tx, double ty, int size, StepSequencer s) {
        super(tx, ty, size, size);
        parent = s;
        radius = (size - (size / 4)) / 2;
        createDial();
        fillColor = new Color(200, 200, 200, 100);
    }

    void createDial() {
        super.setBaseColor(new Color(200, 200, 200));

        dx = this.getX() + (this.getWidth() / 2) - radius;
        dy = this.getY() + (this.getHeight() / 2) - radius;

        double br = (radius * 2) - radius / 8;

        baseEllipse.setShape(new Ellipse2D.Double(dx, dy, br, br));
        baseEllipse.setDrawPaint(Color.gray);
        baseEllipse.setFillPaint(Color.GREEN);
        baseEllipse.setOpacity(0.6f);
        baseEllipse.setMode(SGShape.Mode.STROKE_FILL);
        baseEllipse.setDrawStroke(new BasicStroke(1));
        baseEllipse.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        subGroup.add(baseEllipse);
        subGroup.add(createIndicator());

        this.addComponent(createDashes(5, 1, 2));



        dialAffine = SGTransform.createAffine(new AffineTransform(), subGroup);

        this.addComponent(dialAffine);
    }

    @Override
    public void nodeDragged() {
        theta -= 2 * this.getYVelocity();
        theta = Tools.constrain(theta, -180, 180);
        float updateTheta = ((int) (theta / stepSize)) * stepSize;
        rotate(Math.toRadians(updateTheta));

        int value = (int) theta / stepSize;
        if (lastValue != value) {
            setSelectionValue(value);
        }
        lastValue = value;


    }

    public void update() {
        theta = Tools.map(value, 0f, 100f, -2.6f, 2.6f);
        theta = Tools.constrain((float) theta, -2.6f, 2.6f);
        rotate(theta);
    }

    private void rotate(double theta) {
        AffineTransform at = new AffineTransform();
        at.rotate(theta, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
        dialAffine.setAffine(at);

    }

    private SGNode createDashes(int num, int length, int strokeWidth) {
        SGGroup group = new SGGroup();
        GeneralPath p = new GeneralPath();
        dashSize = length;
        numTicks = num;
        stepSize = 360 / numTicks;

        double[] x = new double[num];
        double[] y = new double[num];
        double[] x2 = new double[num];
        double[] y2 = new double[num];

        for (int i = 0; i < num; i++) {
            double radians = Math.toRadians(360 / num * i - 90);
            x[i] = (radius + (radius / 2) - dashSize) * Math.cos(radians) + this.getX() + ((this.getWidth() - (radius * 2)) / 2);
            y[i] = (radius + (radius / 2) - dashSize) * Math.sin(radians) + this.getY() + ((this.getHeight() - (radius * 2)) / 2);
            x2[i] = (radius + (radius / 2)) * Math.cos(radians) + this.getX() + ((this.getWidth() - (radius * 2)) / 2);
            y2[i] = (radius + (radius / 2)) * Math.sin(radians) + this.getY() + ((this.getHeight() - (radius * 2)) / 2);
        }

        for (int i = 0; i < num; i++) {
            p.moveTo(x[i], y[i]);
            p.lineTo(x2[i], y2[i]);
        }

        FXShape tick = new FXShape();
        tick.setShape(p);
        tick.setFillPaint(Color.black);
        tick.setDrawPaint(Color.white);
        tick.setMode(SGShape.Mode.STROKE_FILL);
        tick.setDrawStroke(new BasicStroke(strokeWidth));
        tick.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        SGTransform.Translate trans = SGTransform.createTranslation(radius, radius, tick);

        group.add(trans);

        FXShape arc = new FXShape();
        double aw = (radius * 2) + radius / 2;
        double ax = this.getX() + ((this.getWidth() - aw) / 2);
        double ay = this.getY() + ((this.getHeight() - aw) / 2);

        arc.setShape(new Arc2D.Double(ax, ay, aw, aw, -(stepSize) + 12, stepSize * 4 + 12, Arc2D.OPEN));
        arc.setDrawPaint(Color.white);
        arc.setMode(SGShape.Mode.STROKE);
        arc.setDrawStroke(new BasicStroke(1));
        arc.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        group.add(arc);

        return group;

    }

    private SGNode createIndicator() {
        //add indicator on dial
        FXShape indicator = new FXShape();
        double ix = dx + radius;
        double iy = dy - radius / 4;
        indicator.setShape(new Line2D.Double(ix, iy, ix, iy + radius));
        indicator.setFillPaint(Color.white);
        indicator.setDrawPaint(Color.white);
        indicator.setMode(SGShape.Mode.STROKE_FILL);
        indicator.setDrawStroke(new BasicStroke(2));
        indicator.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        return indicator;
    }

    private void setSelectionValue(int value) {
        parent.setResolution(value);
    }
}
