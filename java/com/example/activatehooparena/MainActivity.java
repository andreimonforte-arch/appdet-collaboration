package com.example.activatehooparena;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activatehooparena.data.local.SessionManager;  // ← YOUR SessionManager

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);  // ← FIXED: setContentView, not onCreate

                SessionManager session = new SessionManager(this);  // ← Now works with your SessionManager

                if (session.isLoggedIn()) {  // ← FIXED: isLoggedIn(), not clone()
                        startActivity(new Intent(this, HomeActivity.class));
                } else {
                        startActivity(new Intent(this, LoginActivity.class));  // ← Use Activity, not Fragment
                }

                finish();  // Close splash/launcher activity
        }
}