package me.duncte123.linecounter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Main {
    private AtomicInteger totalSize = new AtomicInteger();
    private AtomicInteger totalFiles = new AtomicInteger();

    public Main() {
        final String userDir = System.getProperty("user.dir");
        final File userDirFile = new File(userDir);
        final String projectName = userDirFile.getName();

        System.out.println("Project: " + projectName);

        final String projectSrcDir = userDir + "/src/main/java";
        final File projectDir = new File(projectSrcDir);

        writeToFOutput((writer) -> {
            try {
                writer.write("Project: " + projectName + "\n");

                walkFiles(projectDir, writer);
                writer.write("Project ");
                writer.write(projectName);
                writer.write(" has a total line count of ");
                writer.write(String.valueOf(totalSize.get()));
                writer.write(" lines with a file total of ");
                writer.write(String.valueOf(totalFiles.get()));
                writer.write(" files");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void walkFiles(File base, BufferedWriter writer) throws IOException {

        for (File file : Objects.requireNonNull(base.listFiles())) {

            System.out.println(file);
            writer.write(file.toString());
            writer.write("\n");

            if (file.isDirectory()) {
                walkFiles(file, writer);
            } else if (file.isFile()) {
                printFileDetails(file, writer);
            }
        }
    }

    private void printFileDetails(File file, BufferedWriter writer) throws IOException {
        System.out.println("File name: " + file.getName());
        final int size = Files.readAllLines(file.toPath()).size();
        System.out.println("Lines: " + size);

        totalSize.set(
                totalSize.get() + size
        );
        totalFiles.incrementAndGet();

        writer.write("File ");
        writer.write(file.getName());
        writer.write(" has ");
        writer.write(String.valueOf(size));
        writer.write(" lines of code.\n");
    }

    private void writeToFOutput(Consumer<BufferedWriter> writerConsumer) {
        try {
            File output = new File("output.txt");

            if (output.exists()) {
                output.delete();
            }

            output.createNewFile();

            try (FileWriter fw = new FileWriter(output, StandardCharsets.UTF_8)) {
                try (BufferedWriter writer = new BufferedWriter(fw)) {
                    writerConsumer.accept(writer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Main();
    }
}
