package utils;

import java.util.Scanner;

public class ValidationUtils {

    public static int validateInt(Scanner sc, int min, int max) {
        int value = 0;
        boolean valid = false;

        while (!valid) {
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.printf("Ошибка: число должно быть от %d до %d!%n", min, max);
                }
            } else {
                System.out.println("Ошибка: введите целое число!");
            }
            sc.nextLine();
        }
        return value;
    }

    public static boolean isStringValid(String str) {
        return str.matches("^[A-Za-zА-Яа-яЁё]{2,20}\\s+[A-Za-zА-Яа-яЁё]{2,20}\\s+(1[0-1][0-9]|[1-9][0-9]|[0-9]|12[0-5])");
    }

    public static String validateName(Scanner sc) {
        String line = sc.nextLine();
        if (isNameValid(line)) {
            return line;
        } else {
            System.out.println("Количество символов одного значения не должно превышать 20, допускаются только буквенные символы.\n" +
                    "Количество значений должно соответствовать указанному размеру");
        }
        return null;
    }

    private static boolean isNameValid(String s) {
        return s.matches("[а-яА-ЯёЁ]+") && s.length() > 1 && s.length() <= 20;
    }
}
