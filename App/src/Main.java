import entity.Person;
import comparator.PersonComparators;
import exception.WorkingWithFileException;
import filling.FileFilling;
import filling.Filling;
import filling.RandomFilling;
import filling.UserFilling;
import search.BinarySearch;
import search.ThreadSearch;
import service.ThreadPoolSortService;
import strategy.BubbleSortStrategy;
import strategy.EvenFieldSortStrategy;
import strategy.QuickSortStrategy;
import strategy.SortStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static utils.ValidationUtils.validateInt;
import static utils.ValidationUtils.validateName;

public class Main {
    private final Scanner sc;
    private final List<Person> people;


    public Main(Scanner sc, List<Person> people) {
        this.sc = sc;
        this.people = people;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in), new ArrayList<>()).start();
    }

    public void start() {
        try (sc) {
            while (true) {
                System.out.println("Меню");
                System.out.println("1. Добавить данные");
                System.out.println("2. Отсортировать данные");
                System.out.println("3. Сохранить данные");
                System.out.println("4. Вывести на консоль");
                System.out.println("5. Найти");
                System.out.println("6. Выйти");
                System.out.println("Выберите пункт меню:");
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

    void print() {
        if (people.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        //people.forEach(System.out::println);
        int count = 1;
        for (Person s : people) {
            System.out.println(count + ") " + s);
            count++;
        }
    }

    private void find() {
        if (people.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }

        System.out.println("""
                Выберите поле для поиска:
                1. Имя
                2. Фамилия
                3. Возраст
                """);
        int fieldChoice = validateInt(sc, 1, 3);
        Comparator<Person> comparator = switch (fieldChoice) {
            case 1 -> PersonComparators.BY_NAME;
            case 2 -> PersonComparators.BY_SURNAME;
            case 3 -> PersonComparators.BY_AGE;
            default -> PersonComparators.BY_NAME;
        };
        try (ThreadPoolSortService<Person> sortService = new ThreadPoolSortService<>(2)) {
            sortService.sort(people, comparator, new QuickSortStrategy<>());
        }
        System.out.println("Введите значение для поиска:");
        Person searchKey = null;


        if (fieldChoice == 1) {
            String name = null;
            while (name == null) {
                name = validateName(sc);
                searchKey = new Person.Builder().name(name).surname("").age(0).build();
            }
        }
        if (fieldChoice == 2) {
            String name = null;
            while (name == null) {
                name = validateName(sc);
                searchKey = new Person.Builder().name("").surname(name).age(0).build();
            }
        }
        if (fieldChoice == 3) {
            int value = validateInt(sc, 1, 123);
            searchKey = new Person.Builder().name("").surname("").age(value).build();
        }
        System.out.println("""
                Выбирите тип поиска:
                1.Найти элемент коллекции.
                2.Найти количество вхождений элемента в коллекцию.
                """);
        int FindChoice = validateInt(sc, 1, 2);
        if (FindChoice == 1) {
            int index = BinarySearch.binarySearch(people, searchKey, comparator);
            if (index == -1)
                System.out.println("Элемент не найден");
            else System.out.println("Индекс элемента:  " + index);
        } else {
            if (comparator == PersonComparators.BY_NAME){
                ThreadSearch.findCount(people, searchKey.getName(), Person::getName);
            } else if (comparator == PersonComparators.BY_SURNAME) {
                ThreadSearch.findCount(people, searchKey.getSurname(), Person::getSurname);
            } else {
                ThreadSearch.findCount(people, searchKey.getAge(), Person::getAge);
            }
        }
    }

    void save() {
        if (people.isEmpty()) {
            System.out.println("Данных для записи нет");
            return;
        }
        System.out.println("Укажите путь к файлу:");
        String filePath = sc.nextLine().trim();
        writeFile(Path.of(filePath));
    }

    private void writeFile(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, CREATE, APPEND)) {
            people.stream()
                    .map(person -> String.format("%s %s %d\n", person.getName(), person.getSurname(), person.getAge()))
                    .forEach(str -> {
                        try {
                            writer.write(str);
                        } catch (IOException e) {
                            throw new WorkingWithFileException("Сбой операции записи файла", e);
                        }
                    });
        } catch (IOException | WorkingWithFileException e) {
            System.out.println("Сбой операции записи файла");
            return;
        }
        System.out.println("Данные записаны в файл " + path);
    }
}