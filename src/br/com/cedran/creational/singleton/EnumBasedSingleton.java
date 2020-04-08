package br.com.cedran.creational.singleton;

import java.io.*;

/**
 * Enum works partially as a class, except it has a finite number of instances and does not allow inheritance.
 * By default it does not have the problems with reflection being capable of accessing private constructors and copies
 * from serialization creating new instances, although it is serializable by default.
 *
 * Another confusing concept about enums is, no state is serialized at all, although enums are serializable by default.
 * The only thing which is serialized is the enum name itself. In the example below it seems to serialize the variable value
 * but when deserializing it only brings the value because the JVM is using the instance which is in memory. Should
 * be app be killed, when trying to deserialize one previously serialized enum, it will get completely reinitialized.
 *
 * To test the previously explanation, execute the next program two times:
 * One which gets the enum instance and change the value value and another one which comments out the value assignation and the
 * instance retrieval and only loads the enum from the serialized file.
 *
 * In a nutshell, enum fields are always effectively as transient.
 *
 */
public enum EnumBasedSingleton {
    INSTANCE;

    EnumBasedSingleton() {
        this.value = 30;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
       EnumBasedSingleton enumBasedSingleton = EnumBasedSingleton.INSTANCE;
        enumBasedSingleton.setValue(10);
        System.out.println(enumBasedSingleton.value);

        EnumBasedSingleton copy = copy(null);
        System.out.println(copy == enumBasedSingleton);
        System.out.println(enumBasedSingleton.getValue());
        System.out.println(copy.getValue());
    }

    public static EnumBasedSingleton copy(EnumBasedSingleton enumBasedSingleton) throws IOException, ClassNotFoundException {
        String enumFilename = "enumserialization.bin";

        FileOutputStream fos = new FileOutputStream(enumFilename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(enumBasedSingleton);

        FileInputStream fis = new FileInputStream(enumFilename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (EnumBasedSingleton) ois.readObject();

    }
}
