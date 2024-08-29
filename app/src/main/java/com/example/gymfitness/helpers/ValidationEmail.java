package com.example.gymfitness.helpers;

import android.util.Patterns;
import android.widget.EditText;

public class ValidationEmail {
    public static boolean validateInputEmail(String usernameOrEmail, String password,
                                             EditText usernameEditText, EditText passwordEditText) {
        boolean isValid = true;

        if (usernameOrEmail.isEmpty()) {
            usernameEditText.setError("Username or Email cannot be empty");
            isValid = false;
        }

        if (usernameOrEmail.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
            usernameEditText.setError("Invalid email format");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
            isValid = false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            isValid = false;
        }

        if (isValid) {
            usernameEditText.setError(null);
            passwordEditText.setError(null);
        }
        return isValid;
    }
}
