package test;

import org.junit.jupiter.api.Test;
import strategy.QuickSortStrategy;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortStrategyTest {

    @Test
    void testQuickSortStrings() {
        List<String> list = new ArrayList<>(List.of("c", "a", "b"));
        new QuickSortStrategy<String>().sort(list, Comparator.naturalOrder());
        assertEquals(List.of("a", "b", "c"), list);
    }
}