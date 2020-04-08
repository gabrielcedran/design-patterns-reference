package br.com.cedran.creational.singleton;

/**
 * Monostate is a quite confusing pattern and should be avoided, but the idea behind it is turn all the attributes static,
 * hence, in class variables, making the class behaves as though it was a singleton and allowing clients to create as many instances
 * as they want.
 * The problem with this pattern is that the class hides its behavior, making it confusing and not clear to the clients.
 *
 * Suppose the following scenario:
 * Companies only have one Chief Executive Officer. If you want to ensure that all the instances of the CEO within your
 * application have exactly the same values no matter the instance, you can turn all its attributes into static:
 */
public class Monostate {
    public static void main(String[] args) {
        var ceo1 = new ChiefExecutiveOfficer();
        ceo1.setName("Don Bob");
        ceo1.setAge(39);

        var ceo2 = new ChiefExecutiveOfficer();

        System.out.println(ceo1);
        System.out.println(ceo2);
    }
}

class ChiefExecutiveOfficer {
    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
