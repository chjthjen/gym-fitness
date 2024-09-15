package com.example.gymfitness.fragments.setting;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymfitness.R;

public class PasswordSettingsFragment extends AppCompatActivity {

    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private ImageButton btnShowHideCurrentPassword, btnShowHideNewPassword, btnShowHideConfirmPassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_settings);

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnShowHideCurrentPassword = findViewById(R.id.btnShowHideCurrentPassword);
        btnShowHideNewPassword = findViewById(R.id.btnShowHideNewPassword);
        btnShowHideConfirmPassword = findViewById(R.id.btnShowHideConfirmPassword);

        btnShowHideCurrentPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etCurrentPassword, btnShowHideCurrentPassword);
            }
        });

        btnShowHideNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etNewPassword, btnShowHideNewPassword);
            }
        });

        btnShowHideConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etConfirmPassword, btnShowHideConfirmPassword);
            }
        });

        Button btnChangePass = findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void togglePasswordVisibility(EditText editText, ImageButton imageButton) {
        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageButton.setImageResource(R.drawable.ic_visibility_off);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageButton.setImageResource(R.drawable.ic_visibility);
        }
        editText.setSelection(editText.getText().length());
    }

}
