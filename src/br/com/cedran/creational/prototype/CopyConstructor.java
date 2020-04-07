package br.com.cedran.creational.prototype;

import java.util.Arrays;

/**
 * The idea of copy constructors is to explicitly specify that a given object is copyable.
 */
public class CopyConstructor {

    public static void main(String[] args) {
        /**
         * Suppose two members of the same household. Instead of creating both objects from scratch, it would be a good
         * idea to create one, copy the information to the second one and change only what is different.
         *
         */
        CopyCloneable.Person don = new CopyCloneable.Person(new String[]{"Don", "Cedran"}, new CopyCloneable.Address("Avenida Don Bosco", 11));
        CopyCloneable.Person bob = (CopyCloneable.Person) don.clone();
        bob.names[0] = "Bob";
        bob.address.houseNumber=12;

        System.out.println(don);
        System.out.println(bob);
    }

    class Address {
        public String streetName;
        public int houseNumber;

        public Address(String streetName, int houseNumber) {
            this.streetName = streetName;
            this.houseNumber = houseNumber;
        }

        public Address(Address other) {
            this.streetName = other.streetName;
            this.houseNumber = other.houseNumber;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "streetName='" + streetName + '\'' +
                    ", houseNumber=" + houseNumber +
                    '}';
        }
    }

    class Person {
        public String[] names;
        public Address address;

        public Person(String[] names, Address address) {
            this.names = names;
            this.address = address;
        }

        public Person(Person other) {
            this.names = other.names.clone();
            this.address = new Address(other.address);
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


