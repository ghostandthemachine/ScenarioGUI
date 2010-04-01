/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui.ui;

import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGTransform;
import javax.swing.JComboBox;

/**
 *
 * @author Jon
 */
public class TrackModule extends GUIComponent {

    SGGroup group = new SGGroup();
    SGComponent comp = new SGComponent();
    SGTransform.Translate translation = SGTransform.createTranslation(0, 0, group);
    SimpleDial dial1;
    SimpleDial dial2;
    SimpleDial dial3;
    SimpleDial dial4;


    public TrackModule(double tx, double ty) {
        super(tx, ty, 175, 80);

        this.addComponent(translation);

        translation.setTranslation(tx + 8, ty + 8);

        String[] boner = {"this", "is a", "fucking", "test"};
        JComboBox combo = new JComboBox(boner);
        combo.setSize(500, 50);
        //  combo.setFont(new Font("Helvitica", 12, Font.PLAIN));
        combo.setLocation((int) tx, (int) ty);

        comp.setComponent(combo);

        dial1 = new SimpleDial("shit", 80, 0, 20, this);

        group.add(dial1.getComponentGroup());
        
        group.add(comp);


    }
}
