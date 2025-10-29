package filling;

import Entity.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFillingTest {
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
    void readDataWhenUserInputValidValue() {
        setUserInput("Сергей\nСмирнов\n15\n");

        UserFilling userFilling = new UserFilling();
        userFilling.readData(1, scanner, people);

        String expected = "Введите имя: " + "Введите фамилию: " + "Введите возраст: " +
                "Данные добавлены" + System.lineSeparator();

        Person person = people.getFirst();
        assertEquals(expected, outputStream.toString());
        assertEquals(1, people.size());
        assertEquals("Сергей", person.getName());
        assertEquals("Смирнов", person.getSurname());
        assertEquals(15, person.getAge());
    }

    @AfterEach
    void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
