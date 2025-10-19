package Entity;

public class Student {
    private final int id;
    private  final String name;
    private  final String surname;
    private  final int age;

    private Student(Student.Builder builder) {
        this.id=builder.id;
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
        private int id;
        private String name;
        private String surname;
        private int age;

        public Student.Builder id(int id) {
            this.id = id;
            return this;
        }
        public Student.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Student.Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Student.Builder age(int age) {
            this.age = age;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}

