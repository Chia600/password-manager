package com.monprojet.passwordmanager.dao;

import com.monprojet.passwordmanager.model.PasswordEntry;
import java.util.List;

public interface PasswordDAO {
    void addPassword(PasswordEntry entry);
    List<PasswordEntry> getAllPasswords();
}