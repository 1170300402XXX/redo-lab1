/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {      
        for(int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90.0);
        }      
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
        
        double insidesAngles = (double)((sides-2)*180) / sides ;
        return insidesAngles;        
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
        
        double side = 180*2/(180-angle);
        return (int) Math.round(side);  //math.round四捨五入函數
        
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
        
        for(int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180.0-calculateRegularPolygonAngle(sides));
        }

        //throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        
        double hypotenuse = Math.sqrt( (double)( (currentX - targetX)*(currentX - targetX) 
                                                + (currentY - targetY)*(currentY - targetY) ) );//斜邊
       // System.out.println("斜邊"+hypotenuse);//********             
        if (hypotenuse == 0) {
            return 0.0;
        }
        double tempAngle = Math.asin(( ((double)(targetY - currentY)) /hypotenuse))*(180.0/Math.PI);//(-90  --  +90)            
        //System.out.println("反三角"+tempAngle);//********               
        double targetBearing = 0;      
        if (targetX >= currentX ) { 
                targetBearing = 90.0 - tempAngle ;   
        }else {
            targetBearing = 270.0 + tempAngle ;
        }        
      //  System.out.println("總夾角"+targetBearing);//****************             
        if (targetBearing >= currentBearing) {
            return targetBearing - currentBearing;
        }else {
            return 360.0-(currentBearing - targetBearing);
        }
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        
        List<Double> list = new ArrayList<Double>();
        double bearing = 0.0;
        
        for(int i = 0; i < xCoords.size()-1 ; i++) {
            bearing = calculateBearingToPoint(bearing, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
            list.add(bearing);  
        }
        return list;
    }
    
    /**
     * 自写函数
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double doubleCalculateBearingToPoint(double currentBearing, double currentX, double currentY,
                                                 double targetX, double targetY) {
        
        double hypotenuse = Math.sqrt(  ( (currentX - targetX)*(currentX - targetX) 
                                                + (currentY - targetY)*(currentY - targetY) ) );//斜邊
       // System.out.println("斜邊"+hypotenuse);//********             
        if (hypotenuse == 0) {
            return 0.0;
        }
        double tempAngle = Math.asin(( ((targetY - currentY)) /hypotenuse))*(180.0/Math.PI);//(-90  --  +90)            
        //System.out.println("反三角"+tempAngle);//********               
        double targetBearing = 0;      
        if (targetX >= currentX ) { 
                targetBearing = 90.0 - tempAngle ;   
        }else {
            targetBearing = 270.0 + tempAngle ;
        }        
      //  System.out.println("總夾角"+targetBearing);//****************             
        if (targetBearing >= currentBearing) {
            return targetBearing - currentBearing;
        }else {
            return 360.0-(currentBearing - targetBearing);
        }
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        
        if (points.size()<3) {           
            return new HashSet<Point>(points);   // 0、1、2個點的情況
        }      
        //*****************
        //System.out.println("最初点："+points);
        //*****************       
        Point aPoint ;
        Point highestPoint = new Point(0.0, -999999999.0);//初始值設為一個非常大的值
        Iterator<Point> iterator = points.iterator();
        while(iterator.hasNext()) {    //找最高点，若有多個最高点，找最右的
            aPoint = iterator.next();
            if ( aPoint.y() >= highestPoint.y() ) {
                if ( aPoint.y() > highestPoint.y() ) {
                    highestPoint = aPoint;
                }else {
                    if ( aPoint.x() > highestPoint.x() ) {
                        highestPoint = aPoint;  //y相等時，若x更大，則替換
                    }
                }     
            }
        }
        List< Point> pointList = new ArrayList<Point>();
        for ( Point i : points ) {
            pointList.add(i);           
        }
        //*****************
        //System.out.println("最高点"+highestPoint);
        //*****************
        //*****************
        //System.out.println("转化为list"+pointList);
        //*****************
        
        ArrayList<Point> convexHullList = new ArrayList<Point>();  //储存凸包点
        Point currentPoint, nextPoint , smallextBearingPoint =null;   //当前点和待寻找的可能的下一个点,可能的最小旋转角度点
        double currentAngle = 0.0;      //当前点角度朝向
        double smallestBearing = 370.0;   //最小旋转角度,即由当前点朝向到下一个点的需要旋转的角度。初始设置为非常大的角度       
        double bearing; //遍历的每一个点计算的旋转角度
        
        currentPoint = highestPoint;
       // smallextBearingPoint = currentPoint;
        convexHullList.add(highestPoint);     
        
        while(true) {   
            smallestBearing = 370.0;  //******************
            /*
             * 问题：寻找凸包点的过程中，出现所找到的下一个点始终是当前点
             * 
             * 分析：理论上不应该是这样，因为for循环里面有一个if条件，就是判断所找到的点不能是当前点
             * 
             * 问题来源：smallestBearing一开始只在while循环外面初始化了一次370.0 ，但是实际上，应该是每次while循环内都应该重新初始化一次。
             * 
             *           因为每一个while循环意味着 遍历所有点，找当前点的最小角度点。而这个最小角度一开始的比较初始值值都应该是370.0
             * 
             * 
             * */          
            for(int i = 0; i < pointList.size(); i++) {
                if ( !currentPoint.equals(pointList.get(i)) ) {
                    nextPoint = pointList.get(i);    
                    bearing = 0.0;
                    bearing = doubleCalculateBearingToPoint(currentAngle, currentPoint.x(), currentPoint.y(), nextPoint.x(), nextPoint.y()) ;
                    if( bearing < smallestBearing) {
                        smallestBearing = bearing;
                        smallextBearingPoint = nextPoint ;
                    }
                }

            }           
            //*****************
            //System.out.println("当前点 "+currentPoint+"   找到的下一个点"+smallextBearingPoint);
            //*****************        
            if (smallextBearingPoint.equals(highestPoint)) {   //找到的最小旋转角的点是最高点，说明已经遍历一圈，说明寻找完毕。
                break;
            }
            convexHullList.add(smallextBearingPoint);
            currentPoint = smallextBearingPoint;            //更新当前点和当前朝向角度
            currentAngle = smallestBearing + currentAngle;
        }
        //*****************
        //System.out.println("暂时的凸包点"+convexHullList);
        //*****************
        
        //****寻找凸包点中的边上点
        //这个凸包list中是有顺序的，按照最高点，顺时针的顺序存入lsit    
        ArrayList<Point> a = new ArrayList<Point>();  //边上点集合
        for (int i = 0; i < convexHullList.size()-2; i++) {    //若三点在一条直线上。斜率相等，满足一个乘式

            double formula1 = ( convexHullList.get(i+2).x() - convexHullList.get(i).x() )
                                  *(convexHullList.get(i+1).y() - convexHullList.get(i).y());
            
            double formula2 = ( convexHullList.get(i+1).x() - convexHullList.get(i).x() )
                    *(convexHullList.get(i+2).y() - convexHullList.get(i).y());
            if ((formula1 - formula2) < 0.000001 && (formula2 - formula1) < 0.000001  ) {  //判断两个浮点数相等
                a.add( convexHullList.get(i+1) );  //说明三点共线，把中点记录
            }            
        }
        //因为list没有循环性，所以还未判断最高点和最后几点是否共线
        int size = convexHullList.size();
        if (size > 2) {
            double formula3 = ( highestPoint.x() - convexHullList.get(size-2).x() )
                               *( convexHullList.get(size-1).y() - convexHullList.get(size-2).y() );
            double formula4 = ( convexHullList.get(size-1).x() - convexHullList.get(size-2).x() )
                               *( highestPoint.y() - convexHullList.get(size-2).y());
            if ((formula3 - formula4) < 0.000001 && (formula4 - formula3) < 0.000001  ) {  //判断两个浮点数相等
               a.add( convexHullList.get(size-1));  //说明三点共线，把中点记录
            }
        }

        //*****************
        //System.out.println("边上点"+a.toString());
        //*****************
        
        //删除凸包点
        for(int i = 0; i < a.size(); i++) {
            convexHullList.remove(a.get(i));
        }       
        //*****************
        //System.out.println("最后的凸包点"+convexHullList);
        //*****************
        Set<Point> convexHullSet = new HashSet<>(convexHullList);        
        return convexHullSet;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        throw new RuntimeException("implement me!");
        

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

        //drawSquare(turtle, 40);
        drawRegularPolygon(turtle, 5, 40);

        // draw the window
        turtle.draw();
    }

}
