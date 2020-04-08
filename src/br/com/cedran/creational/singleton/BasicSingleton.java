package br.com.cedran.creational.singleton;

import java.io.*;

/**
 * In a nutshell, singleton is a component which is instantiated only once.
 *
 * The motivations to use the singleton patterns usually are:
 * - For some components, it makes sense to have only one instance in the system
 * For instance: Database repository, Object Factory
 * - When constructor call is expensive
 * In this case, the instantiation is done only one time and provide everyone the same instance
 * - Need to take care for lazy initialization and thread safety
 *
 */
public class BasicSingleton implements Serializable {

    /**
     * The way to prevent people from instantiating a class which is supposed to be singleton is creating a private
     * constructor. To maintain one single instance and allow usage of that instance, it is a matter of creating one
     * static variable which will hold the class instance and a static method which allows the clients to retrieve it.
     */
    private BasicSingleton() {

    }

    private static final BasicSingleton INSTANCE = new BasicSingleton();

    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected Object readResolve() {
        return INSTANCE;
    }
}

class Demo {
    public static void main(String[] args) {
        BasicSingleton basicSingleton = BasicSingleton.getInstance();
        basicSingleton.setValue(1);
        System.out.println(basicSingleton.getValue());
    }
}

class ProblemWithSingleton {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1. Reflection can still defeat the private constructor - but in this approach the developer is aware what he is doing
        // 2. Serialization still can make copies of the singleton and in this case, the developer could be doing it unaware,
        // not figuring out that he is breaking the singleton contract.
        // To sort out the serialization problem, it is possible to implement the method readResolve in the object class.
        // It works as a hint for the JVM to whenever it is deserializing an object of that class, it should use an existent instance.

        var singleton = BasicSingleton.getInstance();
        singleton.setValue(1);

        saveToFile(singleton, "test");

        var newSingleton = readFromFile("test");
        newSingleton.setValue(30);

        System.out.println(singleton == newSingleton);
        System.out.println(singleton.getValue() == newSingleton.getValue());

        var anotherCopy = copy(singleton);
        anotherCopy.setValue(333);
        System.out.println(singleton == anotherCopy);
        System.out.println(singleton.getValue() == anotherCopy.getValue());
        System.out.println(newSingleton.getValue() == anotherCopy.getValue());


    }

    static BasicSingleton copy(BasicSingleton singleton) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(singleton);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (BasicSingleton) ois.readObject();
    }

    static void saveToFile(BasicSingleton basicSingleton, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(basicSingleton);
        }

    }

    static BasicSingleton readFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (BasicSingleton) ois.readObject();
        }
    }
}
