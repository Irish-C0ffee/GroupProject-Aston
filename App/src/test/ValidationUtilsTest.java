package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ValidationUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private Scanner scanner;

    @BeforeEach
    void init() {
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    private void setUserInput(String userInput) {
        scanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
    }

    @Test
    void validateIntWhenUserInputMaxValue() {
        setUserInput("3\n");

        int actual = ValidationUtils.validateInt(scanner, 1, 3);

        Assertions.assertEquals(3, actual);
    }

    @Test
    void validateIntWhenUserInputMinValue() {
        setUserInput("1\n");

        int actual = ValidationUtils.validateInt(scanner, 1, 3);

        Assertions.assertEquals(1, actual);
    }

    @Test
    void validateIntWhenUserInputValidValue() {
        setUserInput("2\n");

        int actual = ValidationUtils.validateInt(scanner, 1, 3);

        Assertions.assertEquals(2, actual);
    }

    @Test
    void validateIntWhenUserInputNotValidValue() {        
        setUserInput("d\n0\n4\n1\n");

        String expected = "Ошибка: введите целое число!" + System.lineSeparator() +
                "Ошибка: число должно быть от 1 до 3!" + System.lineSeparator() +
                "Ошибка: число должно быть от 1 до 3!" + System.lineSeparator();
        
        int actual = ValidationUtils.validateInt(scanner, 1, 3);
        
        assertEquals(expected, outputStream.toString());
        assertEquals(1, actual);
    }

    @Test
    void isStringValidWhenUserInputValidString() {
        String s = "Семенов Влад 34";
        boolean actual = ValidationUtils.isStringValid(s);

        assertTrue(actual);
    }

    @Test
    void isStringValidWhenUserInputNotValidString() {
        String s = "34 Семенов Влад";

        assertFalse(ValidationUtils.isStringValid(s));
    }

    @Test
    void validateNameWhenUserInputValidName() {
        String name = "Александр";
        setUserInput(name);

        String expected = name;
        String actual = ValidationUtils.validateName(scanner);
        
        assertEquals(expected, actual);
    }

    @Test
    void validateNameWhenUserInputTooLongName() {
        setUserInput("Гүрсоронзонгомбосүрэнболд");

        String expected = "Количество символов одного значения не должно превышать 20," +
                " допускаются только буквенные символы." + System.lineSeparator();
        String actual = ValidationUtils.validateName(scanner);

        assertNull(actual);
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void validateNameWhenUserInputTooShortName() {
        setUserInput("Г");

        String expected = "Количество символов одного значения не должно превышать 20," +
                " допускаются только буквенные символы." + System.lineSeparator();
        String actual = ValidationUtils.validateName(scanner);

        assertNull(actual);
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void validateNameWhenUserInputNotLetters() {
        setUserInput("234");

        String expected = "Количество символов одного значения не должно превышать 20," +
                " допускаются только буквенные символы." + System.lineSeparator();
        String actual = ValidationUtils.validateName(scanner);

        assertNull(actual);
        assertEquals(expected, outputStream.toString());
    }

    @AfterEach
    void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}