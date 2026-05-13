package com.example.activatehooparena;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.datatransport.backend.cct.BuildConfig;

public class BuildInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_info);  // ← FIXED

        // Bind each row with label + value
        bindRow(R.id.itemAppName,    "Application", "Activate Hoop Arena");
        bindRow(R.id.itemCompany,    "Company",     "ServiceOS");
        bindRow(R.id.itemCopyright,  "Copyright",   "2026, ServiceOS Ltd.");
        bindRow(R.id.itemVersion,    "Version",     BuildConfig.VERSION_NAME);
        bindRow(R.id.itemBuild,      "Build",       String.valueOf(BuildConfig.VERSION_CODE));
        bindRow(R.id.itemReactBuild, "React Build", "2082");

        // Back button
        ImageButton btnBack = findViewById(R.id.btnBack);  // ← FIXED
        btnBack.setOnClickListener(v -> finish());  // ← FIXED
    }

    // Helper: find row view by ID
    private View findRow(int rowId) {
        return findViewById(rowId);
    }

    // Bind label and value TextViews inside a row
    private void bindRow(int rowId, String label, String value) {
        View row = findRow(rowId);
        if (row == null) return;

        TextView tvLabel = row.findViewById(R.id.tvLabel);
        TextView tvValue = row.findViewById(R.id.tvValue);

        if (tvLabel != null) tvLabel.setText(label);
        if (tvValue != null) tvValue.setText(value);
    }
}