package br.com.cedran.solid;

/**
 * Liskov Substitution Principle states that a super class should be replaceable to any of its subclass not breaking
 * the clients which is using it.
 *
 * This principle helps to identify OO design flaws.
 */
public class LiskovSubstitutionPrinciple {

    static class Rectangle {
        private Integer width;
        private Integer height;

        public void setWidth(Integer width) {
            this.width = width;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer calculateArea() {
            return width * height;
        }
    }

    /**
     * At first, the square implementation seems sensible, size its sizes are the same and a square is a rectangle from OO perspective.
     */
    static class Square extends Rectangle {

        @Override
        public void setWidth(Integer width) {
            super.setWidth(width);
            super.setHeight(width);
        }

        @Override
        public void setHeight(Integer height) {
            super.setHeight(height);
            super.setWidth(height);
        }
    }

    public static void changeDimensionsAndAssertArea(Rectangle rectangle) {

        rectangle.setHeight(10);
        rectangle.setWidth(2);

        assert rectangle.calculateArea().equals(20) : "Unexpected Area found";
}

    /**
     * But when that implementation is passed to a client which is expecting a rectangle, it could cause that client
     * to fail.
     */
    public static void main(String[] args) {
        changeDimensionsAndAssertArea(new Rectangle());
        changeDimensionsAndAssertArea(new Square());
    }
}
