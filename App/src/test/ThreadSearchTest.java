package test;

import comparator.PersonComparators;
import entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search.ThreadSearch;
import strategy.QuickSortStrategy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ThreadSearchTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final List<Person> people = new ArrayList<>();
    private final QuickSortStrategy<Person> quick = new QuickSortStrategy<>();


    @BeforeEach
    void init() {
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        people.add(new Person.Builder().surname("Иванов").name("Иван").age(24).build());
        people.add(new Person.Builder().surname("Лескова").name("Марина").age(24).build());
    }

    @Test
    void findName(){

        quick.sort(people, PersonComparators.BY_NAME);

        ThreadSearch.findCount(people,"Иван",Person::getName);
         String expected = "Количество найденных элементов: 1\n";
        Assertions.assertEquals(expected,outputStream.toString());
    }

    @Test
    void findSurname(){

        quick.sort(people, PersonComparators.BY_SURNAME);

        ThreadSearch.findCount(people,"Лескова",Person::getSurname);
        String expected = "Количество найденных элементов: 1\n";
        Assertions.assertEquals(expected,outputStream.toString());
    }

    @Test
    void findAge(){

        quick.sort(people, PersonComparators.BY_AGE);

        ThreadSearch.findCount(people,24,Person::getAge);
        String expected = "Количество найденных элементов: 2\n";
        Assertions.assertEquals(expected,outputStream.toString());
    }

    @Test
    void findAgeWhenNotInList(){

        quick.sort(people, PersonComparators.BY_AGE);

        ThreadSearch.findCount(people,27,Person::getAge);
        String expected = "Количество найденных элементов: 0\n";
        Assertions.assertEquals(expected,outputStream.toString());
    }
}
