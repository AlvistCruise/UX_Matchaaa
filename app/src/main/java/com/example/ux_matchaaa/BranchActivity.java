package com.example.ux_matchaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BranchActivity extends AppCompatActivity {

    private ImageButton btnMenuBranch;
    private RecyclerView rvBranches;

    private final String[] regions = {"Jakarta Pusat", "Jakarta Selatan", "Jakarta Barat"};
    private final String[] addresses = {
            "Jalan Lorem Ipsum Blok QE/67",
            "Jalan SCBD Sudirman No. 88",
            "Jalan Tanjung Duren Raya No. 12"
    };
    private final String[] hours = {"08:00 - 21:00", "09:00 - 22:00", "10:00 - 20:00"};

    private final int[] branchImages = {
            R.mipmap.img_branch_1,
            R.mipmap.img_branch_2,
            R.mipmap.img_branch_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        btnMenuBranch = findViewById(R.id.btnMenuBranch);
        rvBranches = findViewById(R.id.rvBranches);

        btnMenuBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBranchMenu(v);
            }
        });

        BranchAdapter adapter = new BranchAdapter();
        rvBranches.setAdapter(adapter);
    }

    private void showBranchMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(BranchActivity.this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    Intent intent = new Intent(BranchActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.menu_item) {
                    Intent intent = new Intent(BranchActivity.this, ItemActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.menu_branch) {
                    Toast.makeText(BranchActivity.this, "Already on Branch Page", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.menu_logout) {
                    Intent intent = new Intent(BranchActivity.this, MainActivity.class);
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

    private class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {

        @NonNull
        @Override
        public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_branch_card, parent, false);
            return new BranchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
            holder.ivImage.setImageResource(branchImages[position]);
            holder.tvRegion.setText(regions[position]);
            holder.tvAddress.setText(addresses[position]);
            holder.tvHours.setText(hours[position]);
        }

        @Override
        public int getItemCount() {
            return regions.length;
        }

        class BranchViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvRegion, tvAddress, tvHours;

            public BranchViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.ivBranchImage);
                tvRegion = itemView.findViewById(R.id.tvBranchRegion);
                tvAddress = itemView.findViewById(R.id.tvBranchAddress);
                tvHours = itemView.findViewById(R.id.tvBranchHours);
            }
        }
    }
}