package com.example.gymfitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymfitness.R;

public class ForgottenPasswordActivity extends AppCompatActivity {

    Button btn_continue;
    ImageView fp_img_backArrow;
    private void addControls()
    {
        btn_continue = findViewById(R.id.btn_continue);
        fp_img_backArrow = findViewById(R.id.fp_img_backArrow);
    }
    private void addEvents()
    {
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgottenPasswordActivity.this, SetPasswordActivity.class);
                startActivity(intent);
            }
        });
        fp_img_backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgotten_password);
        addControls();
        addEvents();
    }
}