package strategy;


import java.util.Comparator;
import java.util.List;

public class SortContext<T> {
    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy){
        this.strategy = strategy;
    }

    public void executeSort(List<T> list, Comparator<T> comparator){
        if (strategy == null){
            throw new IllegalStateException("Сортировка не выбрана");
        }
        strategy.sort(list, comparator);
    }
}
