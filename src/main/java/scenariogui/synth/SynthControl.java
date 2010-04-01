/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

import com.sun.scenario.scenegraph.SGText;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
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

    SGText mainLabel = new SGText();
    ISynth synth;
    float[] args = {0f, 0f, 0f, 0f, 0f};
    int dialWidth = 40;
    int padding = 10;
    int spacing = 15;
    int numDials;
    SimpleDial[] dials;

    public SynthControl(ISynth synth) {
        super(0, 0, (40 * synth.getParams().length + 30), 100);
        this.numDials = synth.getParams().length;
        this.synth = synth;
        this.dials = new SimpleDial[numDials];

        this.setBaseColor(new Color(100,100,100));

        createDials(numDials);
        createButtons();
        addMainLabel(synth.getName());

        this.setWidth(numDials * (dialWidth + spacing) + padding * 2);
    }

    private void createDials(int numDials) {
        ISynthParam[] params = synth.getParams();

        for (int i = 0; i < numDials; i++) {
            String name = params[i].getName();
            SimpleDial dial = dials[i];
            int x = padding + (i * (dialWidth + spacing));
            int y = padding;

            dial = new SimpleDial(name, x, y, dialWidth, this) {

                @Override
                public void dragged() {
                    super.dragged();
                    synth.control(this.getName(), this.getValue());
                }
            };

            dial.addLabel(name);
            dial.setRange(params[i].getStart(), params[i].getEnd());
            this.add(dial);
        }



    }

    void createButtons() {
        double tx = this.getX() + 20;
        double ty = this.getY() + this.getHeight() - 26;


        GUIButton trigger = new GUIButton(tx, ty, 20, 20, "trigger") {


            @Override
            public void clicked() {
                if (!this.isOn()) {
                    this.setOn();
                    synth.play(args);
                } else {
                    this.setOff();
                    synth.kill();
                }
            }
        };
        trigger.setBaseColor(Color.darkGray);
        trigger.setOnColor(Color.lightGray);
        trigger.setTriangleOnColor(Color.green);
        trigger.setTriangleColor(Color.white);

        this.addComponent(trigger);
    }

    public void addMainLabel(String t) {
        mainLabel.setText(t);

        double tx = this.getX() + this.getWidth() - mainLabel.getBounds().getWidth();
        double ty = this.getY() + this.getHeight() - mainLabel.getBounds().getHeight()/2;
        mainLabel.setFont(new Font("helvetica", Font.BOLD, 20));
        mainLabel.setLocation(new Point2D.Double(tx, ty));
        mainLabel.setFillPaint(Color.white);

        this.add(mainLabel);
    }



}
