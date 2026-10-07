package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView rvMatchaItems;

    private final String[] matchaNames = {
            "ChiMatcha Latte", "Mint Matcha Latte", "Strawberry Matcha", "Matcha Latte Cream"
    };
    private final String[] matchaPrices = {
            "Rp. 24.000", "Rp. 24.000", "Rp. 24.000", "Rp. 24.000"
    };
    private final int[] matchaImages = {
            R.drawable.img_matcha_1,
            R.drawable.img_matcha_2,
            R.drawable.img_matcha_3,
            R.drawable.img_matcha_4
    };
    private ImageButton btnMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        btnMenuItem = findViewById(R.id.btnMenuBranch);
        btnMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        rvMatchaItems = findViewById(R.id.rvMatchaItems);
        MatchaGridAdapter adapter = new MatchaGridAdapter();
        rvMatchaItems.setAdapter(adapter);
    }
    private void showMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(ItemActivity.this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    Intent intent = new Intent(ItemActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;

                } else if (id == R.id.menu_item) {

                } else if (id == R.id.menu_branch) {
                    Intent intent = new Intent(ItemActivity.this, BranchActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.menu_logout) {
                    // Logout ke Login Page
                    Intent intent = new Intent(ItemActivity.this, MainActivity.class);
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

            holder.itemView.setOnClickListener(v -> {
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