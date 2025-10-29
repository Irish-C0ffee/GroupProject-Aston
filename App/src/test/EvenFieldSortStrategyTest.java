package test;

import org.junit.jupiter.api.Test;
import strategy.EvenFieldSortStrategy;
import strategy.QuickSortStrategy;
import strategy.SortStrategy;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class EvenFieldSortStrategyTest {

    @Test
    void testEvenFieldSortOnlyEvenValues() {
        List<Integer> list;
        list = new ArrayList<>(List.of(9, 4, 1, 2, 7, 8));
        SortStrategy<Integer> base = new QuickSortStrategy<>();
        SortStrategy<Integer> evenSort = new EvenFieldSortStrategy<>(x -> x, base);

        evenSort.sort(list, Comparator.naturalOrder());
        assertEquals(List.of(9, 2, 1, 4, 7, 8), list);
    }
}
