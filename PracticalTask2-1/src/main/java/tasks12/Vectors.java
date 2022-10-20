package tasks12;

import java.io.*;
import java.util.*;

public class Vectors {
    private final int dim; // Vector's dimension

    private final List<Double> components; // vector's components

    /**
     * Constructor
     * @param components vector's components in <b>List<Double></b>>
     */
    public Vectors(List<Double> components) {
        this.dim = components.size();
        this.components = components;
    }

    /**
     * Constructor
     * @param components vector's components
     */
    public Vectors(Double... components) {
        this.dim = components.length;
        this.components = Arrays.asList(components);
    }

    /**
     * Getter
     * @return vector's dimension
     */
    public int getDim() {
        return dim;
    }

    /**
     * Getter
     * @return vector's components
     */
    public List<Double> getComponents() {
        return components;
    }

    /**
     * Method multiples vector by scalar.
     * @param c scalar to multiply
     * @param v1 vector to multiply
     * @return Multiplied vector <b>c*v1</b>
     */
    public static Vectors multiplyByScalar(double c, Vectors v1) {
        List<Double> newComponents = v1.getComponents().stream().map(aDouble -> aDouble * c).toList();
        return new Vectors(newComponents);
    }

    /**
     * Method sums 2 vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return vector <b>v1+v2</b>
     */
    public static Vectors sum(Vectors v1, Vectors v2) {
        if (v1.getDim() == v2.getDim()) {
            List<Double> newComponents = new ArrayList<>();

            for (int i = 0; i < v1.getDim(); i++) {
                newComponents.add(v1.getComponents().get(i) + v2.getComponents().get(i));
            }

            return new Vectors(newComponents);
        }
        throw new IllegalArgumentException("tasks12.Vectors have different dimensions.");
    }

    /**
     * Method calculates scalar product of two vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return scalar product <b>(v1, v2)</b>
     */
    public static double scalarProduct(Vectors v1, Vectors v2) {
        double result = 0;

        if (v1.getDim() == v2.getDim()) {
            List<Double> newComponents = new ArrayList<>();

            for (int i = 0; i < v1.getDim(); i++) {
                result += v1.getComponents().get(i) * v2.getComponents().get(i);
            }

            return result;
        }
        throw new IllegalArgumentException("tasks12.Vectors have different dimensions.");
    }

    /**
     * Method writes vector into string
     * @return vector in format "<b>*dimension* *component1* *component2*... </b>"
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getDim());
        for (int i = 0; i < this.getDim(); i++) {
            sb.append(" ");
            sb.append(this.getComponents().get(i));
        }
        return sb.toString();
    }

    /**
     * Method writes vector into OutputStream out.
     * @param v vector to output
     * @param out byte output stream
     */
    public static void outputVector(Vectors v, OutputStream out) {
        try {
            out.write(String.valueOf(v.toString()).getBytes());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method reads vector from InputStream in.
     * @param in byte input stream.
     * @return read vector.
     */
    public static Vectors inputVector(InputStream in) {
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();

        try {
            sb.append(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parseVectorFromString(sb.toString());
    }

    /**
     * Method writes vector into Character Stream <b>out</b>
     * @param v vector to write
     * @param out Character Stream (Writer)
     * @throws IOException
     */
    public static void writeVector(Vectors v, Writer out) throws IOException {
        out.write(v.toString());
        out.close();
    }

    /**
     * Method reads vector from Character stream <b>in</b>.
     * @param in Character Stream (Reader)
     * @return read vector.
     * @throws IOException
     */
    public static Vectors readVector(Reader in) throws IOException {
        char[] buffer = new char[100];
        in.read(buffer);
        String str = String.valueOf(buffer);

        return parseVectorFromString(str);
    }

    /**
     * Service method parsing vector from String.
     * @param str string to parse vector from.
     * @return parsed vector.
     */
    private static Vectors parseVectorFromString(String str) {
        StringTokenizer st = new StringTokenizer(str, " ");

        int dimension = Integer.parseInt(st.nextToken());

        List<Double> vAttributes = new ArrayList<>();
        while (dimension > 0) {
            vAttributes.add(Double.parseDouble(st.nextToken()));
            dimension--;
        }

        return new Vectors(vAttributes);
    }

}
