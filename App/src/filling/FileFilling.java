package filling;

import entity.Person;
import utils.ValidationUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class FileFilling implements Filling {

    @Override
    public void readData(int size, Scanner sc, List<Person> people) {
        String filePath;
        System.out.print("Укажите путь к файлу: ");
        filePath = sc.nextLine().trim();
        File file = new File(filePath);
        if (file.exists()) {
            Path path = file.toPath();
            read(size, path, people);
        } else {
            System.out.println("Файл не найден!");
        }
    }

    private static void read(int size, Path path, List<Person> people) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            reader.lines()
                    .map(String::trim)
                    .filter(string -> !string.isEmpty())
                    .filter(ValidationUtils::isStringValid)
                    .limit(size)
                    .map(s -> s.split(" "))
                    .map(s -> new Person.Builder()
                            .surname(s[0])
                            .name(s[1])
                            .age(Integer.parseInt(s[2]))
                            .build())
                    .forEach(people::add);
            System.out.println("Валидные данные добавленны");
        } catch (IOException e) {
            System.out.println("Сбой операции считывания файла");
        }
    }
}
