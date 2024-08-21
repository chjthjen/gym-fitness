package com.example.gymfitness.activities;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.gymfitness.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText edtPassword = findViewById(R.id.edt_password);
        edtPassword.setTransformationMethod(new CustomPasswordTransformation());

    }
}


// Chuyển đổi kí tự . sang * khi nhập password
class CustomPasswordTransformation extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private static class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source;
        }

        @Override
        public char charAt(int index) {
            return '*';
        }

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end);
        }
    }
}