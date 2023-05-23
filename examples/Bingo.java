import java.util.LinkedList;
import java.util.List;

public class Bingo implements ClosedSystem {

    private boolean[] board = new boolean[25];

    List<Operation<ClosedSystem>> operations;

    public class OperationImpl implements Operation<ClosedSystem> {

        private final int finalI;

        public OperationImpl(int finalI) {
            this.finalI = finalI;
        }

        @Override
        public ClosedSystem apply(ClosedSystem object) {
            Bingo copy = ((Bingo) object).clone();
            copy.board[finalI] = true;
            return (ClosedSystem) copy;
        }

        @Override
        public String repr(){
            return ((Integer) finalI).toString();
        }
    }

    Bingo(boolean for_copy){
        if(for_copy) return;
        operations = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            Operation<ClosedSystem> operation = new OperationImpl(i);
            operations.add(operation);
        }
    }

    Bingo(){
        this(false);
    }

    @Override
    protected Bingo clone(){
        Bingo copy = new Bingo(true);
        copy.board = board.clone();
        copy.operations = operations;
        return copy;
    }

    @Override
    public boolean is_reflexive() {
        return true;
    }

    @Override
    public List<Operation<ClosedSystem>> operations() {
        return operations;
    }

    private static void print_sol(StateTree.Node node){
        if(node.parent == null) return;
        print_sol(node.parent);
        System.out.println(((Operation<Bingo>)node.key).repr());
    }

    public static void main(String[] args){
        Bingo board = new Bingo();
        Condition<Bingo> condition = (b) -> b.board[4] && b.board[9] && b.board[14] && b.board[19] && b.board[24];
        Solver<Bingo> solver = new Solver<>(board, condition);
        StateTree.Node solution = solver.Solve();
        print_sol(solution);
    }
}
