package entity;

import java.util.Comparator;

public class Person {
    private static int cnt = 0;
    private final int id;
    private final String name;
    private final String surname;
    private final int age;

    private Person(Builder builder) {
        this.id = ++cnt;
        this.name = builder.name;
        this.surname = builder.surname;
        this.age = builder.age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public static Comparator<Person> compareByAllFields() {
        return (p1, p2) -> {
            int surnameCompare = p1.getSurname().compareTo(p2.getSurname());
            if (surnameCompare != 0) {
                return surnameCompare;
            }
            int nameCompare = p1.getName().compareTo(p2.getName());
            if (nameCompare != 0) {
                return nameCompare;
            }
            return Integer.compare(p1.getAge(), p2.getAge());
        };
    }

    //    public int compareTo(Person key) {
//
//        int result = this.name.compareTo(key.name);
//        System.out.println(this.name + "\n" + key.name + result);
//        if (result == 0) {
//            result = this.surname.compareTo(key.surname);
//            System.out.println(this.surname + "\n" + key.surname + result);
//            if (result == 0) {
//                result=  Integer.compare(age, key.age);
//                System.out.println(this.age + "\n" + key.age + result);
//            }
//        }
//            return result;
//    }
    public static class Builder {
        private String name;
        private String surname;
        private int age;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" + "id='" + id + "', " + "name='" + name + "', " + "surname='" + surname + "', " + "age=" + age + '}';
    }
}
