package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView rvMatchaItems;

    // Data Produk Simulasi
    private final String[] matchaNames = {
            "ChiMatcha Latte", "Mint Matcha Latte", "Strawberry Matcha", "Matcha Latte Cream"
    };
    private final String[] matchaPrices = {
            "Rp. 24.000", "Rp. 24.000", "Rp. 24.000", "Rp. 24.000"
    };
    // Ganti ini dengan ID resource gambar asli milikmu
    private final int[] matchaImages = {
            R.drawable.img_matcha_1, // Placeholder 1
            R.drawable.img_matcha_2, // Placeholder 2
            R.drawable.img_matcha_3, // Placeholder 3
            R.drawable.img_matcha_4  // Placeholder 4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // --- Konfigurasi Navigasi Header ---
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navBranch = findViewById(R.id.navBranch);
        LinearLayout navLogout = findViewById(R.id.navLogout);

        navHome.setOnClickListener(v -> {
            // Kembali ke Home
            finish();
        });

         navBranch.setOnClickListener(v -> {
             startActivity(new Intent(ItemActivity.this, BranchActivity.class));
             finish(); // Tutup halaman Item agar tumpukan activity tidak menumpuk
         });

        navLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ItemActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // --- Konfigurasi RecyclerView Grid ---
        rvMatchaItems = findViewById(R.id.rvMatchaItems);
        MatchaGridAdapter adapter = new MatchaGridAdapter();
        rvMatchaItems.setAdapter(adapter);
    }

    // =========================================================
    // INNER CLASS: Adapter untuk RecyclerView (Format Grid Kartu)
    // =========================================================
    private class MatchaGridAdapter extends RecyclerView.Adapter<MatchaGridAdapter.MatchaViewHolder> {

        @NonNull
        @Override
        public MatchaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matcha_card, parent, false);
            return new MatchaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchaViewHolder holder, int position) {
            holder.tvName.setText(matchaNames[position]);
            holder.tvPrice.setText(matchaPrices[position]);
            holder.ivImage.setImageResource(matchaImages[position]);

            // Deteksi ketika kartu diklik (Akan mengarah ke Item Detail Page) [cite: 123]
            holder.itemView.setOnClickListener(v -> {
//                Toast.makeText(ItemActivity.this, "Clicked: " + matchaNames[position], Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ItemActivity.this, ItemDetailActivity.class);
                intent.putExtra("ITEM_NAME", matchaNames[position]);
                intent.putExtra("ITEM_PRICE", matchaPrices[position]);
                intent.putExtra("ITEM_IMAGE", matchaImages[position]);
                startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            return matchaNames.length;
        }

        class MatchaViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvName, tvPrice;

            public MatchaViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.ivMatchaImage);
                tvName = itemView.findViewById(R.id.tvMatchaName);
                tvPrice = itemView.findViewById(R.id.tvMatchaPrice);
            }
        }
    }
}