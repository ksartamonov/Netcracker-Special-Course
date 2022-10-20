import task4.BinaryTree;
import task4.Node;

public class BinaryTreeTest {
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.addElement(3);
        bt.addElement(1);
        bt.addElement(6);
        bt.addElement(-1);
        bt.addElement(4);
        bt.addElement(8);
        System.out.println("Pre-Order Traversal:");
        System.out.println(bt.preorderTraversal());
        System.out.println("Post-Order Traversal:");
        System.out.println(bt.postorderTraversal());
        System.out.println("In-Order Traversal:");
        System.out.println(bt.inorderTraversal());
    }
}
