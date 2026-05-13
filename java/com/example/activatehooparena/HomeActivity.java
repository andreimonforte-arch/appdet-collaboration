package com.example.activatehooparena;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView recyclerHoophub;
    private HoophubAdapter adapter;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etSearch = findViewById(R.id.etSearch);
        recyclerHoophub = findViewById(R.id.recyclerHoophub);
        bottomNav = findViewById(R.id.bottomNav);

        setupBottomNav();
        setupGrid();
        setupSearch();
    }

    private void setupGrid() {
        recyclerHoophub.setLayoutManager(new GridLayoutManager(this, 2));

        // FIX: Use static method or create list directly
        List<Court> courtList = getDefaultCourts();
        adapter = new HoophubAdapter(courtList, this::openCourtDetail);
        recyclerHoophub.setAdapter(adapter);
    }

    private List<Court> getDefaultCourts() {
        List<Court> courts = new ArrayList<>();
        courts.add(new Court("1", "Court A", "Manila", "url", 500, 4.5, true));
        courts.add(new Court("2", "Court B", "Quezon City", "url", 600, 4.0, true));
        courts.add(new Court("3", "Court C", "Makati", "url", 700, 4.8, true));
        return courts;
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void openCourtDetail(Court court) {
        Intent intent;
        // FIX: Use a string type or enum, not undefined constants
        String type = court.getType() != null ? court.getType() : "solo";

        switch (type) {
            case "solo":
                intent = new Intent(this, SoloActivity.class);
                break;
            case "half":
                intent = new Intent(this, HalfCourtActivity.class);
                break;
            default:
                intent = new Intent(this, WholeCourtActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void setupBottomNav() {
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_bookings) {
                startActivity(new Intent(this, BookingsActivity.class));
                return true;
            } else if (id == R.id.nav_menu) {
                startActivity(new Intent(this, MenuActivity.class));
                return true;
            }
            return false;
        });
    }
}