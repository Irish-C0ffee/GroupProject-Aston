package test;

import org.junit.jupiter.api.Test;
import service.ThreadPoolSortService;
import strategy.BubbleSortStrategy;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ThreadPoolSortServiceTest {

    @Test
    void testParallelSorting() {
        List<Integer> list = new ArrayList<>(List.of(10, 3, 5, 2, 8));
        try (ThreadPoolSortService<Integer> service = new ThreadPoolSortService<>(2)) {
            service.sort(list, Comparator.naturalOrder(), new BubbleSortStrategy<>());
        }
        assertEquals(List.of(2, 3, 5, 8, 10), list);
    }
}
