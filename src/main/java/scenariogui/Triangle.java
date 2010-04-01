package scenariogui;

import java.awt.*;
import java.awt.geom.*;


/*
 * Class to represent a Triangle.
 * Implementing the java.awt.Shape inteface.
 */
public class Triangle implements Shape {

    private Polygon poly;

    public Triangle(Polygon p) {
        poly = p;
    }

    public Triangle(Point p1, Point p2, Point p3) {
        poly = new Polygon();
        poly.addPoint(p1.x, p1.y);
        poly.addPoint(p2.x, p2.y);
        poly.addPoint(p3.x, p3.y);
    }


    public void draw(Graphics g) {
        g.drawPolygon(poly);
    }

    public void fill(Graphics g) {
        g.fillPolygon(poly);
    }

    //methods implemented from interface Shape
    public Rectangle getBounds() {
        return poly.getBounds();
    }

    public Rectangle2D getBounds2D() {
        return poly.getBounds2D();
    }

    public boolean contains(double x, double y) {
        return poly.contains(x, y);
    }

    public boolean contains(Point2D p) {
        return poly.contains(p);
    }

    public boolean intersects(double x, double y, double w, double h) {
        return poly.intersects(x, y, w, h);
    }

    public boolean intersects(Rectangle2D r) {
        return poly.intersects(r);
    }

    public boolean contains(double x, double y, double w, double h) {
        return poly.contains(x, y, w, h);
    }

    public boolean contains(Rectangle2D r) {
        return poly.intersects(r);
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return poly.getPathIterator(at);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return poly.getPathIterator(at, flatness);
    }
}
