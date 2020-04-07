package br.com.cedran.creational.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Custom Builders help to improve readability since they simplify object creation by allowing a step by step approach.
 */
public class HtmlElement {
    protected String name;
    protected String value;
    protected List<HtmlElement> innerElements = new ArrayList<>();
    private static final Integer indentSize = 2;
    private static final String newLine = System.lineSeparator();

    public HtmlElement() {

    }

    public HtmlElement(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String toString() {
        return this.toStringImpl(0);
    }

    public String toStringImpl(Integer currentIndent) {
        StringBuilder htmlBuilder = new StringBuilder();
        String currentTagIndent = calculateIndentation(currentIndent);

        htmlBuilder.append(String.format("%s<%s>%s", currentTagIndent, name, newLine));

        Optional.ofNullable(value).ifPresent(val -> {
            String currentValueIndent = calculateIndentation(currentIndent+1);
            htmlBuilder.append(String.format("%s%s%s", currentValueIndent, value, newLine));
        });

        innerElements.stream().map(innerElement -> innerElement.toStringImpl(currentIndent + 1)).forEach(htmlBuilder::append);

        htmlBuilder.append(String.format("%s</%s>%s", currentTagIndent, name, newLine));
        return htmlBuilder.toString();
    }

    private String calculateIndentation(Integer indent) {
        return String.join("", Collections.nCopies(indent * indentSize, "  "));
    }
}

class HtmlBuilder {
    private String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }

    public HtmlBuilder addChild(String childName, String childText) {
        HtmlElement innerElement = new HtmlElement(childName, childText);
        root.innerElements.add(innerElement);
        return this;
    }

    private HtmlBuilder addChild(HtmlBuilder htmlBuilder) {
        root.innerElements.add(htmlBuilder.build());
        return this;
    }

    public void clear() {
        root = new HtmlElement();
        root.name = rootName;
    }

    public HtmlElement build() {
        return root;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) {
        /**
         * Creation with a custom builder.
         */
        HtmlBuilder htmlBuilder = new HtmlBuilder("ul");
        htmlBuilder.addChild("li", "text");
        htmlBuilder.addChild("li", "text 2");

        System.out.println(htmlBuilder.toString());

        /**
         * Fluent Builder.
         */
        htmlBuilder = new HtmlBuilder("ul")
                .addChild("li", "text")
                .addChild("li", "text 2");

        System.out.println(htmlBuilder.toString());

        /**
         * The same previous object being created without the builder.
         */
        HtmlElement rootElement = new HtmlElement();
        rootElement.name = "ul";
        rootElement.innerElements.add(new HtmlElement("li", "text"));
        rootElement.innerElements.add(new HtmlElement("li", "text 2"));

        System.out.println(rootElement.toString());


        HtmlBuilder htmlBuilder2 = new HtmlBuilder("ul")
                .addChild("li", "text")
                .addChild(new HtmlBuilder("ul")
                        .addChild("li", "text 2"));

        System.out.println(htmlBuilder2.toString());
    }

}
