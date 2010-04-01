/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariogui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 *
 * @author Jon
 */
class JonLabel extends JButton {

    private String text;
    private Color accentColor = new Color(200);
    private Color textColor = new Color(15, 15, 15);
    private float offset = 1;

    public JonLabel(String s) {
        super(s);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float x = 2;
        float y = getHeight() - ((getHeight() - g2.getFontMetrics().getHeight()) / 2) + offset;

        g2.drawRect((int)x - 3, (int)y - 3, this.getWidth() + 6, this.getHeight() + 4);

        g2.setColor(accentColor);
        g2.drawString(text, x, y);

        y -= offset;
        g2.setColor(textColor);

        g2.drawString(text, x, y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(super.getMinimumSize().width, 60);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 200);
    }
}
