package com.uskey512;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile implements AutoCloseable {

    private byte[] buffer;
    private List<File> targetFiles;
    private ZipOutputStream zipOutputStream;

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public ZipFile(String fileName, String path) throws IOException {
        this(fileName, path, Charset.defaultCharset(), DEFAULT_BUFFER_SIZE);
    }

    public ZipFile(String fileName, String path, int bufferSize) throws IOException {
        this(fileName, path, Charset.defaultCharset(), bufferSize);
    }

    public ZipFile(String fileName, String path, Charset charset) throws IOException {
        this(fileName, path, charset, DEFAULT_BUFFER_SIZE);
    }

    public ZipFile(String fileName, String path, Charset charset, int bufferSize) throws IOException {
        File zipFile = new File(path + File.separator + fileName);
        buffer = new byte[bufferSize];
        targetFiles = new ArrayList<File>();
        zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)), charset);
    }

    public void addFile(String filePath) {
        targetFiles.add(new File(filePath));
    }

    public void build() {
        writeFiles(targetFiles.toArray(new File[0]));
    }

    private void writeFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                writeFiles(file.listFiles());
            } else {
                try {
                    ZipEntry entry = new ZipEntry(file.getPath().replace('\\', '/'));
                    zipOutputStream.putNextEntry(entry);
                    try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                        int readLength = 0;
                        while (0 < (readLength = is.read(buffer))) {
                            zipOutputStream.write(buffer, 0, readLength);
                        }
                    }
                } catch (IOException ioe) {
                    throw new RuntimeException("", ioe);
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        zipOutputStream.close();
    }
}