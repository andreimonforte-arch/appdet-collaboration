package com.example.activatehooparena;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.activatehooparena.data.repository.UserRepository;
import com.example.activatehooparena.databinding.ActivityAccountDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountDetailsActivity extends AppCompatActivity {

    private ActivityAccountDetailsBinding binding;
    private AccountViewModel viewModel;

    private static final int REQUEST_IMAGE_PICK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        observeLiveData();
        setupClickListeners();
        setupBottomNavigation();
    }

    private void initViewModel() {
        UserRepository repo = new UserRepository(this);
        viewModel = new ViewModelProvider(this, new AccountViewModelFactory(repo))
                .get(AccountViewModel.class);
        viewModel.loadUser();
    }

    private void observeLiveData() {
        viewModel.getUserName().observe(this, name -> binding.tvName.setText(name));
        viewModel.getUserEmail().observe(this, email -> binding.tvEmail.setText(email));

        viewModel.getToastMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getNavigateToMain().observe(this, shouldNavigate -> {
            if (Boolean.TRUE.equals(shouldNavigate)) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });

        viewModel.getShowDeleteDialog().observe(this, shouldShow -> {
            if (Boolean.TRUE.equals(shouldShow)) {
                showDeleteAccountDialog();
            }
        });
    }

    private void setupClickListeners() {
        binding.tvEditName.setOnClickListener(v -> showEditNameDialog());
        binding.tvEditPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Password change coming soon", Toast.LENGTH_SHORT).show();
        });
        binding.tvChangePhoto.setOnClickListener(v -> openImagePicker());
        binding.tvSaveAddress.setOnClickListener(v -> saveAddress());
        binding.tvSavePhone.setOnClickListener(v -> savePhone());

        binding.switchCommunications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked ? "enabled" : "disabled";
            Toast.makeText(this, "Marketing communications " + status, Toast.LENGTH_SHORT).show();
        });

        binding.tvDeleteAccount.setOnClickListener(v -> viewModel.onDeleteAccountClicked());
    }

    private void showEditNameDialog() {
        EditText input = new EditText(this);
        input.setText(binding.tvName.getText());
        input.setHint("Enter your name");

        new AlertDialog.Builder(this)
                .setTitle("Edit Name")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    viewModel.updateName(input.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure? This will permanently delete your account and all associated data. This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> viewModel.confirmDeleteAccount())
                .setNegativeButton("Cancel", null)
                .setIcon(R.drawable.ic_shield_24dp)
                .show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void saveAddress() {
        String barangay = binding.etBarangay.getText().toString().trim();
        String flat = binding.etFlat.getText().toString().trim();
        String zip = binding.etPostCode.getText().toString().trim();

        if (barangay.isEmpty() || flat.isEmpty() || zip.isEmpty()) {
            Toast.makeText(this, "Please fill in all address fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Address saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void savePhone() {
        String phone = binding.etPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Phone number saved", Toast.LENGTH_SHORT).show();
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
                navigateTo(com.example.activatehooparena.BookingsActivity.class);
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