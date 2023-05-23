import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StateTree<K, V> {

    private int depth;
    private LinkedList<Node<K, V>> end_row;
    private LinkedList<Node<K, V>> previous_row;
    private Node<K, V> head;
    public class Node<K, V>{
        public K key;
        public V currentValue;
        public List<Node<K, V>> children;
        public Node<K, V> parent;
        public int depth;

        Node(Node<K,V> parent, K key, V value){
            currentValue = value;
            this.key = key;
            children = new LinkedList<>();
            this.parent = parent;
            if(parent != null)
                depth = parent.depth + 1;
            else
                depth = 0;
        }
    }

    StateTree(V initial){
        depth = 0;
        end_row = new LinkedList<>();
        previous_row = null;
        head = new Node<>(null, null, initial);
        end_row.add(head);
        increase_depth();
    }

    public Node<K, V> add(Node<K, V> parent, K key, V value){
        if(parent.depth + 1 != depth){
            return null;
        }
        Node<K, V> newNode = new Node<>(parent, key, value);
        parent.children.add(newNode);
        end_row.add(newNode);
        return newNode;
    }

    public boolean increase_depth(){
        if(end_row.isEmpty()){
            return false;
        }
        if(previous_row != null) {
            for (Node<K, V> node : previous_row
            ) {
                node.currentValue = null;
            }
        }
        previous_row = end_row;
        end_row = new LinkedList<>();
        depth++;
        return true;
    }

    public int getDepth(){
        return depth;
    }

    public Iterator<Node<K, V>> prev_row_iterator(){
        return previous_row.iterator();
    }
}
