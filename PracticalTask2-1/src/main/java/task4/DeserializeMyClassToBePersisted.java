package task4;

import java.io.*;

public class DeserializeMyClassToBePersisted {
    public static void main(String[] args) {
        MyClassToBePersisted data = new MyClassToBePersisted();

        InputStream fIn = null;
        try {
            fIn = new FileInputStream("file4.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectInput input = new ObjectInputStream(fIn);
            data = (MyClassToBePersisted) input.readObject();
            input.close();
            fIn.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }
}
