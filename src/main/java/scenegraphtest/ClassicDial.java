package scenegraphtest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.geom.GeneralPath;

public class ClassicDial extends Component {

    FXShape baseEllipse = new FXShape();
    SGGroup subGroup = new SGGroup();
    DropShadow iShadow = new DropShadow();
    double theta = 0;
    private float dashSize = 8;
    private float radius;
    SGTransform.Affine dialAffine;
    float cx, cy, px, py;
    double rotationStep = 30.0;
    private Color fillColor;
    private float value = 0;

    public ClassicDial(float tx, float ty, float size) {
        super(tx, ty, size, size);
        radius = size - 10;
        createDial();
        fillColor = new Color(200, 200, 200);
    }

    void createDial() {
        super.setBaseColor(new Color(200, 200, 200));
        baseEllipse.setShape(new Ellipse2D.Double(0, 0, radius, radius));
        baseEllipse.setTranslateX(this.getX() + 5);
        baseEllipse.setTranslateY(this.getY() + 5);
        baseEllipse.setDrawPaint(new Color(50, 50, 50));
        baseEllipse.setFillPaint(Color.GREEN);
        baseEllipse.setMode(SGShape.Mode.STROKE_FILL);
        baseEllipse.setDrawStroke(new BasicStroke(2));
        baseEllipse.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        subGroup.add(baseEllipse);
        subGroup.add(createDashes(10, 5, 2));

        dialAffine = SGTransform.createAffine(new AffineTransform(), subGroup);

        this.addComponent(dialAffine);
    }

    @Override
    public void nodeDragged() {
        theta -= this.getyVel();
        theta = Tools.constrain((float) theta, -130f, 130f);
        rotate(Math.toRadians(theta));

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
        GeneralPath p = new GeneralPath();
        dashSize = length;
        double[] x = new double[num];
        double[] y = new double[num];
        double[] x2 = new double[num];
        double[] y2 = new double[num];

        for (int i = 0; i < num; i++) {
            double radians = Math.toRadians(360 / num * i - 90);
            x[i] = (radius/2 - dashSize) * Math.cos(radians) + this.getX() - this.getWidth()/2 + radius/4;
            y[i] = (radius/2 - dashSize) * Math.sin(radians) + this.getY() - this.getHeight()/2 + radius/4;
            x2[i] = (radius/2) * Math.cos(radians) + this.getX() - this.getWidth()/2 + radius/4;
            y2[i] = (radius/2) * Math.sin(radians) + this.getY() - this.getHeight()/2 + radius/4;
        }

        for (int i = 0; i < num; i++) {
            p.moveTo(x[i], y[i]);
            p.lineTo(x2[i], y2[i]);
        }

        FXShape tick = new FXShape();
        tick.setShape(p);
        tick.setFillPaint(Color.black);
        tick.setDrawPaint(Color.black);
        tick.setMode(SGShape.Mode.STROKE_FILL);
        tick.setDrawStroke(new BasicStroke(strokeWidth));
        tick.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
        SGTransform.Translate trans = SGTransform.createTranslation(radius, radius, tick);
        return trans;

    }
}
