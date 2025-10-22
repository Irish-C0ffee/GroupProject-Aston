package filling;

import Entity.Person;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RandomFilling implements Filling {

    private static final String[] surnames = {"Иванов", "Сапелкин", "Лескова", "Жуков", "Семменова", "Добров", "Пашин", "Добрышев",
            "Крылова", "Федоров", "Зайцева", "Агафонова", "Булгаков", "Изерский", "Кошенева", "Смирнов", "Тарасенко",
            "Решетилова", "Виноградов", "Пораскев", "Вышегородцев", "Веденькина", "Некрасова", "Хаметова", "Владимиров",
            "Собачкина", "Перец", "Владимиров", "Шахболатова", "Сапелкин", "Константинова", "Лебедев", "Лосинская",
            "Буслаева", "Тараканов", "Назаров", "Буслаев", "Цыганов", "Шахболатов", "Николаева"};
    private static final String[] names = {"Иван", "Андрей", "Марина", "Владимир", "Арина", "Сергей", "Владимир", "Вадим", "Александра", "Владимир"
            , "Евгения", "София", "Роман", "Дмитрий", "Ангелиина", "Глеб", "Ксения", "Ульяна", "Андрей", "Виолетта", "Виктор",
            "Елизавета", "Анна", "Милана", "Антон", "Софья", "Эдисон", "Сергей", "Маина", "Федор", "Анна", "Богдан", "Юлия",
            "Варвара", "Григорий", "Арсений", "Ахмед", "Арсений", "Заур", "Аделина"};

    @Override
    public void readData(int size, Scanner sc, List<Person> people) {
        Random random = new Random();
        IntStream.range(0, size)
                .mapToObj(i -> new Person.Builder()
                        .surname(surnames[random.nextInt(surnames.length)])
                        .name(names[random.nextInt(names.length)])
                        .age(random.nextInt(1, 123))
                        .build())
                .forEach(people::add);

        System.out.println("Данные добавлены");
    }
}