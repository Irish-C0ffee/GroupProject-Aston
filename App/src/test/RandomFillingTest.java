package test;

import entity.Person;
import filling.RandomFilling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomFillingTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final List<Person> people = new ArrayList<>();

    @BeforeEach
    void init() {
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @Test
    void readDataValidSize() {

        RandomFilling randomFilling = new RandomFilling();
        randomFilling.readData(2, null, people);

        String expected = "Данные добавлены" + System.lineSeparator();

        assertEquals(2, people.size());
        assertEquals(expected, outputStream.toString());
    }
}
