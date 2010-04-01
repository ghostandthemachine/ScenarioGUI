/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.synth;

/**
 *
 * @author Jon
 */
public interface ISynth {

    ISynthParameter[] getParams();

    String getName();

    void play(float[] args);

    void kill();

    void control(String parameter, float value);
}
