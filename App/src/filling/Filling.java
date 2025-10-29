package filling;

import entity.Person;

import java.util.List;
import java.util.Scanner;

public interface Filling {
   void readData(int size, Scanner sc, List<Person> people);
}
