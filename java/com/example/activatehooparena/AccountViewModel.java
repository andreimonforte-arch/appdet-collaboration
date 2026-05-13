package com.example.activatehooparena;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.activatehooparena.data.repository.UserRepository;
public class AccountViewModel extends ViewModel {

    private final UserRepository userRepo;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _navigateToMain = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _showDeleteDialog = new MutableLiveData<>();

    public AccountViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void loadUser() {
        _userName.setValue(userRepo.getUserName());
        _userEmail.setValue(userRepo.getUserEmail());
    }

    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            _toastMessage.setValue("Name cannot be empty");
            return;
        }
        userRepo.updateName(newName.trim());
        _userName.setValue(newName.trim());
        _toastMessage.setValue("Name updated successfully");
    }

    public void onDeleteAccountClicked() {
        _showDeleteDialog.setValue(true);
    }

    public void confirmDeleteAccount() {
        userRepo.logout();
        _navigateToMain.setValue(true);
    }

    public LiveData<String> getUserName() { return _userName; }
    public LiveData<String> getUserEmail() { return _userEmail; }
    public LiveData<String> getToastMessage() { return _toastMessage; }
    public LiveData<Boolean> getNavigateToMain() { return _navigateToMain; }
    public LiveData<Boolean> getShowDeleteDialog() { return _showDeleteDialog; }
}