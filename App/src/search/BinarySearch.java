package search;

import java.util.List;
import java.util.Comparator;

public class BinarySearch<T> {

    public static <T> int binarySearch(List<T> list, T key, Comparator<T> comparator) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;


            T midVal = list.get(mid);
            int comparison = comparator.compare(midVal, key);
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