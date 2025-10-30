package search;

import java.util.List;
import java.util.Comparator;

public class BinarySearch<T> {

    // Метод с кастомным компаратором
    public static <T> int binarySearch(List<T> list, T key, Comparator<T> comparator) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

          //  int comparison = list.get(mid).compareTo(key);

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

//    // Метод для поиска в отсортированном массиве (дженерик с ограничением)
//    public static <T extends Comparable<T>> int binarySearch(List<Person> array, Person key) {
//        int left = 0;
//        int right = array.size() - 1;
//
//        while (left <= right) {
//            int mid = left + (right - left) / 2;
//
//            int comparison = array.get(mid).compareTo(key);
//            if (comparison == 0) {
//                return mid; // Элемент найден
//            } else if (comparison < 0) {
//                left = mid + 1; // Искать в правой половине
//            } else {
//                right = mid - 1; // Искать в левой половине
//            }
//        }
//
//        return -1; // Элемент не найден
//    }

//// Метод для поиска в List
//public static <T extends Comparable<T>> int binarySearch(List<T> list, T key) {
//    int left = 0;
//    int right = list.size() - 1;
//
//    while (left <= right) {
//        int mid = left + (right - left) / 2;
//
//        int comparison = list.get(mid).compareTo(key);
//
//        if (comparison == 0) {
//            return mid;
//        } else if (comparison < 0) {
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//
//    return -1;
//}

//    // Рекурсивная версия
//    public static <T extends Comparable<T>> int binarySearchRecursive(T[] array, T key) {
//        return binarySearchRecursive(array, key, 0, array.length - 1);
//    }
//
//    private static <T extends Comparable<T>> int binarySearchRecursive(T[] array, T key, int left, int right) {
//        if (left > right) {
//            return -1;
//        }
//
//        int mid = left + (right - left) / 2;
//        int comparison = array[mid].compareTo(key);
//
//        if (comparison == 0) {
//            return mid;
//        } else if (comparison < 0) {
//            return binarySearchRecursive(array, key, mid + 1, right);
//        } else {
//            return binarySearchRecursive(array, key, left, mid - 1);
//        }
//    }
}