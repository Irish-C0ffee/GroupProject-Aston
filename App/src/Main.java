import Entity.Person;
import comparator.PersonComparators;
import filling.*;
import service.ThreadPoolSortService;
import strategy.*;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

import static utils.ValidationUtils.validateInt;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    private final List<Person> people = new ArrayList<>();

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        try (sc) {
            while (true) {
                System.out.println("Выберите пункт меню");
                System.out.println("1. Добавить данные");
                System.out.println("2. Отсортировать данные");
                System.out.println("3. Сохранить данные");
                System.out.println("4. Вывести на консоль");
                System.out.println("5. Найти");
                System.out.println("6. Выйти");
                int choice = validateInt(sc, 1, 6);
                switch (choice) {
                    case 1 -> menuAdd();
                    case 2 -> menuSort();
                    case 3 -> save();
                    case 4 -> print();
                    case 5 -> find();
                    case 6 -> {
                        return;
                    }
                }
            }
        }
    }

    private void menuAdd() {
        System.out.println("Укажите размер массива данных");
        int size = validateInt(sc, 1, Integer.MAX_VALUE);
        System.out.println("""
                Выберите способ добавления данных:
                1. Из файла
                2. Рандомно
                3. Вручную
                4. Главное меню""");
        int choice = validateInt(sc, 1, 4);
        Filling filling = switch (choice) {
            case 1 -> new FileFilling();
            case 2 -> new RandomFilling();
            case 3 -> new UserFilling();
            default -> null;
        };
        Optional.ofNullable(filling).ifPresent(f -> f.readData(size, sc, people));
    }

    private void menuSort() {
        if (people.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        System.out.println("""
                Выберите режим сортировки:
                1. Обычная сортировка
                2. Сортировка только чётных элементов по числовому полю
                """);
        int modeChoice = validateInt(sc, 1, 2);
        boolean evenOnly = modeChoice == 2;

        if (evenOnly) {
            System.out.println("""
                    Выберите числовое поле для сортировки:
                    1. ID
                    2. Возраст
                    """);
        } else {
            System.out.println("""
                    Выберите поле для сортировки:
                    1. ID
                    2. Имя
                    3. Фамилия
                    4. Возраст
                    """);
        }

        int fieldChoice = evenOnly ? validateInt(sc, 1, 2) : validateInt(sc, 1, 4);

        Comparator<Person> comparator;
        Function<Person, Integer> numericExtractor = null;

        if (evenOnly) {
            switch (fieldChoice) {
                case 1 -> {
                    comparator = Comparator.comparingInt(Person::getId);
                    numericExtractor = Person::getId;
                }
                case 2 -> {
                    comparator = Comparator.comparingInt(Person::getAge);
                    numericExtractor = Person::getAge;
                }
                default -> throw new IllegalArgumentException("Некорректный выбор поля");
            }
        } else {
            comparator = switch (fieldChoice) {
                case 1 -> Comparator.comparingInt(Person::getId);
                case 2 -> Comparator.comparing(Person::getName);
                case 3 -> Comparator.comparing(Person::getSurname);
                case 4 -> Comparator.comparingInt(Person::getAge);
                default -> Comparator.comparingInt(Person::getId);
            };
        }

        System.out.println("""
                Выберите алгоритм сортировки:
                1. Пузырьковая сортировка
                2. Быстрая сортировка
                """);
        int sortChoice = validateInt(sc, 1, 2);
        SortStrategy<Person> strategy = switch (sortChoice) {
            case 1 -> new BubbleSortStrategy<>();
            case 2 -> new QuickSortStrategy<>();
            default -> new BubbleSortStrategy<>();
        };

        if (evenOnly && numericExtractor != null) {
            strategy = new EvenFieldSortStrategy<>(numericExtractor, strategy);
        }

        try (ThreadPoolSortService<Person> sortService = new ThreadPoolSortService<>(2)) {
            sortService.sort(people, comparator, strategy);
        }
    }

    private void print() {
        if (people.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        people.forEach(System.out::println);
    }

    private void find() {
    }

    private void save() {
        String filePath;
        System.out.println("Укажите путь к файлу:");
        filePath = sc.nextLine().trim();
        File file = new File(filePath);
        if (file.exists()) {
            Path path = file.toPath();
        } else {
            System.out.println("Файл не найден!");
        }
    }
}