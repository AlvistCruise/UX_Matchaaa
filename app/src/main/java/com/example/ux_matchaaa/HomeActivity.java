package com.example.ux_matchaaa; // Sesuaikan dengan package-mu!

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.viewpager2.widget.ViewPager2;

public class HomeActivity extends AppCompatActivity {

    private TextView tvGreeting;
    private ImageButton btnPrev, btnNext;
    private ViewPager2 viewPagerCarousel;

    // Array gambar untuk Carousel (Ganti namanya sesuai yang ada di folder drawable-mu)
    private final int[] carouselImages = {
            R.drawable.img_carousel_1,
            R.drawable.img_carousel_2,
            R.drawable.img_carousel_3,
            R.drawable.img_carousel_4
    };

    // Handler untuk auto-slide
    private Handler slideHandler = new Handler(Looper.getMainLooper());
    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPagerCarousel.getCurrentItem();
            int nextItem = currentItem + 1;

            // Kembali ke gambar pertama jika sudah di ujung
            if (nextItem >= carouselImages.length) {
                nextItem = 0;
            }
            viewPagerCarousel.setCurrentItem(nextItem, true); // 'true' untuk animasi smooth

            // Ulangi setiap 3 detik (3000 ms)
            slideHandler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvGreeting = findViewById(R.id.tvGreeting);
//        btnMenu = findViewById(R.id.btnMenu);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        viewPagerCarousel = findViewById(R.id.viewPagerCarousel);

        LinearLayout navItem = findViewById(R.id.navItem);
        LinearLayout navBranch = findViewById(R.id.navBranch);
        LinearLayout navLogout = findViewById(R.id.navLogout);

        // Nanti buka comment ini kalau halamannya sudah dibuat

        navItem.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ItemActivity.class));
        });

        navBranch.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BranchActivity.class));
        });


        navLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // 1. Tampilkan Username dari Variabel Global sesuai instruksi dokumen
        String greetingText = "Welcome, " + MainActivity.globalUsername;
        tvGreeting.setText(greetingText);

        // 2. Setup Popup Menu (Dropdown)
//        btnMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDropdownMenu(v);
//            }
//        });

        // 3. Setup Carousel Adapter & ViewPager
        CarouselAdapter adapter = new CarouselAdapter(carouselImages);
        viewPagerCarousel.setAdapter(adapter);

        // Menambahkan efek animasi (Zoom-out ringan saat berpindah)
        viewPagerCarousel.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        // 4. Tombol Next dan Previous Carousel
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = viewPagerCarousel.getCurrentItem() + 1;
                if (next < carouselImages.length) {
                    viewPagerCarousel.setCurrentItem(next, true);
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prev = viewPagerCarousel.getCurrentItem() - 1;
                if (prev >= 0) {
                    viewPagerCarousel.setCurrentItem(prev, true);
                }
            }
        });

        // Daftarkan aksi ViewPager agar timer di-reset saat di-swipe manual
        viewPagerCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 3000); // Mulai hitung 3 detik lagi
            }
        });
    }

    // Fungsi memunculkan Dropdown Menu
//    private void showDropdownMenu(View anchorView) {
//        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, anchorView);
//        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.menu_home) {
//                    Toast.makeText(HomeActivity.this, "Already on Home", Toast.LENGTH_SHORT).show();
//                    return true;
//                } else if (id == R.id.menu_item) {
//                    // Intent ke ItemActivity (Nanti di-uncomment kalau sudah ada halamannya)
//                    // startActivity(new Intent(HomeActivity.this, ItemActivity.class));
//                    return true;
//                } else if (id == R.id.menu_branch) {
//                    // Intent ke BranchActivity
//                    // startActivity(new Intent(HomeActivity.this, BranchActivity.class));
//                    return true;
//                } else if (id == R.id.menu_logout) {
//                    // Kembali ke MainActivity (Login)
//                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        popupMenu.show();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable); // Hentikan timer jika aplikasi diminimize
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000); // Lanjutkan timer
    }

    // =========================================================
    // INNER CLASS: Adapter untuk Carousel ViewPager2
    // =========================================================
    private class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

        private int[] images;

        public CarouselAdapter(int[] images) {
            this.images = images;
        }

        @NonNull
        @Override
        public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Kita buat ImageView secara programatik agar tidak perlu file XML tambahan
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new CarouselViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
            holder.imageView.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class CarouselViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public CarouselViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = (ImageView) itemView;
            }
        }
    }
}