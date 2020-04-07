package br.com.cedran.creational.prototype;

import java.util.Arrays;

/**
 * Prototype pattern is all about object copies. Complicated object are not made from scratch and many times instead of
 * building a new one, it makes sense to copy an existent one which is similar to what is needed
 * (does not matter if it is fully or partially constructed) and customize.
 * In order to make copies and avoid undesired side-effects, it is necessary to make a 'deep copy', which is a replication
 * of all the attributes rather then just a copy of the references (shallow copy), which could lead to one object change affect another.
 *
 *
 * DON'T USE CLONEABLE INTERFACE AND CLONE METHOD. This approach requires too much effort in order to properly copy with
 * all the possible references (lists, arrays, maps, inner objects, etc), hence is susceptible to errors if not during the first
 * development, during maintenance.
 * Another point is, the cloneable does not really state what type of clone is going to be performed and in fact the default
 * type is shallow copy.
 */
public class CopyCloneable {

    public static void main(String[] args) {

        /**
         * Suppose two members of the same household. Instead of creating both objects from scratch, it would be a good
         * idea to create one, copy the information to the second one and change only what is different.
         *
         */
        Person don = new Person(new String[]{"Don", "Cedran"}, new Address("Avenida Don Bosco", 11));
        Person bob = (Person) don.clone();
        bob.names[0] = "Bob";
        bob.address.houseNumber=12;

        System.out.println(don);
        System.out.println(bob);

    }


    static class Address implements Cloneable {
        public String streetName;
        public int houseNumber;

        public Address(String streetName, int houseNumber) {
            this.streetName = streetName;
            this.houseNumber = houseNumber;
        }

        @Override
        public Object clone() {
            return new Address(streetName, houseNumber);
        }

        @Override
        public String toString() {
            return "Address{" +
                    "streetName='" + streetName + '\'' +
                    ", houseNumber=" + houseNumber +
                    '}';
        }
    }

    static class Person implements Cloneable {
        public String[] names;
        public Address address;

        public Person(String[] names, Address address) {
            this.names = names;
            this.address = address;
        }

        @Override
        public Object clone() {
            return new Person(names.clone(), (Address) address.clone());
        }

        @Override
        public String toString() {
            return "Person{" +
                    "names=" + Arrays.toString(names) +
                    ", address=" + address +
                    '}';
        }
    }
}

