package task4;

import java.io.*;

public class SerializeMyClassToBePersisted {
    public static void main(String[] args) {
        MyClassToBePersisted data = new MyClassToBePersisted("profile1", "group1");
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream("file4.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find file file4.txt");
        }

        try {
            ObjectOutput output = new ObjectOutputStream(fOut);
            output.writeObject(data);
            output.close();
            fOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
