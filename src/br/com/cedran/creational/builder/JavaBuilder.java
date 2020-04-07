package br.com.cedran.creational.builder;

import java.util.stream.Stream;

/**
 * The idea behind builders is:
 * Sometimes, the number of attributes to create an object can be huge, and provide everything to a constructor can be cumbersome
 * and awkward. The builder patterns allows the creation of objects in a sort of steps ways.
 *
 * Java API comes with some builders out of the box and one of the most famous one is the StringBuilder, which allows a String
 * to be created incrementally rather then concatenating many pieces of String with the plus operator:
 */
public class JavaBuilder {

    /**
     * Suppose the words array need to have each of its entries output as an element of a HTML unordered list.
     * It could be done the traditional way, using String concatenation or Using the StringBuilder. Both approaches are
     * demonstrated below:
     */
    public static void main(String[] args) {
        String[] words = {"Testing", "Builders", "Provided", "By", "The", "Api"};
        String htmlWithConcatenation = "<ul>";
        for (String word : words) {
            htmlWithConcatenation += String.format("  <li>%s</li>", word);
        }
        htmlWithConcatenation += "</ul>";
        System.out.println(htmlWithConcatenation);

        StringBuilder htmlWithStringBuilder = new StringBuilder("<ul>");
        Stream.of(words).forEach(word -> htmlWithStringBuilder.append(String.format("  <li>%s</li>", word)));
        htmlWithStringBuilder.append("</ul>");
        System.out.println(htmlWithStringBuilder);
    }
}
