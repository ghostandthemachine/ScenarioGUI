package scenariogui.ui.stepsequencer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import scenariogui.Triangle;
import scenariogui.ui.GUIButton;
import scenariogui.ui.GUIButtonClickListener;

public class AdvancedStepSequencer extends StepSequencer {

    private int numPresetButtons = 8;
    private FXShape buttonShape = new FXShape();
    private GUIButton[] trackSelectionButtons;
    private GUIButton[][] presetButtons;
    private Color presetButtonColor = Color.ORANGE;
    private int numTracks;
    private int numSteps;
    private GUIButton trigger;

    public AdvancedStepSequencer(float tx, float ty, int tsteps, int ttracks) {
        super(tx, ty, 250, 100, tsteps, ttracks, 50, 0, 90, 0);
        numTracks = ttracks;
        numSteps = tsteps;

        this.addComponent(createTrackSelectorInterface());
//        this.addComponent(createPresetInterface());

        ResolutionDial resoultionDial = new ResolutionDial(this.getX() + this.getWidth() - 24, this.getY() + 6, 16, this);
        resoultionDial.setBaseShapeOpacity(0.3f);
        addResolutionLabel();

        addComponent(resoultionDial);

        createButtons();


    }

    void createButtons() {
        double tx = this.getX() + 3;
        double ty = this.getY() + 3;


        trigger = new GUIButton(tx, ty, 10, 10, "trigger") {

            private boolean toggle = false;

            @Override
            public void clicked() {
                if (toggle) {
                    stop();
                    toggle = false;
                    trigger.setOff();
                } else {
                    start();
                    toggle = true;
                    trigger.setOn();
                }
            }
        };

        trigger.setBaseColor(Color.lightGray);
        trigger.setOnColor(Color.white);
        trigger.addIndicator(createTriangle());
        trigger.setIndicatorOnColor(Color.green);
        trigger.setIdicatorColor(Color.white);

        this.add(trigger);
    }

    public SGGroup createPresetInterface() {
        SGGroup group = new SGGroup();
        presetButtons = new GUIButton[numPresetButtons / 2][numPresetButtons / 2];

        double bw = 10;
        double bh = 10;

        for (int i = 0; i < (numPresetButtons / 2) - 1; i++) {
            for (int j = 0; j < (numPresetButtons / 2) - 1; j++) {
                double tx = 3 + (i * (bw + 3));
                double ty = 3 + (j * (bh + 3));
                presetButtons[i][j] = new GUIButton(tx, ty, bw, bh, Integer.toString(0));
                presetButtons[i][j].addMouseListener(new GUIButtonClickListener(presetButtons[i][j], i, j) {

                    private boolean toggle = false;

                    @Override
                    public void clicked() {
                        if (toggle) {
                            toggle = false;
                            this.getParent().setOff();
                        } else {
                            toggle = true;
                            this.getParent().setOn();
                            for (int i = 0; i < numPresetButtons / 2 - 1; i++) {
                                for (int j = 0; j < numPresetButtons / 2 - 1; j++) {
                                    if (i != this.getID()) {
                                        presetButtons[i][j].setOff();
                                    }
                                }
                            }
                        }
                    }
                });


                group.add(presetButtons[i][j]);
            }
        }




        return group;
    }

    public SGGroup createTrackSelectorInterface() {
        SGGroup group = new SGGroup();
        trackSelectionButtons = new GUIButton[numTracks];

        Point2D.Double p = new Point2D.Double(this.getX(), this.getY());
        Point2D p2 = this.getBaseShape().localToGlobal(p, p);




        for (int i = 0; i
                < numTracks; i++) {
            final int id = i;

            Point2D.Double p1 = new Point2D.Double((p2.getX() + this.getxStepOffset() + this.getX()) / 2, p2.getY() + (i * this.getStepShape().getHeight()) + 2);



            double bw = this.getStepShape().getWidth() * 0.8;


            double bh = this.getStepShape().getWidth() * 0.8;


            double bx = p1.x + this.getxStepOffset() - 35;


            double by = p1.y + (i * this.getStepShape().getHeight()) + 2;

            trackSelectionButtons[i] = new GUIButton(bx, by, bw, bh, Integer.toString(0));
            trackSelectionButtons[i].addMouseListener(new GUIButtonClickListener(this, 0, i) {

                private boolean toggle = false;

                @Override
                public void clicked() {
                    AdvancedStepSequencer seq = this.getSequencer();
                    if (trackSelectionButtons[id].isOn()) {
                        trackSelectionButtons[id].setOff();
                        seq.unfocusTrack(id);
                        seq.setIsFocusMode(false);  //turn off focus mode boolean


                    } else if (!trackSelectionButtons[id].isOn()) {

                        if (!seq.isFocusMode()) {    //set a boolean to determine if the sequencer is in focus mode
                            seq.setIsFocusMode(true);


                        } else if (seq.isFocusMode()) {
                            seq.updateStepVelocities();


                        }

                        trackSelectionButtons[id].setOn();
                        seq.updateFocussedTrack(seq.getLastFocussedTrack());
                        seq.selectFocusTrack(id);
                        seq.setCurrentTrack(id);

                        seq.setCurrentFocussedTrack(id);





                    }

                    setTrackSelectionGroupOff(id);


                }

                @Override
                public void pressed() {
                }

                public void releassed() {
                }
            });
            group.add(trackSelectionButtons[i]);


        }

        return group;


    }

    private void setTrackSelectionGroupOff(int id) {
        for (int i = 0; i
                < numTracks - 1; i++) {
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

    private FXShape createTriangle() {
        int pad = 3;
        FXShape triangle = new FXShape();
        Point p1 = new Point((int) (pad + trigger.getX()), (int) (pad + trigger.getY()));
        Point p2 = new Point((int) (trigger.getWidth() - pad + trigger.getX()), (int) (trigger.getY() + trigger.getHeight() / 2));
        Point p3 = new Point((int) (pad + trigger.getX()), (int) (trigger.getY() + trigger.getHeight() - pad));

        Triangle tri = new Triangle(p1, p2, p3);

        triangle.setShape(tri);


        return triangle;

    }
}
