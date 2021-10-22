package com.ex.services;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHash {
    private static final int costFactor = 10;

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(costFactor, password.toCharArray());
    }
}
