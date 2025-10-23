package Entity;

public class Person {
    private static int cnt=0;
    private final int id;
    private  final String name;
    private  final String surname;
    private  final int age;

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
