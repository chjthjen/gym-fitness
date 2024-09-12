package com.example.gymfitness.helpers;

import android.util.Patterns;
import android.widget.EditText;

public class ValidationHelpers {
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

    public static boolean validatePhoneNumber(String phoneNumber, EditText phoneEditText) {
        boolean isValid = true;
        if (phoneNumber.isEmpty()) {
            phoneEditText.setError("Phone number cannot be empty");
            isValid = false;
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            phoneEditText.setError("Invalid phone number format");
            isValid = false;
        } else {
            phoneEditText.setError(null);
        }

        return isValid;
    }
    public static boolean validateEmpty(String input, EditText inputEditText, String errorMessage) {
        boolean isValid = true;

        if (input.isEmpty()) {
            inputEditText.setError(errorMessage);
            isValid = false;
        } else {
            inputEditText.setError(null);
        }

        return isValid;
    }

    public static boolean validateEmail(String email, EditText emailEditText) {
        boolean isValid = true;
        if (email.isEmpty()) {
            emailEditText.setError("Email cannot be empty");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email format");
            isValid = false;
        } else
            emailEditText.setError(null);

        return isValid;
    }
}
