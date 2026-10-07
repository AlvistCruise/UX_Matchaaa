package com.example.ux_matchaaa; // Sesuaikan dengan package-mu!

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegUsername, etRegPassword, etRegConfirmPassword;
    private AppCompatButton btnRegister;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegConfirmPassword = findViewById(R.id.etRegConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etRegUsername.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegConfirmPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etRegUsername.setError("Username must be filled");
                    return;
                }
                if (password.isEmpty()) {
                    etRegPassword.setError("Password must be filled");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    etRegConfirmPassword.setError("Confirm Password must be filled");
                    return;
                }

                if (username.length() <= 6) {
                    etRegUsername.setError("Username length must be greater than 6");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    etRegConfirmPassword.setError("Password and Confirm Password must be the same");
                    return;
                }
                MainActivity.globalUsername = username; // Simpan ke variabel global [cite: 97]
                Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}