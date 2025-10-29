package comparator;
import entity.Person;
import java.util.Comparator;

public class PersonComparators {

    public static final Comparator<Person> BY_ID = Comparator.comparingInt(Person::getId);

    public static final Comparator<Person> BY_NAME = Comparator.comparing(Person::getName);

    public static final Comparator<Person> BY_SURNAME= Comparator.comparing(Person::getSurname);

    public static final Comparator<Person> BY_AGE = Comparator.comparingInt(Person::getAge);

}
