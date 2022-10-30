package task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaDocCopier {
    public static void main(String[] args) {
        Path pathFrom = Paths.get("src/main/resources/index.txt");
        Path pathInto = Paths.get("src/main/resources/task3_out.txt");

        writeLinesToFile(
                pathInto,
                readUntil(pathFrom, "java", 3)
        );

//        String str = "I am java documentation";
//        StringBuilder sb = new StringBuilder();
//        String remains = str;
//        int wordMentions = 0;
//        String buffer = null;
//        while (remains.contains("java") && wordMentions != 3) {
//            System.out.println("remain: " + remains);
//            System.out.println("index of Word: " + remains.indexOf("java"));
//            buffer = remains.substring(0, remains.indexOf("java") + 4);
//            System.out.println("buffer: " + buffer);
//            remains = remains.substring(buffer.length());
//            System.out.println("remains: " + remains);
//            sb.append(buffer);
//            wordMentions++;
//        }
//        sb.append(remains);
//        System.out.println("wow");
    }

    /**
     * Method reading file until the String <b>word</b> mets <b>maxWordMention</b> times.
     * @param path file to read.
     * @param word String to count.
     * @param maxWordMentions number of word meetings.
     * @return
     */
    private static List<String> readUntil(Path path, String word, int maxWordMentions) {
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file.");
        }

        List<String> lines = new ArrayList<>();
        int wordMentions = 0;
        while (wordMentions < maxWordMentions) {
            String nextLine = null;
            try {
                nextLine = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Cannot read a line.");
            }
            if (nextLine.toLowerCase().contains(word)) {
                StringBuilder sb = new StringBuilder(); // line can contain more than one chosen word^ builder will contain only
                String remains = nextLine; // remaining part of String
                String buffer = null;
                while (remains.toLowerCase().contains(word) && wordMentions != maxWordMentions) {
                    buffer = remains.substring(0, remains.indexOf(word) + word.length());
                    remains = remains.substring(buffer.length());
                    sb.append(buffer);
                    wordMentions++;
                    if (wordMentions != maxWordMentions) { // adding rest part of the string if needed
                        sb.append(remains);
                    }
                }
                lines.add(sb.toString()); // Adding only required part of line
            } else { // just adding full line
                lines.add(nextLine);
            }
        }
        return lines;
    }

    /**
     * Method writing a list of Strings into file.
     * @param path file to write into.
     * @param lines Strings to write.
     */
    private static void writeLinesToFile(Path path, List<String> lines) {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter finalWriter = writer;
        lines.forEach(s -> {
            try {
                finalWriter.write(s);
                finalWriter.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            writer.close();
            finalWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
