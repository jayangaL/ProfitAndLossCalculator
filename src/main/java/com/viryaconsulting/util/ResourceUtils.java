package com.viryaconsulting.util;

import java.io.*;
import java.nio.file.Files;

import static com.viryaconsulting.util.Constants.FILE_NOT_FOUND;

public class ResourceUtils {

    public static File getFileFromResource(String fileName) throws IOException {
        System.out.println(fileName);
        InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            File file = new File(fileName);
            if (!file.exists())
                throw new IllegalArgumentException(String.format(FILE_NOT_FOUND, fileName));
            return file;
        } else {

            return createFileFromInputStream(inputStream, fileName);
        }
    }

    public static File createTemporyFile(String prefix, String suffix) throws IOException {
        return Files.createTempFile(prefix, suffix).toFile();
    }

    public static String[] getLinesFromFile(File file) throws IOException {
        return Files.readAllLines(file.toPath()).toArray(new String[0]);
    }

    public static String[] getParametersFromLine(String line) {
        return line.trim().split(",");
    }

    public static File createFileFromInputStream(InputStream inputStream, String filename) throws IOException {

        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        File temporyFile = createTemporyFile("-", filename);

        OutputStream outStream = new FileOutputStream(temporyFile);
        outStream.write(buffer);
        outStream.flush();
        inputStream.close();
        return temporyFile;
    }

    private ResourceUtils() {
        throw new UnsupportedOperationException();
    }
}
