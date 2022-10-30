package task1;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.*;

/**
 * Implementation of FileVisitor used for searching files in directory traversing file tree
 */
public class FileSearch implements FileVisitor<Path> {

    private final String fileName;
    private final Path startDir;

    private Path resultPath;

    public FileSearch(String fileName, Path startDir) {
        this.fileName = fileName;
        this.startDir = startDir;
    }
    public Path getResultPath() {
        return resultPath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileName = file.getFileName().toString();
        if (this.fileName.equals(fileName)) {
            this.resultPath = file;
            return TERMINATE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.out.println("Cannot access file: " + file.toString() + ".");
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        boolean finishedSearch = false;
        try {
            finishedSearch = Files.isSameFile(dir, this.startDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (finishedSearch) {
            System.out.println("File:" + this.fileName + " not found.");
            return TERMINATE;
        }
        return CONTINUE;
    }
}
