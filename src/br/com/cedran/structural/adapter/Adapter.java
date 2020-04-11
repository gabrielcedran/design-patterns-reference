package br.com.cedran.structural.adapter;

import java.util.*;
import java.util.function.Consumer;

/**
 * The idea of the adapter pattern is pretty much the same idea of the power adapters of the real world.
 * Given an interface X, it needs to be adapted to conform to interface Y.
 *
 * In this example, suppose one api returns VectorRectangles (which consists of four lines with their respective start and end points)
 * and the api to print to the screen only supports pixel form (set of points x and y coordinates) rather than in vector form (set of lines) - drawPoint method.
 *
 * The adapter in this case receives the lines which needs to be printed out and transforms them into points - essentially, adapts lines into points.
 *
 */
public class Adapter {
    private static final List<VectorObject> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle(1, 1, 10, 10),
            new VectorRectangle(3, 3, 6, 6)));

    public static void drawPoint(Point p) {
        System.out.println(".");
    }

    private static void draw() {
        for (VectorObject vo : vectorObjects) {
            for (Line line : vo) {
                LineToPointAdapterCache adapter = new LineToPointAdapterCache(line);
                adapter.forEach(Adapter::drawPoint);
            }
        }
    }

    public static void main(String[] args) {
        draw();
        draw();
    }
}

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class VectorObject extends ArrayList<Line> {

}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        add(new Line(new Point(x, y), new Point(x + width, y)));
        add(new Line(new Point(x, y), new Point(x, y + height)));
        add(new Line(new Point(x+width, y), new Point(x+width, y + height)));
        add(new Line(new Point(x, y+height), new Point(x+width, y + height)));
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(start, line.start) &&
                Objects.equals(end, line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}

class LineToPointAdapter extends ArrayList<Point> {

    private static int count = 0;

    public LineToPointAdapter(Line line) {
        System.out.println(String.format("[%s] Adapting Line start point(%s, %s) end point (%s, %s). [no caching]",
                ++count, line.start.x, line.start.y, line.end.x, line.end.y));
        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int bottom = Math.min(line.start.y, line.end.y);
        int top = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = top-bottom;
        if (dx == 0) {
            for (int y = bottom; y <= top; y++) {
                add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; x++) {
                add(new Point(x, bottom));
            }
        }

    }

}

/**
 * When using adapters, it very common to create the same object many times and when it happens it is good to
 * have a caching mechanism in place.
 *
 */
class LineToPointAdapterCache implements Iterable<Point> {

    private static int count = 0;
    private static Map<Integer, List<Point>> cache = new HashMap<>();
    private int hash;

    public LineToPointAdapterCache(Line line) {
        hash = line.hashCode();
        if (cache.containsKey(hash)) {
            return;
        }
        System.out.println(String.format("[%s] Adapting Line start point(%s, %s) end point (%s, %s). [with caching]",
                ++count, line.start.x, line.start.y, line.end.x, line.end.y));
        List<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int bottom = Math.min(line.start.y, line.end.y);
        int top = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = top-bottom;
        if (dx == 0) {
            for (int y = bottom; y <= top; y++) {
                points.add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; x++) {
                points.add(new Point(x, bottom));
            }
        }
        cache.put(hash, points);
    }

    @Override
    public Iterator<Point> iterator() {
        return cache.get(hash).iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        cache.get(hash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return cache.get(hash).spliterator();
    }
}
