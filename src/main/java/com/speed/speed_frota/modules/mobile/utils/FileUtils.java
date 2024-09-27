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
            throw new IOException("Arquivo não encontrado.");
        }

        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty()) {
            return "[]";
        }

        JSONArray usersArray = new JSONArray();
        JSONArray vehiclesArray = new JSONArray();
        JSONArray providersArray = new JSONArray();

        for (String line : lines) {
            String[] data = line.split("\\|");

            if (data[1].equals("1")) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", data[2]);
                jsonObject.put("username", data[3]);
                jsonObject.put("password", data[4]);

                usersArray.put(jsonObject);
            } else if (data[1].equals("2")) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", data[2]);
                jsonObject.put("model", data[3]);
                jsonObject.put("license_plate", data[4]);

                vehiclesArray.put(jsonObject);
            } else if (data[1].equals("3")) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", data[2]);
                jsonObject.put("providerName", data[3]);

                providersArray.put(jsonObject);
            } 
            else {
                throw new IOException("Formato de arquivo inválido na linha: " + line);
            }

        }

        JSONObject result = new JSONObject();
        result.put("users", usersArray);
        result.put("vehicles", vehiclesArray);
        result.put("providers", providersArray);

        return result.toString(4); // 4 é a indentaão de JSON
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
