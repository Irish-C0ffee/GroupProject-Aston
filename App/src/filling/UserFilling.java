package filling;

import entity.Person;

import java.util.List;
import java.util.Scanner;

import static utils.ValidationUtils.validateInt;
import static utils.ValidationUtils.validateName;

public class UserFilling implements Filling {

    @Override
    public void readData(int size, Scanner sc, List<Person> people) {
        int count = 0;

        while (size - count != 0) {
            String name = null;
            while (name == null) {
                System.out.print("Введите имя: ");
                name = validateName(sc);
            }

            String surname = null;
            while (surname == null) {
                System.out.print("Введите фамилию: ");
                surname = validateName(sc);
            }

            System.out.print("Введите возраст: ");
            int age = validateInt(sc, 1, 123);
            people.add(new Person.Builder().surname(surname).name(name).age(age).build());
            count++;
        }
        System.out.println("Данные добавлены");
    }
}
