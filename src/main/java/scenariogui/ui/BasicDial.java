package scenariogui.ui;

import scenariogui.ui.GUIComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import scenariogui.Tools;

public class BasicDial extends GUIComponent {

    FXShape bigArc = new FXShape();
    FXShape smallEllipse = new FXShape();
    SGGroup subGroup = new SGGroup();
    DropShadow iShadow = new DropShadow();
    double theta = 0;
    SGTransform.Affine dialAffine;
    float cx, cy, px, py;
    double rotationStep = 30.0;
    private Color fillColor;
    private float value = 0;

    public BasicDial(float tx, float ty, float size) {
        super(tx, ty, size, size);
        createDial();
        fillColor = new Color(200, 200, 200);
    }

    void createDial() {
        super.setBaseColor(new Color(200, 200, 200));
        bigArc.setShape(new Arc2D.Double(0, 0, this.getWidth() - 10, this.getHeight() - 10, 125, 290, 0));
        bigArc.setTranslateX(this.getX() + 5);
        bigArc.setTranslateY(this.getY() + 5);
        bigArc.setDrawPaint(new Color(50, 50, 50));
        bigArc.setMode(SGShape.Mode.STROKE);
        bigArc.setDrawStroke(new BasicStroke(2));
        bigArc.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        smallEllipse.setShape(new Ellipse2D.Double(0, 0, this.getWidth() / 5, this.getHeight() / 5));
        smallEllipse.setTranslateX(this.getX() + this.getWidth() / 2 - this.getWidth() / 10);
        smallEllipse.setTranslateY(this.getY() + 5);
        smallEllipse.setDrawPaint(Color.GREEN);
        smallEllipse.setMode(SGShape.Mode.STROKE);
        smallEllipse.setDrawStroke(new BasicStroke(2));
        smallEllipse.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

        subGroup.add(smallEllipse);
        subGroup.add(bigArc);

        dialAffine = SGTransform.createAffine(new AffineTransform(), subGroup);

        this.addComponent(dialAffine);
    }

    @Override
    public void nodeDragged() {
        theta -= this.getYVelocity();
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
        at.rotate(theta, this.getX() + this.getWidth() / 2 , this.getX() + this.getHeight() / 2);
        dialAffine.setAffine(at);

    }
}
