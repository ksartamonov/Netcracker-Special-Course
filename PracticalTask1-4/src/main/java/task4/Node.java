package task4;

public class Node {
    private Object value;
    private Node left; // left child
    private Node right; // right child

    /**
     * Constructor without any fields.
     */
    public Node() {
    }

    /**
     * Constructor
     * @param value element in node
     */
    public Node(Object value) {
        this.value = value;
    }

    // GETTERS AND SETTERS
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
