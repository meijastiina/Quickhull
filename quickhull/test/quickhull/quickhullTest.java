/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickhull;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meija
 */
public class quickhullTest {
    
    public quickhullTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetPointOnRight() {
        Point a = new Point(3, 3);
        Point b = new Point(2, 2);
        Point c = new Point(4, 5);
        Quickhull quickhull = new Quickhull(Arrays.asList(a, b, c));
        Point retVal = quickhull.getPointOnRight();
        Point expectedVal = c;
        assertEquals(expectedVal, retVal);
    }
    @Test
    public void testGetPointOnLeft() {
        Point a = new Point(3, 3);
        Point b = new Point(2, 2);
        Point c = new Point(4, 5);
        Quickhull quickhull = new Quickhull(Arrays.asList(a, b, c));
        Point retVal = quickhull.getPointOnLeft();
        Point expectedVal = b;
        assertEquals(expectedVal, retVal);
    }
    @Test
    public void testDivideInTwoLeftDivision() {
        /**
         * 9                  h
         * 8           e
         * 7       g
         * 6             f
         * 5       c
         * 4
         * 3     a
         * 2   b         d
         * 1
         * 0 1 2 3 4 5 6 7 8 9 
         */
        Point a = new Point(3, 3);
        Point b = new Point(2, 2);
        Point c = new Point(4, 5);
        Point d = new Point(7, 2);
        Point e = new Point(6, 8);
        Point f = new Point(7, 6);
        Point g = new Point(4, 7);
        Point h = new Point(9, 9);
        Quickhull quickhull = new Quickhull(Arrays.asList(a, b, c, d, e, f, g, h));
        quickhull.divideInTwo();
        List retVal = quickhull.getLeftDivision();
        List expectedVal = Arrays.asList(c, e, g);
        assertEquals(expectedVal, retVal);
        
    }
    @Test
    public void testDivideInTwoRightDivision() {
        /**
         * 9                  h
         * 8           e
         * 7       g
         * 6             f
         * 5       c
         * 4
         * 3     a
         * 2   b         d
         * 1
         * 0 1 2 3 4 5 6 7 8 9 
         */
        Point a = new Point(3, 3);
        Point b = new Point(2, 2);
        Point c = new Point(4, 5);
        Point d = new Point(7, 2);
        Point e = new Point(6, 8);
        Point f = new Point(7, 6);
        Point g = new Point(4, 7);
        Point h = new Point(9, 9);
        Quickhull quickhull = new Quickhull(Arrays.asList(a, b, c, d, e, f, g, h));
        quickhull.divideInTwo();
        List retVal = quickhull.getRightDivision();
        List expectedVal = Arrays.asList(a, d, f);
        assertEquals(expectedVal, retVal);
        
    }
    @Test
    public void testGetConvexHull() {
        /*
            6 |       d
            5 | b       g
            4 |   a   e   i
            3 |       c   h
            2 |       
            1 | f
            0 '------------
              0 1 2 3 4 5 6
        */
        Point a = new Point(2, 4);
        Point b = new Point(1, 5);
        Point c = new Point(4, 3);
        Point d = new Point(4, 6);
        Point e = new Point(4, 4);
        Point f = new Point(1, 1);
        Point g = new Point(5, 5);
        Point h = new Point(6, 3);
        Point i = new Point(6, 4);
        Quickhull hull = new Quickhull(Arrays.asList(a, b, c, d, e, f, g, h, i));
        
        List<Point> retVal = hull.getConvexHull();
        List<Point> expectedVal = Arrays.asList(b, d, i, h, f);
        assertEquals(expectedVal, retVal);
    }
    @Test
    public void testGetConvexHullWithCollinearPoints() {
        /*
            6 |       d
            5 |     b   g
            4 |   a   e   i
            3 |     c   h
            2 |       f
            1 |
            0 '------------
              0 1 2 3 4 5 6
        */
        Point a = new Point(2, 4);
        Point b = new Point(3, 5);
        Point c = new Point(3, 3);
        Point d = new Point(4, 6);
        Point e = new Point(4, 4);
        Point f = new Point(4, 2);
        Point g = new Point(5, 5);
        Point h = new Point(5, 3);
        Point i = new Point(6, 4);
        Quickhull hull = new Quickhull(Arrays.asList(a, b, c, d, e, f, g, h, i));
        
        List<Point> retVal = hull.getConvexHull();
        List<Point> expectedVal = Arrays.asList(a, d, i, f);
        assertEquals(expectedVal, retVal);
    }
    
    @Test
    public void testGetConvexHullNotEnoughPoints() {
        Point a = new Point(3, 3);
        Point b = new Point(2, 2);
        Quickhull hull = new Quickhull(Arrays.asList(a, b));
        
        try {
            hull.getConvexHull();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException IllegalArgumentException) {
            assertEquals("Convex hull calculation requires at least 3 unique points", IllegalArgumentException.getMessage());
        }
    }
    @Test
    public void testGetConvexHullAllCollinear() {
        /**
         * 9
         * 8 
         * 7
         * 6
         * 5         d
         * 4       c
         * 3     b
         * 2   a
         * 1
         * 0 1 2 3 4 5 6 7 8 9
         * 
         */
        Point a = new Point(2, 2);
        Point b = new Point(3, 3);
        Point c = new Point(4, 4);
        Point d = new Point(5, 5);
        Quickhull hull = new Quickhull(Arrays.asList(a, b, c, d));
        try {
            hull.getConvexHull();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException IllegalArgumentException) {
            assertEquals("Convex hull calculation requires at least one point not to be collinear", IllegalArgumentException.getMessage());
        }
    }

}
