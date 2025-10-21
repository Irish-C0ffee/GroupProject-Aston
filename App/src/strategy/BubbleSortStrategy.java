package strategy;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator){
        boolean swapped;

        for (int i = 0; i < list.size()-1; i++){
            swapped = false;
            for (int j = 0; j < list.size() -i -1; j++){
                if (comparator.compare(list.get(j), list.get(j+1)) > 0) {
                    T t = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, t);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
