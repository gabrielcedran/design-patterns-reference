package br.com.cedran.creational.builder;

/**
 * When using fluent builders with inheritance, whenever a method of a parent class is called and its instance is returned
 * it is not possible to call methods of the subclass anymore. Suppose the following scenario:
 * ParentBuilder class has the method withName which returns its instance.
 * ChildBuilder class has the method withSurname which returns its instance.
 * If the ChildBuilder is instantiated and the method withName is called first, the method withSurname is not available
 * anymore, since that method returns a reference to ParentBuilder, although the instance is still a ChildBuilder.
 *
 * In order to overcome this problem, it is necessary to use recursive generic, as demonstrated below.
 * The self method is not strictly necessary, but it is a convenient method to centralize the cast to the original
 * reference.
 *
 */
public class FluentBuilderWithInheritance {

    public static void main(String[] args) {
        var personBuilder = new PersonBuilder<>();
        personBuilder.withName("Mary");
        System.out.println(personBuilder.build());

        var employeeBuilder = new EmployeeBuilder();
        employeeBuilder.withName("Gabriel").worksAs("Dev");
        System.out.println(employeeBuilder.build());
    }
}

class Person {
    protected String name;
    protected String position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    protected Person person = new Person();

    public SELF withName(String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    public SELF self() {
        return (SELF) this;
    }

}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    public EmployeeBuilder worksAs(String position) {
        this.person.position = position;
        return self();
    }
}

