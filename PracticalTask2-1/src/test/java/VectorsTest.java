import tasks12.Vectors;

import java.io.*;

public class VectorsTest {
    public static void main(String[] args) {
        Vectors v1 = new Vectors(1.0, 2.0, 3.0);
        Vectors v2 = new Vectors(2.0, 3.0, 4.0);

        System.out.println(Vectors.multiplyByScalar(5, v1));
        System.out.println(Vectors.scalarProduct(v1, v2));
        System.out.println(Vectors.sum(v1, v2));


        Vectors v3 = Vectors.inputVector(System.in);
        Vectors.outputVector(v3, System.out);

        try {
            Reader input = new FileReader("/Users/kirill/IdeaProjects/PracticalTask2-1/src/test/resources/input.txt");
            Vectors v4 = Vectors.readVector(input);
            Writer output = new FileWriter("/Users/kirill/IdeaProjects/PracticalTask2-1/src/test/resources/output.txt");
            Vectors.writeVector(v4, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
