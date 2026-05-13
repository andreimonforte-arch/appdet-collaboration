package com.example.activatehooparena;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etPassword, etRepeatPassword;
    private ImageButton btnTogglePassword, btnToggleRepeat;
    private Button btnCreateAccount;
    private TextView tvResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.activity_ge4_edit_text_password_type_text_color, container, false);

        etFirstName      = view.findViewById(R.id.etFirstNameGE4);
        etLastName       = view.findViewById(R.id.etLastNameGE4);
        etEmail          = view.findViewById(R.id.etUsernameGE4);
        etPassword       = view.findViewById(R.id.etPasswordGE4);
        etRepeatPassword = view.findViewById(R.id.etRepeatPasswordGE4);
        btnTogglePassword= view.findViewById(R.id.btnTogglePasswordGE4);
        btnToggleRepeat  = view.findViewById(R.id.btnToggleRepeatGE4);
        btnCreateAccount = view.findViewById(R.id.btnLoginGE4);
        tvResult         = view.findViewById(R.id.tvResultGE4);

        setupPasswordToggles();
        btnCreateAccount.setOnClickListener(v -> attemptRegister());

        return view;
    }

    private void setupPasswordToggles() {
        btnTogglePassword.setOnClickListener(v -> {
            int type = etPassword.getInputType();
            if (type == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnTogglePassword.setImageResource(R.drawable.ic_eye_24dp);
            } else {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnTogglePassword.setImageResource(R.drawable.ic_eye_off_24dp);
            }
            etPassword.setSelection(etPassword.length());
        });

        // Same pattern for btnToggleRepeat / etRepeatPassword
    }

    private void attemptRegister() {
        String pass   = etPassword.getText().toString();
        String repeat = etRepeatPassword.getText().toString();

        if (!pass.equals(repeat)) {
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText("Passwords do not match.");
            tvResult.setTextColor(Color.RED);
            return;
        }

        // TODO: ApiClient.register(...)
    }
}