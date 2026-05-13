package com.example.activatehooparena;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activatehooparena.model.Booking;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BookingsActivity extends AppCompatActivity {

    private LinearLayout tabUpcoming, tabPast;
    private View indicatorUpcoming, indicatorPast;
    private TextView tvTabUpcoming, tvTabPast;
    private LinearLayout emptyStateView;
    private RecyclerView recyclerBookings;
    private Button btnBookNow;
    private BottomNavigationView bottomNav;

    private boolean showingUpcoming = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);  // ← FIXED

        bindViews();
        setupTabs();
        setupBottomNavigation();
        loadBookings(true);
    }

    private void bindViews() {
        tabUpcoming = findViewById(R.id.tabUpcoming);
        tabPast = findViewById(R.id.tabPast);
        indicatorUpcoming = findViewById(R.id.indicatorUpcoming);
        indicatorPast = findViewById(R.id.indicatorPast);
        tvTabUpcoming = findViewById(R.id.tvTabUpcoming);
        tvTabPast = findViewById(R.id.tvTabPast);
        emptyStateView = findViewById(R.id.emptyStateView);
        recyclerBookings = findViewById(R.id.recyclerBookings);
        btnBookNow = findViewById(R.id.btnBookNow);
        bottomNav = findViewById(R.id.bottomNav);

        btnBookNow.setOnClickListener(v ->
                startActivity(new Intent(this, HomeActivity.class)));  // ← FIXED
    }

    private void setupTabs() {
        tabUpcoming.setOnClickListener(v -> switchTab(true));
        tabPast.setOnClickListener(v -> switchTab(false));
    }

    private void switchTab(boolean upcoming) {
        showingUpcoming = upcoming;

        // Active tab styling
        tvTabUpcoming.setTextColor(upcoming ? Color.parseColor("#1A1A1A") : Color.parseColor("#999999"));  // ← FIXED
        tvTabPast.setTextColor(upcoming ? Color.parseColor("#999999") : Color.parseColor("#1A1A1A"));  // ← FIXED

        indicatorUpcoming.setVisibility(upcoming ? View.VISIBLE : View.INVISIBLE);
        indicatorPast.setVisibility(upcoming ? View.INVISIBLE : View.VISIBLE);

        loadBookings(upcoming);
    }

    private void loadBookings(boolean upcoming) {
        // TODO: fetch from API or local DB
        List<Booking> bookings = new ArrayList<>();

        if (bookings.isEmpty()) {
            emptyStateView.setVisibility(View.VISIBLE);
            recyclerBookings.setVisibility(View.GONE);
        } else {
            emptyStateView.setVisibility(View.GONE);
            recyclerBookings.setVisibility(View.VISIBLE);
            recyclerBookings.setLayoutManager(new LinearLayoutManager(this));
            // TODO: Create proper BookingAdapter
            // adapter = new BookingAdapter(bookings);
            // recyclerBookings.setAdapter(adapter);
        }
    }

    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_bookings);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                navigateTo(MainActivity.class);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                return true;
            } else if (itemId == R.id.nav_menu) {
                navigateTo(MenuActivity.class);
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
}