package test;

import org.junit.jupiter.api.Test;
import strategy.BubbleSortStrategy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BubbleSortStrategyTest {

    @Test
    void testBubbleSortIntegers() {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 8, 1));
        new BubbleSortStrategy<Integer>().sort(list, Comparator.naturalOrder());
        assertEquals(List.of(1, 2, 5, 8), list);
    }

    @Test
    void testAlreadySorted() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        new BubbleSortStrategy<Integer>().sort(list, Comparator.naturalOrder());
        assertEquals(List.of(1, 2, 3), list);
    }
}

