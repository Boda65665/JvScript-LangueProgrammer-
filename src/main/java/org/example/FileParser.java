package org.example;


import org.example.Exception.ParseFileError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
    public String parseFile(String nameFile) {
        validNameFile(nameFile);
        String basePath = System.getProperty("user.dir");
        String path = basePath+"\\src\\main\\java\\org\\example\\"+nameFile;

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder codeString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                codeString.append(line).append("\n");
            }
            return codeString.toString();
        } catch (IOException e) {
            throw new ParseFileError("Ошибка чтения файла");
        }
    }

    private void validNameFile(String nameFile) {
        String fileExtension = nameFile.substring(nameFile.length()-3);
        if (nameFile.length()<=3) throw new ParseFileError("Некорректное имя файла");
        if (!fileExtension.equals(".jv")) throw new ParseFileError("Неверное расширение файла");
    }
}
