package com.example.activatehooparena;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class TimeCardActivity extends AppCompatActivity {

    private GridLayout calendarGrid, timeGrid;
    private Spinner monthSpinner, filterMonthSpinner, filterYearSpinner;
    private TextView tvYearDisplay, tvSelectedDate;
    private Button btnConfirmDateTime;

    private int selectedDay   = -1;
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_card);

        bindViews();
        setupMonthSpinner();
        buildCalendar(Calendar.getInstance());
        buildTimeSlots();
    }

    private void bindViews() {
        calendarGrid        = findViewById(R.id.calendarGrid);
        timeGrid            = findViewById(R.id.timeGrid);
        monthSpinner        = findViewById(R.id.monthSpinner);
        filterMonthSpinner  = findViewById(R.id.filterMonthSpinner);
        filterYearSpinner   = findViewById(R.id.filterYearSpinner);
        tvYearDisplay       = findViewById(R.id.tvYearDisplay);
        tvSelectedDate      = findViewById(R.id.tvSelectedDate);
        btnConfirmDateTime  = findViewById(R.id.btnConfirmDateTime);

        btnConfirmDateTime.setOnClickListener(v -> confirmSelection());
    }

    private void setupMonthSpinner() {
        String[] months = {"January","February","March","April","May","June",
                "July","August","September","October","November","December"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
        filterMonthSpinner.setAdapter(adapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, pos);
                buildCalendar(cal);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void buildCalendar(Calendar cal) {
        calendarGrid.removeAllViews();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0=Sun
        int daysInMonth    = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int today          = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // Empty cells before the 1st
        for (int i = 0; i < firstDayOfWeek; i++) {
            calendarGrid.addView(makeEmptyCell());
        }

        // Day cells
        for (int day = 1; day <= daysInMonth; day++) {
            final int d = day;
            TextView cell = makeDayCell(String.valueOf(day));
            cell.setOnClickListener(v -> {
                selectedDay = d;
                tvSelectedDate.setText("Selected: " + getMonthName(cal) + " " + d + ", 2026");
            });
            calendarGrid.addView(cell);
        }
    }

    private void buildTimeSlots() {
        timeGrid.removeAllViews();
        String[] slots = {"8:00 AM","9:00 AM","10:00 AM","11:00 AM",
                "1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM"};

        for (String slot : slots) {
            Button btn = new Button(this);
            btn.setText(slot);
            btn.setTextSize(12);
            btn.setAllCaps(false);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width  = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(4, 4, 4, 4);
            btn.setLayoutParams(params);

            btn.setOnClickListener(v -> {
                selectedTime = slot;
                btn.setBackgroundColor(Color.parseColor("#FF9800")); // highlight selected
            });

            timeGrid.addView(btn);
        }
    }

    private void confirmSelection() {
        if (selectedDay == -1 || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a date and time.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Pass selection back to caller
        Intent result = new Intent();
        result.putExtra("day", selectedDay);
        result.putExtra("time", selectedTime);
        setResult(RESULT_OK, result);
        finish();
    }

    // Helpers
    private TextView makeEmptyCell() {
        TextView tv = new TextView(this);
        GridLayout.LayoutParams p = new GridLayout.LayoutParams();
        p.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        p.width = 0;
        tv.setLayoutParams(p);
        return tv;
    }

    private TextView makeDayCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(8, 12, 8, 12);
        tv.setTextColor(Color.parseColor("#1A1A1A"));
        GridLayout.LayoutParams p = new GridLayout.LayoutParams();
        p.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        p.width = 0;
        tv.setLayoutParams(p);
        return tv;
    }

    private String getMonthName(Calendar cal) {
        return new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)];
    }
}