package task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SourceCodePrinter {

    public static void main(String[] args) {
        SourceCodePrinter scp = new SourceCodePrinter();
    }

    /**
     * Constructor - performs the main work of printing Source Code
     */
    public SourceCodePrinter() {
        Path curDirectory = Paths.get("").toAbsolutePath(); // getting current directory
        String curFileName = this.getClass().getSimpleName() + ".java"; // getting current class name

        FileSearch fs = new FileSearch(curFileName, curDirectory); // searching our class in directory
        Path curFilePath = null;
        try { // getting our file path
            Files.walkFileTree(curDirectory, fs);
            curFilePath = fs.getResultPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(readFile(curFilePath.toString())); // printing source code
    }

    /**
     * Service method for reading dile into string
     * @param path path to file
     * @return String - file content
     */
    private static String readFile(String path) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + path + ".");
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

}
