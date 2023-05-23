import java.util.HashSet;
import java.util.Iterator;

public class Solver <T extends ClosedSystem> {

    public int maxDepth;
    private HashSet<T> reachedStates;
    private StateTree<Operation<T>, T> stateTree;
    private Condition<T> condition;

    Solver(T initial, Condition<T> condition, int maxDepth){
        stateTree = new StateTree<>(initial);
        reachedStates = new HashSet<>();
        this.condition = condition;
        this.maxDepth = maxDepth;
    }

    Solver(T initial, Condition<T> condition){
        this(initial, condition, 25);
    }

    public StateTree.Node Solve(){
        while(stateTree.getDepth() <= maxDepth){
            System.out.println("Hitting Depth " + ((Integer)stateTree.getDepth()).toString());
            for (Iterator<StateTree<Operation<T>, T>.Node<Operation<T>, T>> it = stateTree.prev_row_iterator(); it.hasNext(); ) {
                StateTree.Node node = it.next();
                T state = (T) node.currentValue;
                for (Operation<ClosedSystem> operation: state.operations()
                     ) {
                    T result = ((Operation<T>) operation).apply(state);
                    if(result == null){
                        continue;
                    }
                    if (reachedStates.contains(result)) {
                        continue;
                    }
                    StateTree.Node newNode = stateTree.add(node, (Operation<T>) operation, result);
                    reachedStates.add(result);
                    if(condition.apply(result)){
                        return newNode;
                    }
                }
            }
            stateTree.increase_depth();
        }
        return null;
    }
}
