/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

/**
 *
 * @author Jon Rose
 */
public class TestSynth implements ISynth {

    String name = "TestSynth";
    SynthParameter[] params = {
        new SynthParameter("level", 0, 2000, 1),
        new SynthParameter("freq", 0, 20000, 1),
        new SynthParameter("pan", -100, 100, 1),
        new SynthParameter("dur", 0, 2000, 1),
        new SynthParameter("release", 0, 2000, 1),
        new SynthParameter("attack", 0, 20000, 1),
        new SynthParameter("sustain", -100, 100, 1),
        new SynthParameter("nothing", 0, 2000, 1)
    };

    @Override
    public ISynthParam[] getParams() {
        return params;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void play(float[] args) {
        System.out.println("play " + this + " synth");

    }

    @Override
    public void kill() {
        System.out.println("kill " + this + " synth");

    }

    @Override
    public void control(String parameter, float value) {
        System.out.println("Control: " + parameter + "   " + value);
    }
}
