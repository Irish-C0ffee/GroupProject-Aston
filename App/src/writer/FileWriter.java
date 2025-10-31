package writer;

import entity.Person;
import exception.WorkingWithFileException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileWriter {
    public static void writeFile(List<Person> people,Path path) {
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
