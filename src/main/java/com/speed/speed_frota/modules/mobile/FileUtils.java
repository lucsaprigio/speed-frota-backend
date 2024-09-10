package com.speed.speed_frota.modules.mobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static byte[] generateArchive(String name, String content) throws IOException {
        
        Files.createDirectories(Paths.get("C:\\temp\\"));

        String fileName = "C:\\temp\\" + name + ".txt";
        File file = new File(fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        }

        return Files.readAllBytes(file.toPath());

    }
}
