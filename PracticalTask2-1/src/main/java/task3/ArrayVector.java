package task3;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ArrayVector<T> implements java.io.Serializable {
    private T [] array;

    public ArrayVector() {
        this.array = null;
    }

    /**
     * Constructor, makes array of elements.
     * @param elements array's elements.
     */
    public ArrayVector(T...elements) {
        array = elements;
    }

    /**
     * Getter for array.
     * @return array of vector's elements.
     */
    public T[] getArray() {
        return array;
    }

    /**
     * Setter for array.
     * @param array new array.
     */
    public void setArray(T... array) {
        this.array = array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(array.length);
        for (Object e : array) {
            sb.append(" ");
            sb.append(e);
        }
        return sb.toString();
    }

    /**
     * Method writes serialized array into ObjectOutput <b>out</b>
     * @param out ObjectOutput to write into.
     */
    public void writeSerialized(ObjectOutput out) {
        try {
            out.writeObject(array);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method reads from ObjectInput <b>In</b> and deserializes into arrayg.
     * @param in ObjectInput to read from.
     */
    public void readSerialized(ObjectInput in) {
        try {
            this.array = (T[]) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
