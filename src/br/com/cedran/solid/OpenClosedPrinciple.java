package br.com.cedran.solid;

import org.w3c.dom.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * OCP states that implementations should be closed for modification but open for extension.
 * When software is changed by extending functionalities rather modifying them, it prevent previously tested and working
 * features from becoming faulty.
 */
public class OpenClosedPrinciple {

    enum Color {
        RED, GREEN, BLUE, YELLOW
    }

    enum Size {
        SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }

    static class Product {
        protected String name;
        protected Color color;
        protected Size size;

        public Product(String name, Color color, Size size) {
            this.name = name;
            this.color = color;
            this.size = size;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", color=" + color +
                    ", size=" + size +
                    '}';
        }
    }


    /**
     * Imagine the system has the following service class which supports two types of search (by color and
     * by color and size) and the business wants to introduce a new filter criteria by name.
     * In order to introduce the new feature, the class ProductFilter should be changed rather than extended,
     * violating the OCP.
     */
    static class ProductFilter {
        Stream<Product> findProductByColor(List<Product> products, Color color) {
            return products.stream().filter(product -> product.color.equals(color));
        }

        Stream<Product> findProductByColorAndSize(List<Product> products, Color color, Size size) {
            return products.stream().filter(product -> product.color.equals(color) && product.size.equals(size));
        }
    }


    /**
     * When the system is designed in order to support extension rather than modification, introducing the find by name
     * is matter of creating a new class (NameFilter in this example), extend the base class and use it. The previously
     * created classes are not even touched.
     *
     * This sample mechanism could be by far more generic to support not only products, but other types of objects, having a
     * generic filter class: AttributePredicate<T> { boolean test(T item); } and a generic Search class:
     * ListFilter<T> { Stream<T> filter(List<T> items, AttributePredicate<T> predicate); }, but to keep the example
     * simpler and easier to understand, the level of abstraction has been reduced.
     */
    interface AttributeFilter {
        boolean filter(Product product);
    }

    static class ColorFilter implements AttributeFilter{

        private Color color;

        public ColorFilter(Color color) {
            this.color = color;
        }

        @Override
        public boolean filter(Product product) {
            return product.color.equals(color);
        }
    }

    static class SizeFilter implements AttributeFilter{

        private Size size;

        public SizeFilter(Size color) {
            this.size = color;
        }

        @Override
        public boolean filter(Product product) {
            return product.size.equals(size);
        }
    }

    static class MultipleAttributeFilter implements AttributeFilter {
        private List<AttributeFilter> filters;

        public MultipleAttributeFilter(AttributeFilter... filters) {
            this.filters = Arrays.asList(filters);
        }

        @Override
        public boolean filter(Product product) {
            return filters.stream().allMatch(filter -> filter.filter(product));
        }
    }

    static class ProductFilterExtensible {

        public Stream<Product> find(List<Product> products, AttributeFilter attributeFilter) {
            return products.stream().filter(attributeFilter::filter);
        }
    }

    public static void main(String[] args) {
        final var tShirt = new Product("TShirt", Color.BLUE, Size.SMALL);
        final var trousers = new Product("Trousers", Color.GREEN, Size.LARGE);
        final var sneakers = new Product("Sneakers", Color.GREEN, Size.MEDIUM);

        var products = List.of(tShirt, trousers, sneakers);

        ProductFilter productFilter = new ProductFilter();
        System.out.println("Green products (OCP Violation): ");
        productFilter.findProductByColor(products, Color.GREEN).forEach(System.out::println);
        System.out.println("Green and Large products (OCP Violation):");
        productFilter.findProductByColorAndSize(products, Color.GREEN, Size.LARGE).forEach(System.out::println);

        ProductFilterExtensible productFilterExtensible = new ProductFilterExtensible();
        System.out.println("Green products (OCP adherent ): ");
        productFilterExtensible.find(products, new ColorFilter(Color.GREEN)).forEach(System.out::println);
        System.out.println("Green products (OCP adherent ): ");
        productFilterExtensible.find(products, new MultipleAttributeFilter(new ColorFilter(Color.GREEN), new SizeFilter(Size.LARGE))).forEach(System.out::println);

    }

}