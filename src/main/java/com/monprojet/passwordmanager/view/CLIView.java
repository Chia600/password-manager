package com.monprojet.passwordmanager.view;

import com.monprojet.passwordmanager.dao.PasswordDAO;
import com.monprojet.passwordmanager.dao.PostgresPasswordDAO;
import com.monprojet.passwordmanager.model.PasswordEntry;
import com.monprojet.passwordmanager.security.EncryptionService;
import com.monprojet.passwordmanager.security.PasswordGenerator;
import java.util.List;
import java.util.Scanner;

public class CLIView {
    private final EncryptionService encryptionService;
    private final PasswordDAO passwordDAO;
    private final Scanner scanner;

    public CLIView(String masterKey) {
        this.encryptionService = new EncryptionService(masterKey);
        this.passwordDAO = new PostgresPasswordDAO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Ajouter un mot de passe");
            System.out.println("2. Afficher les mots de passe");
            System.out.println("3. Quitter");
            System.out.print("Choix : ");
            try {
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consomme la nouvelle ligne
                    if (choice == 1) {
                        addPassword();
                    } else if (choice == 2) {
                        showPasswords();
                    } else if (choice == 3) {
                        break;
                    } else {
                        System.out.println("Choix invalide, veuillez réessayer.");
                    }
                } else {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    scanner.nextLine(); // Consomme l'entrée invalide
                }
            } catch (Exception e) {
                System.out.println("Erreur de saisie : " + e.getMessage());
                scanner.nextLine(); // Consomme l'entrée pour éviter une boucle infinie
            }
        }
        scanner.close();
    }

    private void addPassword() {
        System.out.print("Site : ");
        String site = scanner.nextLine();
        System.out.print("Login : ");
        String login = scanner.nextLine();
        String password = PasswordGenerator.generatePassword(12);
        System.out.println("Mot de passe généré : " + password);
        try {
            String encryptedPassword = encryptionService.encrypt(password);
            passwordDAO.addPassword(new PasswordEntry(0, site, login, encryptedPassword));
            System.out.println("Mot de passe ajouté avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void showPasswords() {
        List<PasswordEntry> entries = passwordDAO.getAllPasswords();
        for (PasswordEntry entry : entries) {
            try {
                String decryptedPassword = encryptionService.decrypt(entry.getEncryptedPassword());
                System.out.println("ID: " + entry.getId() + ", Site: " + entry.getSite() + ", Login: " + entry.getLogin() + ", Mot de passe: " + decryptedPassword);
            } catch (Exception e) {
                System.out.println("Erreur lors du déchiffrement : " + e.getMessage());
            }
        }
    }
}