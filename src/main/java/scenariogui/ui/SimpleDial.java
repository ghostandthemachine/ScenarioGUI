package scenariogui.ui;

import java.awt.geom.Point2D.Double;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGAbstractShape.Mode;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Font;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.tools.Tool;
import scenariogui.Tools;

public class SimpleDial extends GUIComponent {

    SGGroup valueDisplayGroup = new SGGroup();
    FXShape valDisplayBox = new FXShape();
    SGText displayLabel = new SGText();
    SGText label = new SGText();
    FXShape baseEllipse = new FXShape();
    SGGroup subGroup = new SGGroup();
    DropShadow iShadow = new DropShadow();
    double theta = 0;
    private float dashSize = 4;
    private float radius;
    SGTransform.Affine dialAffine;
    float cx, cy, px, py;
    double rotationStep = 10.0;
    private Color fillColor = new Color(100, 200, 100, 30);
    private float value = 0;
    private int numTicks;
    private int stepSize;
    double dx;
    double dy;
    private int lastValue = 0;
    private int selectionValue;
    private GUIComponent parent;
    private int displayPadding = 4;
    private double lastWidth;
    private String name;
    private float minValue = 0;
    private float maxValue = 100;

    public SimpleDial(String name, double tx, double ty, int size, GUIComponent parent) {
        super(tx, ty, size, size);
        this.name = name;
        this.parent = parent;
        radius = (size - (size / 4)) / 2;
        createDial();
        createDisplayValueShape();

    }

    void createDial() {
        super.setBaseColor(new Color(200, 200, 200, 0));

        dx = this.getX() + (this.getWidth() / 2) - radius;
        dy = this.getY() + (this.getHeight() / 2) - radius;

        double br = (radius * 2) - radius / 8;

        baseEllipse.setShape(new Ellipse2D.Double(dx, dy, br, br));
        baseEllipse.setDrawPaint(Color.white);
        baseEllipse.setFillPaint(fillColor);
        baseEllipse.setOpacity(0.6f);
        baseEllipse.setMode(SGShape.Mode.STROKE_FILL);
        baseEllipse.setDrawStroke(new BasicStroke(4));
        baseEllipse.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        subGroup.add(baseEllipse);
        subGroup.add(createIndicator());

        this.addComponent(createDashes(10, 1, 2));



        dialAffine = SGTransform.createAffine(new AffineTransform(), subGroup);

        this.addComponent(dialAffine);
    }

    public void addLabel(String labelString) {
        label.setText(labelString);

        double tx = this.getX() - ((label.getBounds().getWidth() - this.getWidth()) / 2);
        double ty = this.getY() + this.getHeight() + 15;

        label.setFont(new Font("helvetica", Font.PLAIN, 12));
        label.setLocation(new Point2D.Double(tx, ty));
        label.setFillPaint(Color.white);

        this.addComponent(label);
    }

    @Override
    public void dragged() {
        theta -= 2 * this.getYVelocity();
        theta = Tools.constrain(theta, -145, 145); //set constraints for dial rotation
        value = (float) Tools.map(theta, -145, 145, minValue, maxValue);
        rotate(Math.toRadians(theta));
        displayValue(theta);
    }

    @Override
    public void released() {
        hideDisplayValue();
    }

    public void update(float val) {
        theta = Tools.map(val, minValue, maxValue, -2.6f, 2.6f);
        value = val;
        rotate(theta);
    }

    private void rotate(double theta) {

        AffineTransform at = new AffineTransform();
        at.rotate(theta, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
        dialAffine.setAffine(at);

        System.out.println(value);

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
            if (i != num / 2) {
                p.moveTo(x[i], y[i]);
                p.lineTo(x2[i], y2[i]);
            }
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

    private void createDisplayValueShape() {
        double tx = this.getX() + 3;
        double ty = this.getY() + this.getHeight() + 15;

        String value = Float.toString((float) theta);
        displayLabel.setFont(new Font("helvetica", Font.PLAIN, 12));
        displayLabel.setLocation(new Point2D.Double(tx + displayPadding, ty + 2));
        displayLabel.setFillPaint(Color.black);
        displayLabel.setText(value);


        lastWidth = displayLabel.getBounds().getWidth() + displayPadding * 2;

        Point2D.Double p = (Double) this.getGlobalCoordinate();


        valDisplayBox.setShape(new Rectangle2D.Double(tx, ty, displayLabel.getBounds().getWidth() + displayPadding * 2, displayLabel.getBounds().getHeight() + 6));
        valDisplayBox.setDrawPaint(Color.red);
        valDisplayBox.setFillPaint(Color.GREEN);
        valDisplayBox.setMode(Mode.STROKE_FILL);
        valDisplayBox.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        valueDisplayGroup.add(valDisplayBox);
        valueDisplayGroup.add(displayLabel);
    }

    private void displayValue(double theta) {

        double tx = this.getX() + 3;
        double ty = this.getY() + this.getHeight() + 4;
        double tw = displayLabel.getBounds().getWidth() + displayPadding * 2;
        double th = displayLabel.getBounds().getHeight() + 6;

        displayLabel.setText(name + ": " + Float.toString((float) value));

        valDisplayBox.setShape(new Rectangle2D.Double(tx, ty, tw, th));

        parent.addComponent(valueDisplayGroup);

        lastWidth = tw;


    }

    private void hideDisplayValue() {
        parent.removeComponent(valueDisplayGroup);
    }

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setRange(float min, float max) {
        minValue = min;
        maxValue = max;
    }
}
