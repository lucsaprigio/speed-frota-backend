package com.speed.speed_frota.modules.mobile.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class FileUtils {

    public static String readArchive(String md5) throws IOException {

        String filename = "C:\\envia\\" + md5 + ".txt";
        File file = new File(filename);

        if (!file.exists()) {
            throw new IOException("Arquivo não encontrado: " + filename);
        }

        List<String> lines = Files.readAllLines(file.toPath());

        if(lines.isEmpty()) {
            return "[]";
        }

        JSONArray jsonArray = new JSONArray();

        for  (String line : lines) {
            String[] data= line.split("\\|");

            if(data.length == 4) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", data[1]);
                jsonObject.put("username", data[2]);
                jsonObject.put("password", data[3]);

                jsonArray.put(jsonObject);
            } else {
                throw new IOException("Formato de arquivo inválido na linha: " + line);
            }

        }
        return jsonArray.toString(4);
    }

    public static byte[] generateArchive(String name, String content) throws IOException {
        
        Files.createDirectories(Paths.get("C:\\recebe\\"));

        String fileName = "C:\\recebe\\" + name + ".txt";
        File file = new File(fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        }

        return Files.readAllBytes(file.toPath());

    }
}
