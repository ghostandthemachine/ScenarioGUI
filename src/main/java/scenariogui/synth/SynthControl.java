/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import scenariogui.Triangle;
import scenariogui.ui.GUIButton;
import scenariogui.ui.GUIComponent;
import scenariogui.ui.SimpleDial;

/**
 *
 * @author Jon Rose
 */
public class SynthControl extends GUIComponent {

    GUIButton trigger;
    SGText mainLabel = new SGText();
    ISynth synth;
    int dialWidth = 40;
    int padding = 10;
    int spacing = 15;
    int nParams;
    SimpleDial[] dials;

    public SynthControl(ISynth synth) {
        super(0, 0, (40 * synth.getParams().length + 30), 100);
        this.nParams = synth.getParams().length;
        this.synth = synth;
        this.setBaseColor(new Color(100, 100, 100));

        createDials();
        createButtons();
        addMainLabel(synth.getName());

        this.setWidth(nParams * (dialWidth + spacing) + padding * 2);
    }

    private void createDials() {
        dials = new SimpleDial[nParams];
        ISynthParameter[] params = synth.getParams();

        for (int i = 0; i < nParams; i++) {
            String name = params[i].getName();
            int x = padding + (i * (dialWidth + spacing));
            int y = padding;

            SimpleDial dial = new SimpleDial(name, x, y, dialWidth, this) {
                @Override
                public void dragged() {
                    super.dragged();
                    synth.control(this.getName(), this.getValue());
                }
            };

            dial.addLabel(name);
            dial.setRange(params[i].getStart(), params[i].getEnd());
            this.add(dial);
            dials[i] = dial;
        }



    }

    float[] getParamVals() {
      float[] vals = new float[nParams];
      for(int i = 0; i < nParams; i++) {
        vals[i] = dials[i].getValue();
      }
      return vals;
    }

    void createButtons() {
        double tx = this.getX() + 20;
        double ty = this.getY() + this.getHeight() - 26;


        trigger = new GUIButton(tx, ty, 20, 20, "trigger") {

            @Override
            public void clicked() {
                if (!this.isOn()) {
                    this.setOn();

                    synth.play(getParamVals());
                } else {
                    this.setOff();
                    synth.kill();
                }
            }
        };
        trigger.setBaseColor(Color.darkGray);
        trigger.setOnColor(Color.lightGray);
        trigger.addIndicator(createTriangle());
        trigger.setIndicatorOnColor(Color.green);
        trigger.setIdicatorColor(Color.white);

        this.addComponent(trigger);
    }

    public void addMainLabel(String t) {
        mainLabel.setText(t);

        double tx = this.getX() + this.getWidth() - mainLabel.getBounds().getWidth();
        double ty = this.getY() + this.getHeight() - mainLabel.getBounds().getHeight() / 2;
        mainLabel.setFont(new Font("helvetica", Font.BOLD, 20));
        mainLabel.setLocation(new Point2D.Double(tx, ty));
        mainLabel.setFillPaint(Color.white);

        this.add(mainLabel);
    }

    private FXShape createTriangle() {
        FXShape triangle = new FXShape();
        Point p1 = new Point((int) (5 + trigger.getX()),(int) (5 + trigger.getY()));
        Point p2 = new Point((int) (trigger.getWidth() - 5 + trigger.getX()), (int) (trigger.getY() + trigger.getHeight() / 2));
        Point p3 = new Point((int) (5 + trigger.getX()), (int) (trigger.getY() + trigger.getHeight() - 5));

        Triangle tri = new Triangle(p1, p2, p3);

        triangle.setShape(tri);
        return triangle;
    }
}
