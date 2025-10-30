package test;

import entity.Person;
import org.junit.jupiter.api.Test;
import search.BinarySearch;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTest {

    List<Person> list = new ArrayList<>(List.of(
            new Person.Builder().name("Агата").surname("Иванова").age(3).build(),
            new Person.Builder().name("Михаил").surname("Мишанин").age(30).build(),
            new Person.Builder().name("Ксения").surname("Иванова").age(13).build(),
            new Person.Builder().name("Михаил").surname("Мишанин").age(30).build(),
            new Person.Builder().name("Инна").surname("Иванкина").age(63).build(),
            new Person.Builder().name("Петр").surname("Рыжиков").age(27).build(),
            new Person.Builder().name("Иван").surname("Носов").age(23).build(),
            new Person.Builder().name("Ян").surname("Щукин").age(88).build(),
            new Person.Builder().name("Константин").surname("Смелый").age(22).build(),
            new Person.Builder().name("Федор").surname("Дорин").age(35).build(),
            new Person.Builder().name("Марк").surname("Швайко").age(43).build(),
            new Person.Builder().name("Егор").surname("Волков").age(73).build())
    );
    Person searchKey = new Person.Builder().name("Ян").surname("Щукин").age(88).build();

    @Test
    void binarySearchNameTest() {
        Collections.sort(list, Comparator.comparing(Person::getName));
        int result = BinarySearch.binarySearch(list, searchKey, Comparator.comparing(Person::getName));
        assertEquals(11, result);
    }

    @Test
    void binarySearchSurnameTest() {
        Collections.sort(list, Comparator.comparing(Person::getSurname));
        int result = BinarySearch.binarySearch(list, searchKey, Comparator.comparing(Person::getSurname));
        assertEquals(11, result);
    }

    @Test
    void binarySearchAgeTest() {
        Collections.sort(list, Comparator.comparing(Person::getAge));
        int result = BinarySearch.binarySearch(list, searchKey, Comparator.comparing(Person::getAge));
        assertEquals(11, result);
    }
}
