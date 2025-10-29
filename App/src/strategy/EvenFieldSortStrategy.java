package strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class EvenFieldSortStrategy<T> implements SortStrategy<T> {

    private final Function<T, Integer> numericFieldExtractor;
    private final SortStrategy<T> baseStrategy;

    public EvenFieldSortStrategy(Function<T, Integer> numericFieldExtractor, SortStrategy<T> baseStrategy) {
        this.numericFieldExtractor = numericFieldExtractor;
        this.baseStrategy = baseStrategy;
    }

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {

        List<T> evenElements = new ArrayList<>();
        List<Integer> evenIndices = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Integer value = numericFieldExtractor.apply(list.get(i));
            if (value != null && value % 2 == 0) {
                evenElements.add(list.get(i));
                evenIndices.add(i);
            }
        }
        baseStrategy.sort(evenElements, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            list.set(evenIndices.get(i), evenElements.get(i));
        }
    }
}
