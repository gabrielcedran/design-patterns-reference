package br.com.cedran.structural.bridge;

/**
 *  Bridge prevents am entity "Cartesian Product" complexity explosion.
 *
 * Suppose the following object structure
 * Shape ->  Circle and Square
 * Rendering -> Vector and Raster
 * It could end up having 2x2 implementations: VectorCircleRenderer, RasterCircleRenderer, VectorSquareRenderer and RasterSquareRenderer
 * If there were 5 different shapes and 3 renderers, it would end up with 5 x 3 implementations, hence 15.
 *
 * Shape -> Circle and Square -> VectorCircleRenderer, RasterCircleRenderer, VectorSquareRenderer and RasterSquareRenderer
 *
 * The bridge patterns helps here by adding the renderer inside the shapes, reducing the number of classes.
 *
 */
public class Bridge {
    public static void main(String[] args) {
        RasterRenderer raster = new RasterRenderer();
        VectorRenderer vector = new VectorRenderer();

        // Ideally this example would be using a DI framework rather than injecting the dependency manually
        Circle circle = new Circle(raster, 10);
        circle.draw();
        circle.resize(2);
        circle.draw();

        circle = new Circle(vector, 4);
        circle.draw();
    }
}

interface Renderer {
    void renderCircle(float radius);
}

class VectorRenderer implements Renderer {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing a circle of radius " + radius);
    }
}

class RasterRenderer implements Renderer {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing a circle of radius " + radius);
    }
}

abstract class Shape {

    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle extends Shape {

    public float radius;

    public Circle(Renderer renderer) {
        super(renderer);
    }

    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}