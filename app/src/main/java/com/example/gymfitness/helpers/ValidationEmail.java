package com.example.gymfitness.helpers;

import android.util.Patterns;
import android.widget.EditText;

public class ValidationEmail {
    public static boolean validateInputEmail(String usernameOrEmail, String password,
                                             EditText usernameEditText, EditText passwordEditText) {
        if (usernameOrEmail.isEmpty()) {
            usernameEditText.setError("Username or Email cannot be empty");
            return false;
        }

        if (usernameOrEmail.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
            usernameEditText.setError("Invalid email format");
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            return false;
        }


        usernameEditText.setError(null);
        passwordEditText.setError(null);
        return true;
    }
}
