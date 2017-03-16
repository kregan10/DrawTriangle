package q2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <p> This code draws an equilateral triangle.  The center of
 * the triangle is the first mouseclick, and as the mouse is dragged,
 * the top part of the triangle is defined where the mouse is released.</p>
 * 
 * @author Kerry Regan
 * @version 1.0
 */
public class DrawTriangle extends JFrame {

    /**
     *Sets up pafram for drawing triangle
     */
    public DrawTriangle() {
        super("Kerry Regan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new DrawTrianglePanel());
        setSize(400, 400);
        setVisible(true);
    }

    /**
     *<p>Sets up panel for drawing the triangle, implements the MouseListener
     *Class.</p>
     */
    private class DrawTrianglePanel extends JPanel implements MouseListener,
            MouseMotionListener {
        
        /**
         * <p>Sets up the mouselisteners to the DrawTrianglePanel.</p>
         */
        public DrawTrianglePanel() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        
        /*
         * Initializing mouse position variables, that are 
         * to be determined by mouseclicks, releases and drags.
         */
        private int centerX;
        private int centerY;
        private int cornerX;
        private int cornerY;
        
        /**
         * <p>Returns the total height of the triangle</p>
         * @return 2 * Math.sqrt(sideASquared + sideBSquared)
         */
        public double height() {
            final double deltaX = centerX - cornerX;
            final double deltaY = centerY - cornerY;
            final double sideASquared = Math.pow(deltaX, 2);
            final double sideBSquared = Math.pow(deltaY, 2);

            return 2 * Math.sqrt(sideASquared + sideBSquared);
        }

        /**
         * <p>Returns the side length of the equilateral triangle using
         * half of the 60 degree angle at the corners.</p>
         * @return height() / Math.cos(Math.toRadians(30))
         */
        public double sideLength() {
            return height() 
                    / Math.cos(Math.toRadians(30)); 
        }
        
        /**
         * <p>Returns the side length of the equilateral triangle.</p>
         * @param centerPoint
         * @param cornerPoint
         * @return points
         */
        private ArrayList<Point> getPointsForTriangle(
                Point centerPoint, Point cornerPoint) {
            
            ArrayList<Point> points = new ArrayList<Point>();
            /*
             * <p>Declaring instance objects for each corner of triangle</p>
             */
            Point firstPoint = cornerPoint;
            Point secondPoint = new Point();
            Point thirdPoint = new Point();
            
            /*
             * <p>calculating sideLength using pythagorus</p>
             */
            final double deltaY = 
                    centerPoint.y - cornerPoint.y;
            final double deltaX = 
                    centerPoint.x - cornerPoint.x;
            final double sideLength = 
                    sideLength();
            /*
             * setting center basepoint from mouse click
             */
            Point basePoint = new Point((int) (centerPoint.x + deltaX), 
                    (int) (centerPoint.y + deltaY));
            /*
             * Handling special case where deltaX = 0 
             */
            if (deltaX == 0) {
                
                secondPoint.y = basePoint.y;
                secondPoint.x = (int) (basePoint.x + sideLength / 2);
                
                thirdPoint.y = basePoint.y;
                thirdPoint.x = (int) (basePoint.x - sideLength / 2);
            }
            else {
                final double slope = deltaY / deltaX;
                final double angleAlphaRadians = Math.atan(slope);
                final double angleThetaRadians = 
                        (Math.PI / 2 - angleAlphaRadians);
                final double xOffSet = (sideLength / 2) 
                        * Math.cos(angleThetaRadians);
                secondPoint.x = (int) (basePoint.x + xOffSet);
                thirdPoint.x = (int) (basePoint.x - xOffSet);
                
                final double yOffSet = (sideLength / 2) 
                        * Math.sin(angleThetaRadians);
                secondPoint.y = (int) (basePoint.y - yOffSet);
                thirdPoint.y = (int) (basePoint.y + yOffSet);
            }

            points.add(firstPoint);
            points.add(secondPoint);
            points.add(thirdPoint);
            
            return points;
        }


        /**
         *<p>Gets points from getPointsForTriangle, and draws to frame.</p>
         *@param Graphics g
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.YELLOW);
            

            ArrayList<Point> points = getPointsForTriangle(new 
                    Point(centerX, centerY), new Point(cornerX, cornerY));
            
            for(int i = 0; i < points.size(); i++)
            {
                Point thisPoint = points.get(i);
                Point nextPoint = points.get((i + 1) % points.size());
                g.drawLine(thisPoint.x, thisPoint.y, nextPoint.x, nextPoint.y);
            }
            repaint();
        }
        
        /**
         *<p>sets cetner of triangle coordinates whenever mouse is pressed.</p>
         *@param MouseEvent e
         */
        public void mousePressed(MouseEvent e) {
            centerX = e.getX();
            centerY = e.getY();
            cornerX = e.getX();
            cornerY = e.getY();
        }
        /**
         *<p>Sets the corner of the lead corner when mouse is released.</p>
         *@param MouseEvent e
         */
        public void mouseReleased(MouseEvent e) {
            cornerX = e.getX();
            cornerY = e.getY();
            repaint();
            System.out.println("Mouse released called");
        }
        /**
         *<p>Updates lead corner point as mouse is dragged.</p>
         *@param e
         */
        public void mouseDragged(MouseEvent e) {
            cornerX = e.getX();
            cornerY = e.getY();
            
            repaint();
        }
        /**
         * <p></p>
         *@param e
         */
        public void mouseEntered(MouseEvent e) {
        }
        /**
         * <p></p>
         *@param e
         */
        public void mouseExited(MouseEvent e) {
        }
        /**
         * <p></p>
         *@param e
         */
        public void mouseClicked(MouseEvent e) {
        }
        /**
         * <p></p>
         *@param e
         */
        public void mouseMoved(MouseEvent e) {
        }

    }

    /**
     *Main method, calls DrawTriangle
     */
    public static void main(String[] args) {
        new DrawTriangle();
    }

};
