package scenariogui.ui.stepsequencer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SequencerTimerListener implements ActionListener {

    private final StepSequencer stepSeq;

    public SequencerTimerListener(StepSequencer ss) {
        stepSeq = ss;
    }

    public void actionPerformed(ActionEvent e) {
        stepSeq.increaseCount();
    }
}
