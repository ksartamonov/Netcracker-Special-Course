package task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterFrequencyCounter {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/java/task4/CharacterFrequencyCounter.java"); // counting characters in file of this class
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file.");
        }
        int charCounter = 0;
        int c; // read character

        while (true) {
            try {
                if ((c = reader.read()) == -1) break; // if reached the end
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if ((char) c == args[0].charAt(0)) {
                charCounter++;
            }
        }
        System.out.println(args[0].charAt(0));
        System.out.println("Character " + args[0].charAt(0) + " occurs in file " + charCounter + " times.");
    }
}
