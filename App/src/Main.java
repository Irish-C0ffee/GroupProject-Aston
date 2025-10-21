import comparator.PersonComparators;
import Entity.Person;
import strategy.BubbleSortStrategy;
import strategy.SortContext;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person.Builder().name("Тест1").surname("Тест1").age(22).build());
        people.add(new Person.Builder().name("Тест2").surname("Тест2").age(28).build());
        people.add(new Person.Builder().name("Тест3").surname("Тест3").age(20).build());

        System.out.println("==========До сортировки==========");
        people.forEach(System.out::println);
        System.out.println("==========После сортировки==========");
        SortContext<Person> context = new SortContext<>();

        context.setStrategy(new BubbleSortStrategy<>());

        context.executeSort(people, PersonComparators.BY_Age);
        people.forEach(System.out::println);


    }
}