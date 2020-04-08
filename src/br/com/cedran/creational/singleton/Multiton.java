package br.com.cedran.creational.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * This pattern is not a singleton pattern since it allows more than one instances to be created
 * but it only allows a predefined number of finite instances.
 *
 * It is useful when you need more than one instance to be created but controlled.
 *
 * The example below is not thread-safe.
 */
public class Multiton {

    public static void main(String[] args) {
        var primary = Printer.getInstance(SubSystem.PRIMARY);
        var auxiliary = Printer.getInstance(SubSystem.AUXILIARY);
        var auxiliary2 = Printer.getInstance(SubSystem.AUXILIARY);
    }
}

enum SubSystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class Printer {

    private static int instancesCount = 0;
    private static Map<SubSystem, Printer> instances = new HashMap<>();

    private Printer() {
        System.out.println("Creating a printer");
        instancesCount++;
        System.out.println("Total printer instances created: " + instancesCount);
    }

    public static Printer getInstance(SubSystem subSystem) {
        if (instances.containsKey(subSystem)) {
            return instances.get(subSystem);
        }
        return instances.putIfAbsent(subSystem, new Printer());
    }
}
