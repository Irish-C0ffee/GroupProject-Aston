package universalBinarySearch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UniversalBinarySearch<T> {

    public <R> int search(List<T> list, R target, FieldGetter<T, R> fieldGetter, Comparator<R> comparator) {
        if (list == null || list.isEmpty()) {
            return -1;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = list.get(mid);
            R midValue = fieldGetter.getField(midElement);
            int comparison = comparator.compare(midValue, target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // Упрощенная версия для Comparable типов
    @SuppressWarnings("unchecked")
    public <R extends Comparable<R>> int search(List<T> list, R target, FieldGetter<T, R> fieldGetter) {
        return search(list, target, fieldGetter, Comparator.naturalOrder());
    }

    // Поиск элемента (для Comparable типов)
    public <R extends Comparable<R>> T searchElement(List<T> list, R target, FieldGetter<T, R> fieldGetter) {
        int index = search(list, target, fieldGetter);
        return index != -1 ? list.get(index) : null;
    }

    // Поиск с возможностью получить первый найденный элемент
    public <R> T searchElement(List<T> list, R target, FieldGetter<T, R> fieldGetter, Comparator<R> comparator) {
        int index = search(list, target, fieldGetter, comparator);
        return index != -1 ? list.get(index) : null;
    }

    // Поиск всех вхождений (для списка с дубликатами)
    public <R> int[] searchAll(List<T> list, R target, FieldGetter<T, R> fieldGetter, Comparator<R> comparator) {
        int firstIndex = findFirstOccurrence(list, target, fieldGetter, comparator);
        if (firstIndex == -1) {
            return new int[0];
        }

        int lastIndex = findLastOccurrence(list, target, fieldGetter, comparator);

        int[] result = new int[lastIndex - firstIndex + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = firstIndex + 1;
        }
        return result;
    }

    // Поиск первого вхождения элемента в списке с дубликатами
    private <R> int findFirstOccurrence(List<T> list, R target, FieldGetter<T, R> fieldGetter, Comparator<R> comparator) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = list.get(mid);
            R midValue = fieldGetter.getField(midElement);

            int comparison = comparator.compare(midValue, target);

            if (comparison == 0) {
                result = mid;
                right = mid - 1;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    // Поиск последнего вхождения элемента в списке с дубликатами
    private <R> int findLastOccurrence(List<T> list, R target, FieldGetter<T, R> fieldGetter, Comparator<R> comparator) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = list.get(mid);
            R midValue = fieldGetter.getField(midElement);

            int comparison = comparator.compare(midValue, target);

            if (comparison == 0) {
                result = mid;
                left = mid + 1;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}