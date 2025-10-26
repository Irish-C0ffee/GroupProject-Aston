import java.util.Comparator;
import java.util.List;

public class BinarySearch {

    /**
     * Бинарный поиск с пользовательским компаратором
     * @param list отсортированный список
     * @param key искомый элемент
     * @param comparator компаратор для сравнения элементов
     * @return индекс элемента или -1, если элемент не найден
     */
    public static <T> int binarySearch(List<T> list, T key, Comparator<? super T> comparator) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midValue = list.get(mid);

            int comparison = comparator.compare(midValue, key);

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
}