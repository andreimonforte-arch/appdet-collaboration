package com.example.activatehooparena;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.activatehooparena.data.repository.UserRepository;

public class AccountViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository repository;

    public AccountViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AccountViewModel.class)) {
            return (T) new AccountViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}