package universalBinarySearch;

@FunctionalInterface
public interface FieldGetter<T, R> {
    R getField(T object);
}
