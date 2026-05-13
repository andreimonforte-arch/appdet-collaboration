package com.example.activatehooparena;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvResult, tvForgotPassword, tvSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.activity_ge4_edit_text_password_type_text_color, container, false);

        etEmail    = view.findViewById(R.id.etUsernameGE4);
        etPassword = view.findViewById(R.id.etPasswordGE4);
        btnLogin   = view.findViewById(R.id.btnLoginGE4);
        tvResult   = view.findViewById(R.id.tvResultGE4);

        // Initialize optional views if they exist in layout
        tvForgotPassword = view.findViewById(R.id.tvEditPassword);
        tvSignUp = view.findViewById(R.id.tvValue);

        btnLogin.setOnClickListener(v -> attemptLogin());

        return view;
    }

    private void attemptLogin() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        // TODO: Call ApiClient.login(email, password, callback)
        // On success → navigate to HomeActivity
        startActivity(new Intent(getActivity(), HomeActivity.class));
        requireActivity().finish();
    }

    private void showError(String msg) {
        tvResult.setVisibility(View.VISIBLE);
        tvResult.setText(msg);
        tvResult.setTextColor(Color.parseColor("#FF9800"));
    }
}