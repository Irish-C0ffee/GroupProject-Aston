package search;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class ThreadSearchRunner {

    public static <T, E extends Comparable<E>> void findCount(List<T> list, E key, Function<T, E> mapper) {
        try (ExecutorService threadPool = Executors.newFixedThreadPool(2)) {
            Future<Integer> first = threadPool.submit(() -> new ThreadSearchRunner().findFirstIndex(list, key, mapper));
            Future<Integer> last = threadPool.submit(() -> new ThreadSearchRunner().findLastIndex(list, key, mapper));

            Integer firstIndex = first.get();
            Integer lastIndex = last.get();
            int result;
            if (firstIndex == -1) {
                result = 0;
            } else {
                result = lastIndex - firstIndex + 1;
            }
            System.out.printf("Количество найденных элементов: %d\n", result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T, E extends Comparable<E>> int findFirstIndex(List<T> list, E key, Function<T, E> mapper) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compare = mapper.apply(list.get(mid)).compareTo(key);
            if (compare == 0) {
                result = mid;
                right = mid - 1;
            } else if (compare > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private <T, E extends Comparable<E>> int findLastIndex(List<T> list, E key, Function<T, E> mapper) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compare = mapper.apply(list.get(mid)).compareTo(key);
            if (compare == 0) {
                result = mid;
                left = mid + 1;
            } else if (compare > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}

