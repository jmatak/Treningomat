package com.errorsonogsvijeta.treningomat.controllers;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.*;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
public class Util {

    // todo
    // treba promijeniti nacin na koji se odabire path jer nije uvijek isti
    // npr. pri pokretanju se dodjeljuje novi privremeni radni direktorij
    // tako da bi se nebi slike mogle ucitati ako je korisnik registriran u proslom pokretanju
    // ne znam gdje bi bilo primjereno spremati... mozda neka fiksna ruta?
    public static String writeToFile(MultipartFile file, HttpServletRequest request, String folder, String fileName) {
        try {
            byte[] bytes = file.getBytes();
            Path dir = Paths.get(request.getServletContext().getRealPath("") + "/" + folder);

            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }

            String extension = getExtension(file.getOriginalFilename());
            if (!(extension.equals(".jpg") || extension.equals(".png") || extension.equals(".gif"))) {
                return null;
            }

            String name = fileName + extension;
            Path path = Paths.get(dir + "/" + name);
            System.out.println(path.toString());
            Files.write(path, bytes);

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }
        return extension;
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
