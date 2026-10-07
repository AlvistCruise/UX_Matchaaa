package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ItemDetailActivity extends AppCompatActivity {

    private int basePrice = 0;
    private AppCompatButton btnPay;
    private EditText etQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView ivDetailImage = findViewById(R.id.ivDetailImage);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailPrice = findViewById(R.id.tvDetailPrice);
        ImageView btnBack = findViewById(R.id.btnBack);
        Spinner spinnerIce = findViewById(R.id.spinnerIce);
        Spinner spinnerSugar = findViewById(R.id.spinnerSugar);
        btnPay = findViewById(R.id.btnPay);
        etQuantity = findViewById(R.id.etQuantity);
        ImageButton btnMenuItem = findViewById(R.id.btnMenuBranch);

        btnMenuItem.setOnClickListener(this::showMenu);

        String itemName = getIntent().getStringExtra("ITEM_NAME");
        String itemPrice = getIntent().getStringExtra("ITEM_PRICE");
        int itemImage = getIntent().getIntExtra("ITEM_IMAGE", R.mipmap.ic_launcher);

        if (itemName != null) tvDetailName.setText(itemName);

        if (itemPrice != null) {
            tvDetailPrice.setText(itemPrice);
            String cleanPrice = itemPrice.replaceAll("[^0-9]", "");
            if (!cleanPrice.isEmpty()) {
                basePrice = Integer.parseInt(cleanPrice);
            }
        }

        updatePayButton(0);

        ivDetailImage.setImageResource(itemImage);

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String qtyStr = s.toString().trim();
                if (qtyStr.isEmpty()) {
                    updatePayButton(0);
                } else {
                    try {
                        int qty = Integer.parseInt(qtyStr);
                        updatePayButton(qty);
                    } catch (NumberFormatException e) {
                        updatePayButton(0);
                    }
                }
            }
        });

        String[] iceLevels = {"Normal Ice", "Less Ice", "No Ice"};
        String[] sugarLevels = {"Normal Sugar", "Less Sugar", "No Sugar"};

        ArrayAdapter<String> iceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, iceLevels) {
            @NonNull
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(android.graphics.Color.BLACK);
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(android.graphics.Color.BLACK);
                return view;
            }
        };
        spinnerIce.setAdapter(iceAdapter);

        ArrayAdapter<String> sugarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sugarLevels) {
            @NonNull
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(android.graphics.Color.BLACK);
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(android.graphics.Color.BLACK);
                return view;
            }
        };
        spinnerSugar.setAdapter(sugarAdapter);

        btnBack.setOnClickListener(v -> finish());

        btnPay.setOnClickListener(v -> {
            String qtyStr = etQuantity.getText().toString().trim();

            if (qtyStr.isEmpty() || qtyStr.equals("0")) {
                new AlertDialog.Builder(ItemDetailActivity.this)
                        .setTitle("Invalid Quantity")
                        .setMessage("Quantity must be filled and cannot be 0.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_payment_success, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            }

            AppCompatButton btnConfirm = dialogView.findViewById(R.id.btnConfirmDialog);
            btnConfirm.setOnClickListener(view -> {
                dialog.dismiss();
                finish();
            });

            dialog.show();
        });
    }

    private void showMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(ItemDetailActivity.this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                Intent intent = new Intent(ItemDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.menu_item) {
                return true;
            } else if (id == R.id.menu_branch) {
                Intent intent = new Intent(ItemDetailActivity.this, BranchActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.menu_logout) {
                Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void updatePayButton(int qty) {
        int totalPrice = basePrice * qty;
        java.text.NumberFormat formatter = java.text.NumberFormat.getInstance(new java.util.Locale("id", "ID"));
        String formattedPrice = formatter.format(totalPrice);
        btnPay.setText(String.format("Pay - Rp. %s", formattedPrice));
    }
}