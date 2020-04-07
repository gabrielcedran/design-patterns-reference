package br.com.cedran.creational.builder;

/**
 * There could have some scenarios where multiple builders are necessary for any reason like separation of concern
 * or even to avoid a huge class.
 * The example below shows how to build a faceted fluent builder.
 */
public class FacetedBuilder {

    public static void main(String[] args) {
        StudentBuilder studentBuilder = new StudentBuilder();
        Student student = studentBuilder
                .data()
                    .name("Don Bob")
                    .age(6)
                .lives()
                    .city("Zaragoza")
                    .street("Calle Margarita Nelken")
                .build();

        System.out.println(student);
    }
}

class Student {
    protected String name;
    protected String surname;
    protected Integer age;

    protected String street;
    protected String city;
    protected String complement;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", complement='" + complement + '\'' +
                '}';
    }
}

class StudentBuilder {

    private Student student;

    public StudentBuilder() {
        if (student == null) {
            student = new Student();
        }
    }

    public StudentBuilder(Student student) {
        this.student = student;
    }

    public StudentDataBuilder data() {
        return new StudentDataBuilder(student);
    }

    public StudentAddressBuilder lives() {
        return new StudentAddressBuilder(student);
    }

    public Student build() {
        return student;
    }

}

class StudentDataBuilder extends StudentBuilder {

    private Student student;

    public StudentDataBuilder(Student student) {
        super(student);
        this.student = student;
    }

    public StudentDataBuilder name(String name) {
        student.name = name;
        return this;
    }

    public StudentDataBuilder surname(String surname) {
        student.surname = surname;
        return this;
    }

    public StudentDataBuilder age(Integer age) {
        student.age = age;
        return this;
    }
}

class StudentAddressBuilder extends StudentBuilder {

    private Student student;

    public StudentAddressBuilder(Student student) {
        super(student);
        this.student = student;
    }

    public StudentAddressBuilder street(String street) {
        student.street = street;
        return this;
    }

    public StudentAddressBuilder city(String city) {
        student.city = city;
        return this;
    }

    public StudentAddressBuilder complement(String complement) {
        student.complement = complement;
        return this;
    }
}
