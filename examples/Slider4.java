import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Slider4 implements ClosedSystem{

    private enum move{
        up, down, left, right, none
    }
    private byte[] board;
    int empty;
    private move lastMove = move.none;
    List<Operation<ClosedSystem>> operations;

    Slider4(Slider4 copy){
        board = copy.board.clone();
        lastMove = copy.lastMove;
        empty = copy.empty;
        operations = copy.operations;
    }

    Slider4(byte[] arr){
        board = arr.clone();
        lastMove = move.none;
        operations = new LinkedList<>();
        operations.add((a) -> ((Slider4)a).up());
        operations.add((a) -> ((Slider4)a).down());
        operations.add((a) -> ((Slider4)a).left());
        operations.add((a) -> ((Slider4)a).right());
        empty = 0;
        for (int i = 0; i < 16; i++) {
            if (board[i] == 0){
                empty = i;
                break;
            }
        }
    }
    @Override
    public List<Operation<ClosedSystem>> operations() {
        return operations;
    }

    public Slider4 left(){
        if(lastMove == move.right || empty % 4 == 3)
            return null;
        Slider4 result = new Slider4(this);
        result.board[empty] = result.board[empty+1];
        result.board[empty+1] = 0;
        result.empty = empty + 1;
        result.lastMove = move.left;
        return result;
    }

    public Slider4 right(){
        if(lastMove == move.left || empty % 4 == 0)
            return null;
        Slider4 result = new Slider4(this);
        result.board[empty] = result.board[empty-1];
        result.board[empty-1] = 0;
        result.empty = empty - 1;
        result.lastMove = move.right;
        return result;
    }

    public Slider4 up(){
        if(lastMove == move.down || empty > 11)
            return null;
        Slider4 result = new Slider4(this);
        result.board[empty] = result.board[empty+4];
        result.board[empty+4] = 0;
        result.empty = empty+4;
        result.lastMove = move.up;
        return result;
    }

    public Slider4 down(){
        if(lastMove == move.up || empty < 4)
            return null;
        Slider4 result = new Slider4(this);
        result.board[empty] = result.board[empty-4];
        result.board[empty-4] = 0;
        result.empty = empty-4;
        result.lastMove = move.down;
        return result;
    }

    public boolean solved(){
        for (int i = 0; i < 16; i++) {
            if(board[i] - 1 != i && board[i] != 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    private static String[] names = {"up", "down", "left", "right"};

    private static void printMoves(StateTree.Node node, Slider4 ref){
        if(node.parent == null) return;
        printMoves(node.parent, ref);
        System.out.println(names[ref.operations.indexOf(node.key)]);
    }

    public static void main(String[] args){
        Slider4 puzzle = new Slider4(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 15, 14, 16});
        Condition<Slider4> condition = Slider4::solved;
        Solver<Slider4> solver = new Solver<>(puzzle, condition, 50);
        StateTree.Node solution = solver.Solve();
        System.out.println("Solution Found");
        printMoves(solution, puzzle);
    }
}
