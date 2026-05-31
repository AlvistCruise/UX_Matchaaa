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

        // Logika Button Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etRegUsername.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegConfirmPassword.getText().toString().trim();

                // Validasi harus diisi
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

                // Validasi panjang username
                if (username.length() <= 6) {
                    etRegUsername.setError("Username length must be greater than 6");
                    return;
                }

                // Validasi kesamaan password
                if (!password.equals(confirmPassword)) {
                    etRegConfirmPassword.setError("Password and Confirm Password must be the same");
                    return;
                }

                // Jika semua validasi lolos
                MainActivity.globalUsername = username; // Simpan ke variabel global [cite: 97]
                Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_SHORT).show();

                // Nanti kita buka comment ini kalau HomeActivity sudah dibuat
                // Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                // startActivity(intent);
                // finish();
            }
        });

        // Logika kembali ke halaman Login
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Karena kita datang dari Login, kita bisa langsung 'finish' activity ini
                finish();
            }
        });
    }
}