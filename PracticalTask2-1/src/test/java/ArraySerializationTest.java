import task3.ArrayVector;

import java.io.*;

public class ArraySerializationTest {
    public static void main(String[] args) {
        ArrayVector vec1 = new ArrayVector(1, 2, 3);
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream("f.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find file f.txt");
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
        ArrayVector vec2 = new ArrayVector();

        try {
            fIn = new FileInputStream("f.txt");
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
