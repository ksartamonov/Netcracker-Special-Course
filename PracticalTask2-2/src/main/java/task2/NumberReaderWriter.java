package task2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writing integers to a typed file, then read and recursively calculate the average.
 */
public class NumberReaderWriter {
    public static void main(String[] args) {
        // Creating list to write into file
        List<Integer> numbers = new ArrayList<>();
        int n = 100000;
        for (int i = 0; i < n; i++) {
            numbers.add(i);
        }

        Path path = Paths.get("src/main/resources/file2.txt");

        // writing
        writeNumbersToFile(numbers, path);

        // reading into byte array
        byte[] data = readBytesFromFile(path);
        try {
            printStat(Arrays.asList(toObjects(data)), getIntsFromBytes(data), getFloatsFromBytes(data)); // printing required statistics
        } catch (IOException e) {
            throw new RuntimeException("Problem with DataInputStream");
        }
    }

    /**
     * Service method writing list<Integer> into array
     * @param list list to write
     * @param path path to file, where to write
     */
    private static void writeNumbersToFile(List<Integer> list, Path path) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            for (Integer integer : list) {
                writer.write(String.valueOf(integer));
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Service method reading file into byte array.
     * @param path file to read.
     * @return array of bytes.
     */
    private static byte[]  readBytesFromFile(Path path) {
        byte[] data;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    /**
     * Service Method reading <b>integer</b> numbers from <b>byte</b> array.
     * @param data buyte array to read
     * @return List of Integer elements
     * @throws IOException in case of problems with DataInputStream.
     */
    private static List<Integer> getIntsFromBytes(byte[] data) throws IOException {
        List<Integer> intList= new ArrayList<>();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        while (dis.available() >=  4) { // int takes 4 bytes
            intList.add(dis.readInt());
        }
        dis.close();
        return intList;
    }

    /**
     * Service Method reading <b>float</b> numbers from <b>byte</b> array.
     * @param data byte array to read
     * @return List of Float elements
     * @throws IOException in case of problems with DataInputStream.
     */
    private static List<Float> getFloatsFromBytes(byte[] data) throws IOException {
        List<Float> intList= new ArrayList<>();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        while (dis.available() >=  4) { // float takes 4 bytes
            intList.add(dis.readFloat());
        }
        dis.close();
        return intList;
    }

    /**
     * Service method casting <b>byte</b> array into <b>Byte</b> array.
     * @param bytesPrim <b>byte</b> array
     * @return <b>Byte</b> array.
     */
    private static Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytesObj = new Byte[bytesPrim.length];
        Arrays.setAll(bytesObj, n -> bytesPrim[n]);
        return bytesObj;
    }

    /**
     * Service method printing required statistics of byte, integer and float numbers.
     * @param byteList List of Bytes.
     * @param intList List of Integers.
     * @param floatList List of floats.
     */
    private static void printStat(List<Byte> byteList, List<Integer> intList, List<Float> floatList) {
        ElementsStat<Byte> byteElementsStat = new ElementsStat<>(byteList);
        System.out.println("Byte elements:");
        System.out.println(byteElementsStat);

        ElementsStat<Integer> intElementsStat = new ElementsStat<>(intList);
        System.out.println("Integer elements:");
        System.out.println(intElementsStat);

        ElementsStat<Float> floatElementsStat = new ElementsStat<>(floatList);
        System.out.println("Float elements:");
        System.out.println(floatElementsStat);
    }
}
