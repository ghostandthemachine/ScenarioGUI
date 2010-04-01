/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

/**
 *
 * @author Jon
 */
public class Lab extends JLabel {

    String label;
    int offset = 1;
    private Color accentColor = new Color(128, 255, 255);
    private Color textColor = new Color(15, 15, 15);

    public Lab(String s) {
        super(s);

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float x = 2;
        float y = getHeight() - ((getHeight() - g2.getFontMetrics().getHeight()) / 2) + offset;

        g2.setColor(accentColor);
        g2.drawString(this.getText(), x, y);

        y -= offset;
        g2.setColor(textColor);

        g2.drawRoundRect((int)x - 3,(int) y - 3,30, 100, 6, 6);

        g2.drawString(this.getText(), x, y);
    }
}
