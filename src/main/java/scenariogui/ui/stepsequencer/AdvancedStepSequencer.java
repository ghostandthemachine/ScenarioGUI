package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import scenariogui.ui.GUIButton;
import scenariogui.ui.GUIButtonClickListener;

public class AdvancedStepSequencer extends StepSequencer {

    private int numPresetButtons = 8;
    private FXShape buttonShape = new FXShape();
    private GUIButton[] trackSelectionButtons;
    private GUIButton[] buttons;
    private Color presetButtonColor = Color.ORANGE;
    private int numTracks;
    private int numSteps;

    public AdvancedStepSequencer(float tx, float ty, int tsteps, int ttracks) {
        super(tx, ty, 250, 100, tsteps, ttracks, 50, 0, 90, 0);
        numTracks = ttracks;
        numSteps = tsteps;

        this.addComponent(createTrackSelectorInterface());
        this.addComponent(createPresetInterface());

        ResolutionDial resoultionDial = new ResolutionDial(this.getX() + this.getWidth() - 24, this.getY() + 6, 16, this);
        resoultionDial.setBaseShapeOpacity(0.3f);
        addResolutionLabel();

        addComponent(resoultionDial.getComponentGroup());


    }

    public SGGroup createPresetInterface() {
        SGGroup group = new SGGroup();
        buttons = new GUIButton[numPresetButtons];

        Point2D.Double p1 = new Point2D.Double(2 + this.getX(), 2 + this.getY());
        buttons[0] = new GUIButton(p1.x, p1.y, 12, 12, Integer.toString(0));
        buttons[0].addMouseListener(new GUIButtonClickListener(this) {

            private boolean toggle = false;

            @Override
            public void clicked() {
                AdvancedStepSequencer ass = (AdvancedStepSequencer) parent;
                if (toggle) {
                    ass.stop();
                    toggle = false;
                    buttons[0].setOff();
                } else {
                    ass.start();
                    toggle = true;
                    buttons[0].setOn();
                }
            }
        });
        group.add(buttons[0].getComponentGroup());

        return group;
    }

    public SGGroup createTrackSelectorInterface() {
        SGGroup group = new SGGroup();
        trackSelectionButtons = new GUIButton[numTracks];

        Point2D.Double p = new Point2D.Double(this.getX(), this.getY());
        Point2D p2 = this.getBaseShape().localToGlobal(p, p);


        for (int i = 0; i < numTracks; i++) {
            final int id = i;

            Point2D.Double p1 = new Point2D.Double((p2.getX() + this.getxStepOffset() + this.getX()) / 2, p2.getY() + (i * this.getStepShape().getHeight()) + 2);

            double bw = this.getStepShape().getWidth() * 0.8;
            double bh = this.getStepShape().getWidth() * 0.8;
            double bx = p1.x + this.getxStepOffset() - 35 ;
            double by = p1.y + (i * this.getStepShape().getHeight()) + 2;

            trackSelectionButtons[i] = new GUIButton(bx, by, bw, bh, Integer.toString(0));
            trackSelectionButtons[i].addMouseListener(new GUIButtonClickListener(this) {

                private boolean toggle = false;

                @Override
                public void clicked() {
                    StepSequencer sequencer = (StepSequencer) parent;

                    if (trackSelectionButtons[id].isOn()) {
                        trackSelectionButtons[id].setOff();
                        sequencer.unfocusTrack(id);
                    } else if (!trackSelectionButtons[id].isOn()) {
                        trackSelectionButtons[id].setOn();
                        sequencer.updateFocussedTrack(sequencer.getLastFocussedTrack());
                        sequencer.selectFocusTrack(id);

                        sequencer.setCurrentFocussedTrack(id);
                    }

                    setTrackSelectionGroupOff(id);
                }

                @Override
                public void pressed() {
                }

                public void releassed() {
                }
            });
            group.add(trackSelectionButtons[i].getComponentGroup());
        }

        return group;
    }

    private void setTrackSelectionGroupOff(int id) {
        for (int i = 0; i < numTracks - 1; i++) {
            if (i != id) {
                trackSelectionButtons[i].setOff();
            }
        }
    }

    private void addResolutionLabel() {

        double textX = this.getGlobalCoordinate().getX() + this.getWidth() - 26;
        double textY = this.getGlobalCoordinate().getY() + 30;

        SGText resolutionLabel = new SGText();
        resolutionLabel.setFont(new Font("helvitica", Font.PLAIN, 9));
        resolutionLabel.setFillPaint(Color.white);
        resolutionLabel.setLocation(new Point2D.Double(textX, textY));
        resolutionLabel.setText("reso");

        this.addComponent(resolutionLabel);
    }
}
