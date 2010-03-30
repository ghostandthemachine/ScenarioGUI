/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scenariogui;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author jon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame();
        MasterPanel master = new MasterPanel();
        frame.add(master);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Scenario interface tests");
    }





}
