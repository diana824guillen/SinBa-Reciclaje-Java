package com.sinba.util;

import org.apache.commons.validator.routines.EmailValidator;

public class Validators {
    public static boolean isEmailValido(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean noVacio(String... campos) {
        for (String c : campos) {
            if (c == null || c.trim().isEmpty()) return false;
        }
        return true;
    }
}