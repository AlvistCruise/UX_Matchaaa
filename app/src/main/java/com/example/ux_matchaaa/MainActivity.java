package com.example.ux_matchaaa; // Sesuaikan dengan package-mu!

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarasi Komponen
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    // Variabel Global statis sederhana untuk menyimpan Username sesuai instruksi dokumen
    public static String globalUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etUsername.setError("Username must be filled");
                    return;
                }

                if (password.isEmpty()) {
                    etPassword.setError("Password must be filled");
                    return;
                }

                if (username.length() <= 6) {
                    etUsername.setError("Username length must be greater than 6");
                    return;
                }

                globalUsername = username; // Simpan ke global variable

                 Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                 startActivity(intent);
                 finish();

                Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}