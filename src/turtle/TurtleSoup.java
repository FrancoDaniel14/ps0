/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.color(PenColor.GREEN);
        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.color(PenColor.GREEN);
        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.color(PenColor.GREEN);
        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.color(PenColor.GREEN);
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if(sides > 2){
            return ((sides - 2.0) * 180.0) / sides;
        }else{
            return -1.0;
        }
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        Long sides = Math.round(360 / (180 - angle));
        if(angle > 0 && angle < 180){
            return sides.intValue();
        }else{
            return -1;
        }
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        int i = 0;
        while(i < sides){
            turtle.color(PenColor.RED);
            turtle.forward(sideLength);
            turtle.turn(-180 - calculateRegularPolygonAngle(sides));
            i++;
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double changeInX = targetX - currentX;
        double changeInY = targetY - currentY;
        double theta = Math.toDegrees(Math.atan2(changeInY, changeInX));
                
        double rotatedAngle = 90 - (theta + currentHeading);
        
        while(rotatedAngle < 0){
            rotatedAngle += 360;
        }
        
        return rotatedAngle;        
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        
        double currentHeading = 0.0;
        List<Double> headingAdjustments = new ArrayList<Double>();
        
        for(int i = 0; i < xCoords.size() - 1; i++){
            headingAdjustments.add(calculateHeadingToPoint(currentHeading, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1)));
            currentHeading = headingAdjustments.get(i); 
        }
                
        return headingAdjustments;
    }

    /**
     * Given a determinate number for quantity of points and a radius value.
     * Generates X and Y points from an equation.
     *   
     * @param xPointsQuantity quantity of generated points
     * @param radius value of radius
     * @return a bi-dimensional Double array of generated X and Y points
     */
    
    public static Double[][] generateGraphicFromFunction(int xPointsQuantity, double radius){
        Double[][] xyPoints = new Double[xPointsQuantity][2];
        //From (x - 0)2 + (y - 0)2 = r2
        double x = 0.0;
        
        for(int i = 0; i < xyPoints.length; i++){
            xyPoints[i][0] = x;
            //y = f(x) = sqrt(r2 - x2)
            xyPoints[i][1] = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(x, 2.0));
            x += 1.0;             
        }
        
        return xyPoints;
    }
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     * @param xPointsQuantity quantity of generated points
     * @param radius value of radius
     */
    public static void drawPersonalArt(Turtle turtle, int xPointsQuantity, double radius) {
        List<Integer> xCoords = new ArrayList<Integer>();               
        List<Integer> yCoords = new ArrayList<Integer>();
        for(int i = 0; i < xPointsQuantity; i++){
            xCoords.add(generateGraphicFromFunction(xPointsQuantity, radius)[i][0].intValue());            
            yCoords.add(generateGraphicFromFunction(xPointsQuantity, radius)[i][1].intValue());            
        }
                       
        int sideLength = 100;
        for(int i = 0; i < xCoords.size() - 1; i++){
            turtle.color(PenColor.ORANGE);
            turtle.forward(sideLength);
            turtle.turn(calculateHeadings(xCoords, yCoords).get(i));
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 70);
        drawRegularPolygon(turtle, 100, 5);        
        /*calculateHeadingToPoint(30.0, 0, 1, 0, 0);
        List<Integer> xCoords = new ArrayList<Integer>();
        xCoords.add(0); xCoords.add(1); xCoords.add(1);        
        List<Integer> yCoords = new ArrayList<Integer>();
        yCoords.add(0); yCoords.add(1); yCoords.add(2);        
        calculateHeadings(xCoords, yCoords);        
        //generateGraphicFromFunction(20);*/
        drawPersonalArt(turtle, 9, 100.0);
        // draw the window
        turtle.draw();
    }
}