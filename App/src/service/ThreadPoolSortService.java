package service;

import strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolSortService<T> implements AutoCloseable {
    private final ExecutorService pool;

    public ThreadPoolSortService(int threads) {
        if (threads < 2) threads = 2;
        this.pool = Executors.newFixedThreadPool(threads);
    }
    public void sort(List<T> list, Comparator<T> comparator, SortStrategy<T> strategy) {
        if (list == null || list.size() < 2) return;

        int mid = list.size() / 2;

        List<T> left  = new ArrayList<>(list.subList(0, mid));
        List<T> right = new ArrayList<>(list.subList(mid, list.size()));

        Callable<List<T>> leftTask  = () -> { strategy.sort(left, comparator);   return left;  };
        Callable<List<T>> rightTask = () -> { strategy.sort(right, comparator);  return right; };

        try {
            Future<List<T>> f1 = pool.submit(leftTask);
            Future<List<T>> f2 = pool.submit(rightTask);

            List<T> sortedLeft  = f1.get();
            List<T> sortedRight = f2.get();

            mergeInto(list, sortedLeft, sortedRight, comparator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sorting interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Sorting failed", e.getCause());
        }
    }

    private void mergeInto(List<T> target, List<T> left, List<T> right, Comparator<T> c) {
        target.clear();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (c.compare(left.get(i), right.get(j)) <= 0) {
                target.add(left.get(i++));
            } else {
                target.add(right.get(j++));
            }
        }
        while (i < left.size())  target.add(left.get(i++));
        while (j < right.size()) target.add(right.get(j++));
    }



    @Override
    public void close() {
        pool.shutdown();
    }
}
