/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui.notesequencer;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 *
 * @author Jon
 */
public class NoteScene extends JSGPanel {

    private SGGroup group = new SGGroup();

    public NoteScene() {


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(0, 0);
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(450, 110));


        JButton butt = new JButton();
        butt.setSize(40,40);

        SGComponent comp = new SGComponent();
        comp.setComponent(butt);
        comp.setSize(200,200);


        group.add(comp);
    }

    public SGGroup getGroup() {
        return group;
    }
}
