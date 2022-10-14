package task4;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BinaryTree {
    private Node root; // root node

    /**
     * Constructor of empty Binary Tree
     */
    public BinaryTree() {
    }

    /**
     * Constructor
     * @param root root node of the tree
     */
    public BinaryTree(Node root) {
        this.root = root;
    }

    /**
     * Methods adds new element into binary tree
     * @param item element to add
     */
    public void addElement(Object item) {
        Node newNode = new Node(item);

        if (root == null) {
            root = newNode;
            return;
        }

        insert(root, newNode);
    }

    /**
     * Method inserts new node into binary search tree
     * @param curNode element to insert child
     * @param newNode new Node to add into binary tree
     */
    private void insert(Node curNode, Node newNode) {

        if ((Integer) newNode.getValue() < (Integer) curNode.getValue()) {
            if (curNode.getLeft() == null) {
                curNode.setLeft(newNode);
            } else {
                insert(curNode.getLeft(), newNode);
            }
        }

        if ((Integer) newNode.getValue() > (Integer) curNode.getValue()) {
            if (curNode.getRight() == null) {
                curNode.setRight(newNode);
            } else {
                insert(curNode.getRight(), newNode);
            }
        }

        if (Objects.equals(newNode.getValue(), curNode.getValue())) {
            System.out.println("[warning] element" + newNode.getValue() +"already exists");
        }
    }

    /**
     * Method removes element from the tree.
     * @param item element to remove.
     */
    public void removeElement(Object item) {
        if (this.root != null) {
            this.delete(this.root, item);
        }
    }

    /**
     * Service method for deleting object from the tree.
     * @param curNode searching children of element
     * @param item item to remove
     * @return children element
     */
    private Node delete(Node curNode, Object item) {
        if (curNode.getValue() == item) {
            if (curNode.getLeft() == null && curNode.getRight() == null) {
                return null;
            }

            if (curNode.getLeft() == null) {
                return curNode.getRight();
            }

            if (curNode.getRight() == null) {
                return curNode.getLeft();
            }

            curNode.setValue(findMinElement(curNode.getRight()));

            curNode.setRight(
                    delete(curNode.getRight(),
                            findMinElement(curNode.getRight()).getValue()
                    )
            );

            return curNode;
        }

        if ((Integer) item < (Integer) curNode.getValue()) {
            if (curNode.getLeft() == null) {
                return curNode; // element not found
            }

            curNode.setLeft(delete(curNode.getLeft(), item));

            return curNode;
        }

        if ((Integer) item > (Integer) curNode.getValue()) {
            if (curNode.getRight() == null) {
                return curNode; // element not found
            }

            curNode.setRight(delete(curNode.getRight(), item));

            return curNode;
        }
        return curNode;
    }

    /**
     * Service method finding minimal element from children
     * @param node parent node
     * @return minimal element
     */
    private Node findMinElement(Node node) {
        if (node.getLeft() == null) {
            return node;
        }
        return findMinElement(node.getLeft());
    }

    /**
     * Method for preorder binary tree traversal
     * @return list of nodes of the tree
     */
    public List<Node> preorderTraversal() {
        return traverseDLR(root, new LinkedList<Node>());
    }

    /**
     * Service method for preorder traversal
     * @param curNode current Node
     * @param preorderList list to store nodes
     * @return list of nodes in correct order
     */
    private List<Node> traverseDLR(Node curNode, List<Node> preorderList) {
        preorderList.add(curNode);
        if (curNode.getLeft() != null) {
            traverseDLR(curNode.getLeft(), preorderList);
        }
        if (curNode.getRight() != null) {
            traverseDLR(curNode.getRight(), preorderList);
        }
        return preorderList;
    }

    /**
     * Method for postorder binary tree traversal
     * @return list of nodes of the tree
     */
    public List<Node> postorderTraversal() {
        return traverseLRD(root, new LinkedList<Node>());
    }

    /**
     * Service method for postorder traversal
     * @param curNode current Node
     * @param postorderList list to store nodes
     * @return postorderList list of nodes in correct order
     */
    private List<Node> traverseLRD(Node curNode, List<Node> postorderList) {
        if (curNode.getLeft() != null) {
            traverseLRD(curNode.getLeft(), postorderList);
        }
        if (curNode.getRight() != null) {
            traverseLRD(curNode.getRight(), postorderList);
        }
        postorderList.add(curNode);
        return postorderList;
    }

    /**
     * Method for inorder binary tree traversal
     * @return list of nodes of the tree
     */
    public List<Node> inorderTraversal() {
        return traverseLDR(root, new LinkedList<Node>());
    }

    /**
     * Service method for inorder traversal
     * @param curNode current Node
     * @param inorderList list to store nodes
     * @return postorderList list of nodes in correct order
     */
    private List<Node> traverseLDR(Node curNode, List<Node> inorderList) {
        if (curNode.getLeft() != null) {
            traverseLDR(curNode.getLeft(), inorderList);
        }
        inorderList.add(curNode);
        if (curNode.getRight() != null) {
            traverseLDR(curNode.getRight(), inorderList);
        }
        return inorderList;
    }











}
