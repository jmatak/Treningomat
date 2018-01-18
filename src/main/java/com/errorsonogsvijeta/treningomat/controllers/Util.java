package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {
    private static final String UPLOADED_FOLDER = "images";
    private static final String FILE_SEPARATOR = "/";

    public static String writeToFile(MultipartFile file, String subdir, String fileName, HttpServletRequest request) {
        try {
            byte[] bytes = file.getBytes();
            Path dir = Paths.get(request.getServletContext().getRealPath("") + FILE_SEPARATOR + UPLOADED_FOLDER + "_" + subdir);

            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }

            String extension = getExtension(file.getOriginalFilename()).toLowerCase();

            if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".gif") || extension.equals(".jpeg")) {
                Path path = Paths.get(dir + FILE_SEPARATOR + fileName);
                Files.write(path, bytes);
                return null;
            } else {
                return "Nevaljana ekstenzija slike!";
            }
        } catch (Exception e) {
            return "Nemoguć zapis datoteke na server.";
        }
    }

    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }
        return extension;
    }

    public static String barcode2DtoJson(String amount, Attendant attendant, String street,
                                         String place, String nameRec, String strRec,
                                         String placeRec, String iban, String model, String reference,
                                         String purpose, String description) {
        return "{" +
                "\"renderer\": \"image\"," +
                "\"options\": {" +
                "\"format\": \"png\"," +
                "\"color\": \"#000000\"" +
                "}," +
                "\"data\": {" +
                "\"amount\": " + amount + "," +
                "\"sender\": {" +
                "\"name\": \"" + attendant.getName() + " " + attendant.getSurname() + "\"," +
                "\"street\": \"" + street + "\"," +
                "\"place\": \"" + place + "\"" +
                "}," +
                "\"receiver\": {" +
                "\"name\": \"" + nameRec + "\"," +
                "\"street\": \"" + strRec + "\"," +
                "\"place\": \"" + placeRec + "\"," +
                "\"iban\": \"" + iban + "\"," +
                "\"model\": \"" + model + "\"," +
                "\"reference\": \"" + reference + "\"" +
                "}," +
                "\"purpose\": \"" + purpose + "\"," +
                "\"description\": \"" + description + "\"" +
                "}" +
                "}";
    }


    private static class IllegalFormatException extends Exception {
        private static final long serialVersionUID = -2912579288977085069L;

        public IllegalFormatException() {
            super();
        }

        public IllegalFormatException(String message) {
            super(message);
        }
    }
}
