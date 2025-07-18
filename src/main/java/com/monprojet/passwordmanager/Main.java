package com.monprojet.passwordmanager;

import com.monprojet.passwordmanager.view.CLIView;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Lire la clé maître depuis Docker Secrets
            String masterKey = new String(Files.readAllBytes(Paths.get("/run/secrets/master_key")));
            CLIView view = new CLIView(masterKey);
            view.start();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}