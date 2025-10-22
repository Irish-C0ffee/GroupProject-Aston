package strategy;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator){
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(List<T> list, int l, int r, Comparator<T> c){
        if (l >= r) return;
        int p = partition(list, l, r, c);
        quickSort(list, l, p - 1, c);
        quickSort(list, p + 1, r, c);
    }

    private int partition(List<T> list, int l, int r, Comparator<T> c) {
        T pivot = list.get(r);
        int i = l-1;
        for (int j = l; j < r; j++){
            if (c.compare(list.get(j), pivot) <=0){
                i++;
                T t = list.get(i);
                list.set(i, list.get(j));
                list.set(j, t);
            }

        }
        T t = list.get(i + 1); list.set(i + 1, list.get(r)); list.set(r, t);
        return i + 1;
    }

}
