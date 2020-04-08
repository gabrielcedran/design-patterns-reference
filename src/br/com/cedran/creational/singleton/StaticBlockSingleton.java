package br.com.cedran.creational.singleton;

import java.io.File;
import java.io.IOException;

/**
 * In case the private singleton constructor throws checked exception or even if there is a number of finite number of instances
 * you want to return depending on the case in the getInstance method, the way of handling the exception(s) or even
 * the different scenarios instantiation is using static blocks.
 *
 * Suppose the example below which declares it could throw IOException. Simply declaring the static variable, instantiating
 * and assigning the single instance will cause compilation error due to unhandled exception:
 * private static final StaticBlockSingleton INSTANCE = new StaticBlockSingleton();
 */
public class StaticBlockSingleton {

    private StaticBlockSingleton() throws IOException {
        System.out.println("Initializing singleton.");
        File.createTempFile(".", ".");
    }

    private static StaticBlockSingleton INSTANCE;

    static {
        try {
            INSTANCE = new StaticBlockSingleton();
        } catch (IOException e) {
            System.err.println("Failed to create singleton.");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return INSTANCE;
    }

}
