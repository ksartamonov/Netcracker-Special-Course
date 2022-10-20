package task3;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;


public class LinkedListVector<T> implements java.io.Serializable {
    private LinkedList list = new LinkedList<>();

    /**
     * Constructor with no arguments, creates empty list
     */
    public LinkedListVector() {
        this.list = new LinkedList<>();
    }

    /**
     * Constructor, adds all parameters into list.
     * @param elements elements to add into list.
     */
    @SafeVarargs
    public LinkedListVector(T... elements) {
        list.addAll(Arrays.asList(elements));
    }

    /**
     * Getter for list.
     * @return list.
     */
    public LinkedList<T> getList() {
        return list;
    }

    /**
     * Setter for list.
     * @param list list to set.
     */
    public void setList(LinkedList<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(list.size());
        for (Object node : list) {
            sb.append(" ");
            sb.append(node);
        }
        return sb.toString();
    }

    /**
     * Method writes serialized list into ObjectOutput <b>out</b>
     * @param out ObjectOutput to write into.
     */
    public void writeSerialized(ObjectOutput out) {
        try {
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method reads from ObjectInput <b>In</b> and deserializes into list.
     * @param in ObjectInput to read from.
     */
    public void readSerialized(ObjectInput in) {
        try {
            this.list = (LinkedList) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
