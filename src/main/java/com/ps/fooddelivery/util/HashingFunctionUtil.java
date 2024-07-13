package com.ps.fooddelivery.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class HashingFunctionUtil {

    // Secure hashing algorithm
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    // Private constructor to prevent instantiation
    private HashingFunctionUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static String hashPassword(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    public static boolean validatePassword(String rawPassword, String hashedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, hashedPassword);
    }
}
