package com.monprojet.passwordmanager.dao;

import com.monprojet.passwordmanager.model.PasswordEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresPasswordDAO implements PasswordDAO {
    private final String url = "jdbc:postgresql://db:5432/password_manager";
    private final String user = "user";
    private final String password = "password";

    public PostgresPasswordDAO() {
        try {
            // Charger explicitement le pilote JDBC
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String sql = "CREATE TABLE IF NOT EXISTS passwords (id SERIAL PRIMARY KEY, site VARCHAR(255), login VARCHAR(255), password VARCHAR(255))";
                conn.createStatement().execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPassword(PasswordEntry entry) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO passwords (site, login, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getSite());
            stmt.setString(2, entry.getLogin());
            stmt.setString(3, entry.getEncryptedPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PasswordEntry> getAllPasswords() {
        List<PasswordEntry> entries = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT id, site, login, password FROM passwords";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                entries.add(new PasswordEntry(rs.getInt("id"), rs.getString("site"), rs.getString("login"), rs.getString("password")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }
}