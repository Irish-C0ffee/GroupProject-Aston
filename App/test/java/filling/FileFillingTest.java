package filling;

import Entity.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileFillingTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private Scanner scanner;
    private final List<Person> people = new ArrayList<>();

    @BeforeEach
    void init() {
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    private void setUserInput(String userInput) {
        scanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
    }


    @Test
    void readDataWhenUserInputValidFile() throws IOException {
        Path tempFile = Files.createTempFile("userInput", "");
        Files.writeString(tempFile, """
                Иванов Иван 125
                Сапелкин Андрей 130
                
                Лескова Марина 24
                """);
        setUserInput(tempFile.toString());

        FileFilling fileFilling = new FileFilling();
        fileFilling.readData(2, scanner, people);

        String expected1 = "Укажите путь к файлу: " + "Валидные данные добавленны" + System.lineSeparator();
        Person expected2 = new Person.Builder().surname("Иванов").name("Иван").age(125).build();
        Person expected3 = new Person.Builder().surname("Лескова").name("Марина").age(24).build();

        assertEquals(expected1, outputStream.toString());
        assertEquals(expected2.getName(), people.get(0).getName());
        assertEquals(expected3.getName(), people.get(1).getName());
        assertEquals(2, people.size());

        Files.delete(tempFile);
    }

    @Test
    void readDataWhenUserInputNotValidFile() {
        setUserInput("NonExistingFile");

        FileFilling fileFilling = new FileFilling();
        fileFilling.readData(2, scanner, people);

        String expected = "Укажите путь к файлу: " + "Файл не найден!" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @AfterEach
    void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
