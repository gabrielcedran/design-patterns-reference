package br.com.cedran.creational.prototype;

import java.io.*;
import java.util.Arrays;

/**
 * The biggest problem with copy constructors is that it needs to be implement in every single object you have in the chain
 * and forgetting to properly call the copy constructor of one of the objects (or even implement), will cause
 * you to have a shallow copy and lead to problem.
 *
 * In order to implement deep copy avoiding jumping through the hoops of object structure and hierarchy, serialization is
 * a potential good solution.
 *
 */
public class CopyThroughSerialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var person = new Person(new String[]{"Gabriel", "Cedran"}, 31, "M");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(person);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        var copied = (Person) in.readObject();

        copied.sex = "F";

        System.out.println(person);
        System.out.println(copied);

        // Apache utils
        // var copied = SerializationUtils.roundTrip(person);
    }

    static class Person implements Serializable {
        public String[] names;
        public int age;
        public String sex;

        public Person(String[] names, int age, String sex) {
            this.names = names;
            this.age = age;
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "names=" + Arrays.toString(names) +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }

}
