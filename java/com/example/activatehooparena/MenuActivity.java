package com.example.activatehooparena;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activatehooparena.data.local.SessionManager;
import com.example.activatehooparena.databinding.ActivityMenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;  // ← Uses auto-generated class
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());  // ← FIXED
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        setupClickListeners();
        setupBottomNavigation();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.rowAccountDetails.setOnClickListener(v -> {
            startActivity(new Intent(this, AccountDetailsActivity.class));
        });

        binding.bottomNav.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:support@serviceos.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, "App Feedback");
            startActivity(Intent.createChooser(email, "Send feedback"));
        });

        binding.rowAccountDetails.setOnClickListener(v -> {
            Toast.makeText(this, "Support coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.rowAccountDetails.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://serviceos.com/terms")));
        });

        binding.bottomNav.setOnClickListener(v -> {
            startActivity(new Intent(this, BuildInfoActivity.class));
        });

        binding.rowAccountDetails.setOnClickListener(v -> confirmLogout());
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log Out", (dialog, which) -> performLogout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        sessionManager.clearSession();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = binding.bottomNav;
        bottomNav.setSelectedItemId(R.id.nav_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                navigateTo(MainActivity.class);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                navigateTo(BookingsActivity.class);
                return true;
            } else if (itemId == R.id.nav_menu) {
                return true;
            }
            return false;
        });
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}