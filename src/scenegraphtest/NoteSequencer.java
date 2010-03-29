package scenegraphtest;

/* Need to change draw/erase so that when you start a new note, everything is in draw
 * and when you start by erasing, everything after is erased
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;

import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class NoteSequencer extends Component {

    private boolean blackKeyOver = false;
    private float widthUpdateVal;
    FXShape[] keys;
    NoteSeqStep[] keyStep;
    Float[] stepVelocity;
    FXShape smallEllipse = new FXShape();
    SGGroup subGroup = new SGGroup();
    SGGroup whiteKetStepGroup = new SGGroup();
    float cx, cy, px, py;
    private boolean overSharpKey = false;
    // Color Variables
    Color keyHighlightColor = Color.orange;
    Color keyFillColor = Color.white;
    Color keyHitFillColor = Color.green;
    Color keyStrokeColor = Color.black;
    Color sharpKeyFillColor = Color.black;
    Color sharpKeyStrokeColor = Color.gray;
    Color stepCountColor = Color.cyan;
    Color stepHighlightFillColor = Color.green;
    // Stroke Sizes
    private float strokeSize = 0.5f;
    private float highlightStrokeSize = 1.5f;
    // Setup variables
    private int numOctaves;
    private int numSteps;
    private float lastValue;
    private MasterPanel parentPanel;

    public NoteSequencer(double tx, double ty, double w, double h, int octaves, int steps, MasterPanel panel) {
        super(tx, ty, w, h);
        parentPanel = panel;
        numOctaves = octaves + 1;			//add one for easier time dealing with the loops later
        numSteps = steps;
        widthUpdateVal = (float) ((this.getWidth() / (numSteps + 2) * 2) + (numSteps * (this.getWidth() / (numSteps + 2)) + 3));
        createKeys();
        createSequencer();
        super.setBaseColor(keyStrokeColor);
        float r = (float) (((this.getWidth() * this.getHeight()) / 2) / 16);
        FXShape baseRect = this.getBaseShape();
        baseRect.setShape(new RoundRectangle2D.Double(this.getX(), this.getY(), widthUpdateVal, h, 10, 10));
    }

    void createKeys() {
        SGGroup bGroup = new SGGroup();
        SGGroup wGroup = new SGGroup();
        FXShape[] keys = new FXShape[numOctaves * 12];
        double keyHeight = ((this.getHeight() - 4) / (12 * (numOctaves - 1)));
        double keyWidth = ((this.getWidth() - 1) / (numSteps + 2) * 2);

        for (int k = 0; k < numOctaves - 1; k++) {
            for (int j = 0; j < 12; j++) {
                keys[j + (k * 12)] = new FXShape();
                if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10) {
                    bGroup.add(keys[j + (k * 12)]);
                    keyFillColor = Color.black;
                    keys[j + (k * 12)].setFillPaint(Color.BLACK);
                    keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth - keyWidth / 3, keyHeight + (keyHeight / 4)));
                    keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                    keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 4 - keyHeight / 16);
                    keys[j + (k * 12)].setDrawPaint(keyStrokeColor);
                    keys[j + (k * 12)].setMode(SGShape.Mode.STROKE_FILL);
                    keys[j + (k * 12)].setDrawStroke(new BasicStroke(strokeSize));
                    keys[j + (k * 12)].setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
                    keys[j + (k * 12)].addMouseListener(new SGMouseAdapter() {

                        @Override
                        public void mouseEntered(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            blackKeyOver = true;
                            node.setDrawStroke(new BasicStroke(highlightStrokeSize));
                            node.setDrawPaint(Color.orange);
                            overSharpKey = true;
                        }

                        @Override
                        public void mouseExited(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            blackKeyOver = false;
                            node.setDrawStroke(new BasicStroke(strokeSize));
                            node.setDrawPaint(keyStrokeColor);
                            overSharpKey = false;
                        }

                        @Override
                        public void mousePressed(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            node.setDrawPaint(Color.red);
                        }

                        @Override
                        public void mouseReleased(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            node.setDrawPaint(sharpKeyFillColor);
                        }
                    });
                } else {
                    keyFillColor = Color.white;
                    keys[j + (k * 12)].setFillPaint(Color.white);
                    wGroup.add(keys[j + (k * 12)]);

                    if (j == 0) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 - keyHeight / 4 - keyHeight / 16));

                        if (k == 0) {
                            keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY());
                        } else {
                            keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 16);
                        }
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                    }
                    if (j == 2) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 + keyHeight / 8 + keyHeight / 16));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 2 - keyHeight / 16);
                    }
                    if (j == 4) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 - keyHeight / 16));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 2 - keyHeight / 16);
                    }
                    if (j == 5) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 16);
                    }
                    if (j == 7) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight - keyHeight / 16));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 2);
                    }
                    if (j == 9) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 + keyHeight / 8 + keyHeight / 8));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 2 - keyHeight / 16);
                    }
                    if (j == 11) {
                        keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2));
                        keys[j + (k * 12)].setTranslateX(this.getX() + 2);
                        keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + this.getY() - keyHeight / 2 - keyHeight / 8);
                    }
                    keys[j + (k * 12)].setDrawPaint(keyStrokeColor);
                    keys[j + (k * 12)].setMode(SGShape.Mode.STROKE_FILL);
                    keys[j + (k * 12)].setDrawStroke(new BasicStroke(strokeSize));
                    keys[j + (k * 12)].setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
                    keys[j + (k * 12)].addMouseListener(new SGMouseAdapter() {

                        @Override
                        public void mouseEntered(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            blackKeyOver = true;
                            node.setDrawStroke(new BasicStroke(highlightStrokeSize));
                            node.setDrawPaint(Color.orange);
                        }

                        @Override
                        public void mouseExited(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            blackKeyOver = false;
                            node.setDrawStroke(new BasicStroke(strokeSize));
                            node.setDrawPaint(keyStrokeColor);
                        }

                        @Override
                        public void mousePressed(MouseEvent e, SGNode n) {
                            if (!overSharpKey) {
                                FXShape node = (FXShape) n;
                                node.setDrawPaint(keyHitFillColor);
                            }
                        }

                        @Override
                        public void mouseReleased(MouseEvent e, SGNode n) {
                            FXShape node = (FXShape) n;
                            node.setDrawPaint(keyFillColor);
                        }
                    });
                }
            }
        }
        SGTransform translateB = SGTransform.createTranslation(1, 3, bGroup);
        SGTransform translateW = SGTransform.createTranslation(1, 3, wGroup);
        this.addComponent(translateW);
        this.addComponent(translateB);
    }

    void createSequencer() {

        SGGroup seqGroup = new SGGroup();
        keyStep = new NoteSeqStep[(numOctaves * 12) * (numSteps)];
        stepVelocity = new Float[(numOctaves * 12) * (numSteps)];
        double stepHeight = (this.getHeight() - 4) / (12 * (numOctaves - 1));
        double stepWidth = (this.getWidth() - 1) / (numSteps + 2);

        for (int i = 0; i < numSteps; i++) {
            for (int k = 0; k < numOctaves - 1; k++) {
                for (int j = 0; j < 12; j++) {
                    stepVelocity[j + (k * 12)] = 0f;
                    keyStep[j + (k * 12)] = new NoteSeqStep(stepWidth - 1, stepHeight - stepHeight / 8);
                    keyStep[j + (k * 12)].setTranslateX((float) (this.getX() + 2 + (i * stepWidth) + ((this.getWidth() / numSteps + 2) * 2) - stepWidth / 2));
                    keyStep[j + (k * 12)].setTranslateY((float) ((((k * 12) + j) * stepHeight) + this.getY()));

                    if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10) {
                        keyStep[j + (k * 12)].setFillPaint(Color.darkGray);
                    } else {
                        keyStep[j + (k * 12)].setFillPaint(Color.lightGray);
                    }
                    seqGroup.add(keyStep[j + (k * 12)].callStep());
                }
            }
        }
        SGTransform translateS = SGTransform.createTranslation(1, 3, seqGroup);
        this.addComponent(translateS);
    }

    public void nodeOver() {
    }

    public void nodeExited() {
    }

    public void setCount(float countNumber) {
        for (int i = 0; i < numOctaves * 12; i++) {
            keyStep[(int) ((countNumber * 12) + i)].setDrawPaint(keyStrokeColor);
        }
    }

    public void setClearCount(float countNumber) {
        for (int i = 0; i < numOctaves * 12; i++) {
            keyStep[(int) ((countNumber * 12) + i)].setDrawPaint(stepCountColor);
        }
    }

    public void update() {
    }

    private class NoteSeqStep {

        float x;
        float y;
        float w;
        float h;
        float r;
        float defaultVelocity = 100;
        float velocity = 0;
        int counter = 0;
        Color keyStrokeColor = Color.gray;
        boolean isAlive;
        Color fillColor;
        FXShape step = new FXShape();

        NoteSeqStep(double tw, double th) {
            step.setShape(new Rectangle2D.Double(0, 0, tw, th));
            step.setMode(SGShape.Mode.STROKE_FILL);
            step.setDrawStroke(new BasicStroke(0.5f));
            step.setFillPaint(Color.lightGray);
            step.setDrawPaint(Color.GRAY);
            step.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
            step.addMouseListener(new SGMouseAdapter() {

                Point pos;

                @Override
                public void mouseEntered(MouseEvent e, SGNode n) {
                    FXShape node = (FXShape) n;
                    node.setDrawStroke(new BasicStroke(1.5f));
                    node.setFillPaint(new Color(220, 220, 220, 255));
                }

                @Override
                public void mouseExited(MouseEvent e, SGNode n) {
                    if (counter != 1) {
                        FXShape node = (FXShape) n;
                        node.setDrawStroke(new BasicStroke(0.5f));
                        node.setFillPaint(fillColor);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e, SGNode n) {
                    pos = e.getPoint();
                    hitStep(n);
                }
            });
        }

        void hitStep(SGNode n) {
            FXShape node = (FXShape) n;
            if (counter % 2 == 0) {
                node.setDrawPaint(Color.orange);
                node.setFillPaint(new Color(200, 240, 255));
            } else if (counter % 2 == 1) {
                node.setFillPaint(Color.GRAY);
            }
            counter++;
        }

        void setTranslateX(float tx) {
            step.setTranslateX(tx);
        }

        void setTranslateY(float ty) {
            step.setTranslateY(ty);
        }

        void setFillPaint(Color c) {
            fillColor = c;
            step.setFillPaint(c);
        }

        void setDrawPaint(Color c) {
            step.setDrawPaint(c);
        }

        SGNode callStep() {
            return step;
        }
    }
}
