package com.example.activatehooparena.data.repository;

import android.content.Context;
import com.example.activatehooparena.data.local.SessionManager;

public class UserRepository {
    private final SessionManager sessionManager;

    public UserRepository(Context context) {
        this.sessionManager = new SessionManager(context);
    }

    public String getUserName() {
        return sessionManager.getUserName();
    }

    public String getUserEmail() {
        return sessionManager.getUserEmail();
    }

    public void updateName(String name) {
        sessionManager.saveUser(name, sessionManager.getUserEmail());
    }

    public void logout() {
        sessionManager.clearSession();
    }
}