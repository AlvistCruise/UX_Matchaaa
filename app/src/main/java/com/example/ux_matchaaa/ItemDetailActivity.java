package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
    private ImageButton btnMenuItem;


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

        btnMenuItem = findViewById(R.id.btnMenuBranch);
        btnMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

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
    private void showMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(ItemDetailActivity.this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    Intent intent = new Intent(ItemDetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;

                } else if (id == R.id.menu_item) {

                } else if (id == R.id.menu_branch) {
                    Intent intent = new Intent(ItemDetailActivity.this, BranchActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.menu_logout) {
                    // Logout ke Login Page
                    Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}