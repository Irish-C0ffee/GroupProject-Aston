package test;
import entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    Person person = new Person.Builder().name("Петр").surname("Иванов").age(23).build();

    @Test
    public void testGetId() {

        int result = person.getId();
        assertEquals(2, result);
    }

    @Test
    public void testGetName() {
        String result = person.getName();
        assertEquals("Петр", result);
    }

    @Test
    public void testGetSurname() {

        String result = person.getSurname();
        assertEquals("Иванов", result);
    }

    @Test
    public void testGetAge() {

        int result = person.getAge();
        assertEquals(23, result);
    }

}
