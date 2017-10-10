package com.uskey512;

import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        // try (ZipFile zipFile = new ZipFile("packed.zip", ".") { // 化ける
        try (ZipFile zipFile = new ZipFile("packed.zip", ".", Charset.forName("Shift-JIS"))) { // 化けない
            zipFile.addFile(args[0]);
            zipFile.build();
        } catch (IOException ioe) {
        }
    }
}
