import Entity.Person;
import filling.Filling;
import filling.FileFilling;
import filling.RandomFilling;
import filling.UserFilling;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
    }

    private void print() {
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