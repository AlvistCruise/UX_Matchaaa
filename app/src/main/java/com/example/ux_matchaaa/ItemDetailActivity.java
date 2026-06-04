package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ItemDetailActivity extends AppCompatActivity {

    private ImageView ivDetailImage, btnBack;
    private TextView tvDetailName, tvDetailPrice;
    private Spinner spinnerIce, spinnerSugar;
    private EditText etQuantity, etNotes;
    private AppCompatButton btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ivDetailImage = findViewById(R.id.ivDetailImage);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);
        btnBack = findViewById(R.id.btnBack);
        spinnerIce = findViewById(R.id.spinnerIce);
        spinnerSugar = findViewById(R.id.spinnerSugar);
        etQuantity = findViewById(R.id.etQuantity);
        etNotes = findViewById(R.id.etNotes);
        btnPay = findViewById(R.id.btnPay);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navBranch = findViewById(R.id.navBranch);
        LinearLayout navLogout = findViewById(R.id.navLogout);

        String itemName = getIntent().getStringExtra("ITEM_NAME");
        String itemPrice = getIntent().getStringExtra("ITEM_PRICE");
        int itemImage = getIntent().getIntExtra("ITEM_IMAGE", R.mipmap.ic_launcher);

        if (itemName != null) tvDetailName.setText(itemName);
        if (itemPrice != null) {
            tvDetailPrice.setText(itemPrice);
            btnPay.setText("Pay - " + itemPrice); // Ubah text tombol pay dinamis
        }
        ivDetailImage.setImageResource(itemImage);

        String[] iceLevels = {"Ice level dropdown", "Normal Ice", "Less Ice", "No Ice"};
        String[] sugarLevels = {"Sugar level dropdown", "Normal Sugar", "Less Sugar", "No Sugar"};

        ArrayAdapter<String> iceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, iceLevels) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(android.graphics.Color.GRAY);
                } else {
                    tv.setTextColor(android.graphics.Color.BLACK);
                }
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(android.graphics.Color.GRAY);
                } else {
                    tv.setTextColor(android.graphics.Color.BLACK);
                }
                return view;
            }
        };
        spinnerIce.setAdapter(iceAdapter);
        ArrayAdapter<String> sugarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sugarLevels) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(android.graphics.Color.GRAY);
                } else {
                    tv.setTextColor(android.graphics.Color.BLACK);
                }
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(android.graphics.Color.GRAY);
                } else {
                    tv.setTextColor(android.graphics.Color.BLACK);
                }
                return view;
            }
        };
        spinnerSugar.setAdapter(sugarAdapter);
        btnBack.setOnClickListener(v -> finish());
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(ItemDetailActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        navBranch.setOnClickListener(v -> {
            Intent intent = new Intent(ItemDetailActivity.this, BranchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        navLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnPay.setOnClickListener(v -> {
            String qtyStr = etQuantity.getText().toString().trim();
            if (qtyStr.isEmpty()) {
                etQuantity.setError("Quantity must be filled");
                return;
            }
            new AlertDialog.Builder(ItemDetailActivity.this)
                    .setTitle("Order Confirmation")
                    .setMessage("Are you sure you want to buy " + qtyStr + " " + itemName + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Toast.makeText(ItemDetailActivity.this, "Transaction Successful!", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke halaman item setelah beli
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}