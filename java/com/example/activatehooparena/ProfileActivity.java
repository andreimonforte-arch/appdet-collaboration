package com.example.activatehooparena;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activatehooparena.data.local.SessionManager;
import com.google.android.datatransport.backend.cct.BuildConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvAvatarInitial, tvUserName, tvUserEmail, tvVersion;
    private View itemAccountDetails, itemSettings, itemRateApp, itemFeedback, itemLogout;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bindViews();
        loadUserInfo();
        setupMenuClicks();
        setupBottomNavigation();
    }

    private void bindViews() {
        tvAvatarInitial = findViewById(R.id.tvAvatarInitial);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvVersion = findViewById(R.id.tvVersion);
        itemAccountDetails = findViewById(R.id.itemAccountDetails);
        itemSettings = findViewById(R.id.item_menu_settings_row);
        itemRateApp = findViewById(R.id.item_menu_rate_the_app_row);
        itemFeedback = findViewById(R.id.item_menu_send_feedback_row);
        itemLogout = findViewById(R.id.item_logout_row);
        bottomNav = findViewById(R.id.bottomNav);
    }

    private void loadUserInfo() {
        SessionManager session = new SessionManager(this);
        String name = session.getUserName();
        String email = session.getUserEmail();

        tvUserName.setText(name);
        tvUserEmail.setText(email);

        // Use your app's BuildConfig (same package, no import needed)
        String versionText = "v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
        tvVersion.setText(versionText);

        if (!name.isEmpty()) {
            tvAvatarInitial.setText(String.valueOf(name.charAt(0)).toUpperCase());
        }
    }

    private void setupMenuClicks() {
        itemAccountDetails.setOnClickListener(v ->
                startActivity(new Intent(this, AccountDetailsActivity.class)));

        itemSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        itemLogout.setOnClickListener(v -> {
            new SessionManager(this).clearSession();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity();
        });

        itemRateApp.setOnClickListener(v -> {
            try {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        itemFeedback.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:support@serviceos.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, "App Feedback");
            startActivity(Intent.createChooser(email, "Send feedback"));
        });
    }

    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                navigateTo(MainActivity.class);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                navigateTo(BookingsActivity.class);
                return true;
            } else return itemId == R.id.nav_menu;
        });
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private static class SettingsActivity {
    }
}
