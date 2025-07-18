package com.monprojet.passwordmanager.model;

public class PasswordEntry {
    private int id;
    private String site;
    private String login;
    private String encryptedPassword;

    public PasswordEntry(int id, String site, String login, String encryptedPassword) {
        this.id = id;
        this.site = site;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
    }

    public int getId() { return id; }
    public String getSite() { return site; }
    public String getLogin() { return login; }
    public String getEncryptedPassword() { return encryptedPassword; }
}