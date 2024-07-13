package com.tosan.validation.util;

/**
 * @author Masoumeh Ferdosizadeh
 */
public class Encryption {
    public static String makeSemiEncryptString(String input) {
        if (input == null)
            return null;
        return "*SEMIENCRYPTED:".concat(input.length() > 10 ? input.substring(0, input.length() - 5) : input.substring(0, input.length() / 2));
    }

    public static String getPanEncryptedString(String input) {
        if (input == null)
            return null;
        if (input.length() == 16) {
            input = "*SEMI_ENCRYPTED:".concat(input.substring(0, 6)).concat("******").concat(input.substring(12, 16));
        } else if (input.length() == 19) {
            input = "*SEMI_ENCRYPTED:".concat(input.substring(0, 6)).concat("*********").concat(input.substring(15, 19));
        } else {
            if (input.length() >= 2) {
                input = "*SEMI_ENCRYPTED:".concat(input.substring(0, input.length() / 2));
            } else {
                input = "ENCRYPTED VALUE";
            }
        }
        return input;
    }
}
