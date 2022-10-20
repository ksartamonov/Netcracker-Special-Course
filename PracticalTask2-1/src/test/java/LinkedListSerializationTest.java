import task3.LinkedListVector;

import java.io.*;

public class LinkedListSerializationTest {
    public static void main(String[] args) {
        LinkedListVector<Double> vec1 = new LinkedListVector<>(1.5, 2.5, 3.5);

        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream("file3.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find file file3.txt");
        }

        try {
            ObjectOutput output = new ObjectOutputStream(fOut);
            vec1.writeSerialized(output);
            output.close();
            fOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStream fIn = null;
        LinkedListVector vec2 = new LinkedListVector();

        try {
            fIn = new FileInputStream("file3.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectInput input = new ObjectInputStream(fIn);
            vec2.readSerialized(input);
            input.close();
            fIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(vec2);
    }
}
