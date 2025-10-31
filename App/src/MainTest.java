import entity.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private final static String mainMenu =
            "Меню" + System.lineSeparator() +
            "1. Добавить данные" + System.lineSeparator() +
            "2. Отсортировать данные" + System.lineSeparator() +
            "3. Сохранить данные" + System.lineSeparator() +
            "4. Вывести на консоль" + System.lineSeparator() +
            "5. Найти" + System.lineSeparator() +
            "6. Выйти" + System.lineSeparator() +
            "Выберите пункт меню:" + System.lineSeparator() ;

    private final static String addMenu = """
            Выберите способ добавления данных:
            1. Из файла
            2. Рандомно
            3. Вручную
            4. Главное меню""";

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
    void startWhenUserInputAdd() {
        setUserInput("1\n2\n4\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Укажите размер массива данных" + System.lineSeparator()
                + addMenu + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void startWhenUserInputSort() {
        setUserInput("2\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Список пуст." + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void startWhenUserInputSave() {
        setUserInput("3\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Данных для записи нет" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void startWhenUserInputPrint() {
        setUserInput("4\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Список пуст" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void startWhenUserInputFind() {
        setUserInput("5\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Список пуст" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void menuAddWhenFileInput() {
        setUserInput("1\n1\n1\ncdhf\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Укажите размер массива данных" + System.lineSeparator()
                + addMenu + System.lineSeparator()
                + "Укажите путь к файлу: " + "Файл не найден!" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void menuAddWhenRandomInput() {
        setUserInput("1\n2\n2\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Укажите размер массива данных" + System.lineSeparator()
                + addMenu + System.lineSeparator()
                + "Данные добавлены" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    void menuAddWhenUserInput() {
        setUserInput("1\n1\n3\nИван\nСуворов\n12\n6\n");

        Main main = new Main(scanner, new ArrayList<>());
        main.start();
        String expected = mainMenu
                + "Укажите размер массива данных" + System.lineSeparator()
                + addMenu + System.lineSeparator()
                + "Введите имя: " + "Введите фамилию: "
                + "Введите возраст: " + "Данные добавлены" + System.lineSeparator()
                + mainMenu;

        Assertions.assertEquals(expected, outputStream.toString());
    }


    @Test
    void printWhenListNotEmpty() {
        Person person1 = new Person.Builder().surname("Сидоров").name("Сергей").age(37).build();
        Person person2 = new Person.Builder().surname("Серов").name("Олег").age(67).build();
        people.add(person1);
        people.add(person2);

        Main main = new Main(null, people);
        main.print();

        String expected ="0) "+ person1.toString() + System.lineSeparator()
                +"1) " + person2.toString() + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printWenListEmpty() {
        Main main = new Main(null, people);
        main.print();

        String expected = "Список пуст" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void saveWhenListNotEmpty() throws IOException {
        Path tempFile = Files.createTempFile("userInput", "");
        setUserInput(tempFile.toString());

        Person person1 = new Person.Builder().surname("Сидоров").name("Сергей").age(37).build();
        Person person2 = new Person.Builder().surname("Серов").name("Олег").age(67).build();
        people.add(person1);
        people.add(person2);

        Main main = new Main(scanner, people);
        main.save();

        String expected1 = "Укажите путь к файлу:" + System.lineSeparator()
                + "Данные записаны в файл " + tempFile + System.lineSeparator();
        String expected2 =
                String.format("%s %s %d\n", person1.getName(), person1.getSurname(), person1.getAge())
                        + String.format("%s %s %d\n", person2.getName(), person2.getSurname(), person2.getAge());
        String fileContent = Files.readString(tempFile);

        assertEquals(expected1, outputStream.toString());
        assertEquals(expected2, fileContent);

        Files.delete(tempFile);
    }

    @Test
    void saveWhenListEmpty() {
        Main main = new Main(scanner, people);
        main.save();

        String expected = "Данных для записи нет" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @AfterEach
    void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
