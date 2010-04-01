/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

import java.awt.Color;
import scenariogui.ui.GUIButton;
import scenariogui.ui.GUIComponent;
import scenariogui.ui.SimpleDial;

/**
 *
 * @author Jon Rose
 */
public class SynthControl extends GUIComponent {

    ISynth synth;
    float[] args = {0f,0f,0f,0f,0f};
    int dialWidth = 40;
    int padding = 10;
    int spacing = 15;
    int numDials;
    SimpleDial[] dials;

    public SynthControl(ISynth synth) {
        super(0, 0, (40 * synth.getParams().length + 30), 90);
        this.numDials = synth.getParams().length;
        this.synth = synth;
        this.dials = new SimpleDial[numDials];

        createDials(numDials);
        createButtons();
        
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
            this.add(dial);
        }



    }

    void createButtons() {
        double tx = this.getX() + 10;
        double ty = this.getY() + this.getHeight() - 30;


        GUIButton trigger = new GUIButton(tx, ty, 20, 20, "trigger"){

            @Override
            public void clicked(){
                if(!this.isOn()) {
                    this.setOn();
                    synth.play(args);
                } else {
                    this.setOff();
                    synth.kill();
                }
            }
        };
        trigger.setBaseColor(Color.darkGray);
        trigger.setOnColor(Color.GREEN);

        this.addComponent(trigger);
    }
}
