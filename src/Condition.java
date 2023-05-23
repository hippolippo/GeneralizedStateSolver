@FunctionalInterface
public interface Condition<T extends ClosedSystem> {
    boolean apply(T object);
}