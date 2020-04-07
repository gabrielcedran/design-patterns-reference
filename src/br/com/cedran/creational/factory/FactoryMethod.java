package br.com.cedran.creational.factory;

/**
 * Object creation logic can get very complicated and handling all the possible scenarios using constructors
 * is not handy because constructor by itself is not very descriptive (its name must be the same as the containing type,
 * what is not very helpful for the clients, specially when many of them are defined in the same class).
 *
 * Overloading constructors also has its limitation since its name is strictly linked to the containing type.
 * Imagine constructors for all potential scenarios for the creations of objects to represent cartesian or polo coordinates.
 *
 * Handling all the creation scenarios through constructors could lead into the so called "overloading hell",
 * with lots and lots of constructors to cover the business logic needed and many constructors calling each other.
 * Even for the client of that class is difficult to figure out which constructor is the most suitable for his need.
 *
 * Suppose the following scenario:
 * One class named Point stores coordinates in cartesian system, but it is also necessary to support the class initialization
 * in polar coordinates:
 * class Point {
 *     double x, y;
 *     public Point(double x, double y) {
 *         this.x = x;
 *         this.y = y;
 *     }
 *
 *     public Point(double rho, double theta) {
 *         this.x = rho * Math.cos(theta);
 *         this.y = rho * Math.sin(theta);
 *     }
 * }
 * Due to Java limitation, the previous implementation is not supported and a potential solution could merge both constructor
 * and add an extra parameter to notify which type of coordinates is being provided. The problem with this solution is
 * that logic is being introduced in a place which is not meant to have any logic and it is making things more complicated
 * to the clients. FactoryMethod pattern comes in rescue:
 * (Factory methods are nothing more than factories within the class in order to create the objects. It provides the flexibility
 * of naming methods differently, thing that constructors couldn't. Generally, the constructors are changes to private).
 */
public class FactoryMethod {

    public static void main(String[] args) {
        Point polarPoint = Point.newPolarPoint(10, 12);
    }
}

class Point {
    private double x, y;

    /**
     * The constructor is private to force the client to use the factory methods.
     */
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point newCartesianPoint(double x, double y) {
        return new Point(x, y);
    }

    public static Point newPolarPoint(double rho, double theta) {
        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
    }

    /**
     * When a class has many factories, it might make sense to group those factories together in a external place (or class :P).
     * So the Factory pattern is not much more than that. There are only two things to take into consideration:
     * 1. In order to keep the Object constructor private, the factory class has to be inside the object class, to that
     * it has visibility of the private constructor.
     * 2. To place the factory class in an external file (class), it is necessary to turn the constructor public again and
     * it opens the possibility for the clients to instantiate the objects directly by themselves.
     */
    public static class Factory {

        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }

}