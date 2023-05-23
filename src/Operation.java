@FunctionalInterface
public interface Operation<T extends ClosedSystem> {

    public default String repr(){
        return "Operation Object";
    }
    T apply(T object);
}