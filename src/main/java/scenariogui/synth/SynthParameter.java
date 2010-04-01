/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

/**
 *
 * @author Jon Rose
 */
public class SynthParameter implements ISynthParameter {

    String name;
    float start;
    float end;
    float step;

    public SynthParameter(String name, float start, float end, float step) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getStart() {
        return start;
    }

    @Override
    public float getEnd() {
        return end;
    }

    @Override
    public float getStep() {
        return step;
    }
}
