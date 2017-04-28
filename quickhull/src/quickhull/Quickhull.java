/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meija
 */
package quickhull;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Quickhull {
    private List<Point> points;
    private List<Point> convexHull;
    private List<Point> leftDivision;
    private List<Point> rightDivision;
    private Point leftpoint;
    private Point rightpoint;

    public Quickhull(List<Point> points) {
        this.points = points;
    }
    
    protected Point getPointOnRight() {
        Point rightpoint = this.points.get(0);
        for (Point point : this.points) {
            if(point.getX() > rightpoint.getX()){
                rightpoint = point;
            }
        }
        return rightpoint;
    }
    protected Point getPointOnLeft() {
        Point leftpoint = this.points.get(0);
        for (Point point : this.points) {
            if(point.getX() < leftpoint.getX()){
                leftpoint = point;
            }
        }
        return leftpoint;
    }
    protected boolean isLeftOfLine(Point point, Point from, Point to) {
        double crossProduct = (to.x - from.x) * (point.y - from.y) - (to.y - from.y) * (point.x - from.x);
        return Double.compare(crossProduct, 0) > 0;
    }
    protected double getDistance(Point point, Point to) {
        return Math.sqrt((point.x - to.x) * (point.x - to.x) + (point.y - to.y) * (point.y - to.y));
    }
    protected double getDistanceToLine(Point point, Point from, Point to) {
        return Math.abs((to.getX() - from.getX()) * (from.getY() - point.getY()) - (from.getX() - point.getX()) * (to.getY() - from.getY()) 
                / Math.sqrt(Math.pow(to.getX() - from.getX(), 2) + Math.pow(to.getY() - from.getY(), 2)));
    }
    protected void divideInTwo() {
        this.setMaxPoints();
        this.leftDivision = new LinkedList<>();
        this.rightDivision = new LinkedList<>();
        for(Point point : this.points) {
            if(point.equals(this.rightpoint) || point.equals(this.leftpoint)) {
                continue;
            }
            if(isLeftOfLine(point, this.leftpoint, this.rightpoint)) {
                leftDivision.add(point);
            } else {
                rightDivision.add(point);
            }
        }
    }
    protected List getLeftDivision() {
        return this.leftDivision;
    }
    protected List getRightDivision() {
        return this.rightDivision;
    }
    protected void setMaxPoints() {
        this.leftpoint = getPointOnLeft();
        this.rightpoint = getPointOnRight();    
    }
    protected List<Point> getConvexHull() {
        List<Point> convexHull = new ArrayList<>();
        this.divideInTwo();
        //extreme values are always part of the convex hull
        convexHull.add(this.leftpoint);
        List<Point> hull = divide(this.leftDivision, leftpoint, rightpoint);
        convexHull.addAll(hull);
        convexHull.add(rightpoint);
        
        hull = divide(this.rightDivision, this.rightpoint, this.leftpoint);
        convexHull.addAll(hull);
        
        return convexHull;
    }
    protected List<Point> divide(List<Point> points, Point p1, Point p2) {
        List<Point> hull = new ArrayList<>();
        List<Point> tmpPoints;
        List<Point> l1 = new LinkedList<>();
        List<Point> l2 = new LinkedList<>();
            
        if(points.isEmpty()) {
            return hull;
        } else if (points.size() == 1) {
            hull.add(points.get(0));
            return hull;
        }
        Point furthestPoint = getFurthestPoint(points);
        points.remove(furthestPoint);
        
        for(Point point : points) {
            if(this.isLeftOfLine(point, p1, furthestPoint)) {
                l1.add(point);
            } else if (this.isLeftOfLine(point, furthestPoint, p2)) {
                l2.add(point);
            }
        }
        points.clear();
        
        List<Point> hullDivision = divide(l1, p1, furthestPoint);
        hull.addAll(hullDivision);
        hull.add(furthestPoint);
        hullDivision = divide(l2, furthestPoint, p2);
        hull.addAll(hullDivision);
        
        return hull;
    }
    protected Point getFurthestPoint(List<Point> list) {
        Point furthestPoint = list.get(0);
        double distance = 0.0;
        for (Point point : list) {
            double tmpDistance = this.getDistanceToLine(point, this.leftpoint, this.rightpoint);
            if(tmpDistance > distance) {
                distance = tmpDistance;
                furthestPoint = point;
            }
        }
        return furthestPoint;
    }
}
