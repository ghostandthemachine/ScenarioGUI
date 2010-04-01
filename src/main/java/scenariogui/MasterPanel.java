/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.Timer;
import scenariogui.synth.SynthControl;
import scenariogui.synth.TestSynth;
import scenariogui.ui.TrackModule;
import scenariogui.ui.notesequencer.NoteScene;
import scenariogui.ui.stepsequencer.AdvancedStepSequencer;
import scenariogui.ui.notesequencer.NoteSequencer;

/**
 *
 * @author jon
 */
public class MasterPanel extends JSGPanel {

    SGGroup root = new SGGroup();
    public static double mouseX;
    public static double mouseY;
    public static double lastX;
    public static double lastY;
    public static double xVel;
    public static double yVel;
    public static boolean mousePressed = false;
    public static boolean mouseDragged = false;
    private SGShape listenerShape = new SGShape();
    private Timer timer;
    private float speed = (float) ((1.0 / 120.0) * 60000.0);
    private int counter;

    public MasterPanel() {

        AdvancedStepSequencer ass = new AdvancedStepSequencer(50, 50, 16, 8);
        createSceneMouseListener();
        ass.setBpm(4 * 120f);

        NoteSequencer ns = new NoteSequencer(200d, 200d, 200d, 100d, 2, 16, this);

//        TrackModule box = new TrackModule(10, 10);
//        root.add(box.getComponentGroup());

        SynthControl sc = new SynthControl(new TestSynth());
        root.add(sc);


        String[] strings = {"fuck this", "fuck this", "fuck this", "fuck this", "fuck this"};
        TrackModule track = new TrackModule(50,50);
       // root.add(track.getComponentGroup());
        
        NoteScene noteS = new NoteScene();

      //  root.add(ns.getComponentGroup());


        root.add(listenerShape);
        //root.add(ass.getComponentGroup());


        setScene(root);
        setBackground(Color.white);
    }

    public void createSceneMouseListener() {

        listenerShape.setShape(new Rectangle2D.Double(0, 0, 2000, 2000));
        listenerShape.setFillPaint(new Color(0, 0, 0, 0));
        listenerShape.addMouseListener(new SGMouseListener() {

            public void mouseClicked(MouseEvent me, SGNode sgnode) {
            }

            public void mousePressed(MouseEvent me, SGNode sgnode) {
                mousePressed = true;
            }

            public void mouseReleased(MouseEvent me, SGNode sgnode) {
                mousePressed = false;
                mouseDragged = false;
            }

            public void mouseEntered(MouseEvent me, SGNode sgnode) {
            }

            public void mouseExited(MouseEvent me, SGNode sgnode) {
            }

            public void mouseDragged(MouseEvent me, SGNode sgnode) {
                mouseDragged = true;
            }

            public void mouseMoved(MouseEvent me, SGNode sgnode) {
                lastX = mouseX;
                lastY = mouseY;

                xVel = lastX - mouseX;
                yVel = lastY - mouseY;

                mouseX = me.getPoint().getX();
                mouseY = me.getPoint().getY();
            }

            public void mouseWheelMoved(MouseWheelEvent mwe, SGNode sgnode) {
            }
        });


    }

    public static double getLastX() {
        return lastX;


    }

    public static double getLastY() {
        return lastY;


    }

    public static double getMouseX() {
        return mouseX;


    }

    public static double getMouseY() {
        return mouseY;


    }

    public SGGroup getRoot() {
        return root;


    }

    public static double getxVel() {
        return xVel;


    }

    public static double getyVel() {
        return yVel;


    }

    public static boolean isMousePressed() {
        return mousePressed;


    }

    public static boolean isMouseDragged() {
        return mouseDragged;


    }

    public void actionPerformed(ActionEvent e) {
        //If still loading, can't animate.
    }
}
