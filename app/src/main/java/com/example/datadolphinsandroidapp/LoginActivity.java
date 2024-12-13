package com.example.datadolphinsandroidapp;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import com.example.datadolphinsandroidapp.database.entities.User;
import com.example.datadolphinsandroidapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private @NonNull ActivityLoginBinding binding;
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        // Login button
        binding.loginLogInButton.setOnClickListener(v -> verifyUser());

        // New User button
        binding.loginNewUser.setOnClickListener(v ->
                startActivity(NewUserActivity.createNewUserIntent(getApplicationContext()))
        );
    }

    private void verifyUser() {
        String enteredUserName = binding.loginUsername.getText().toString().trim();
        String enteredPass = binding.loginPassword.getText().toString().trim();
        if (enteredUserName.isEmpty() || enteredPass.isEmpty()) {
            Toast.makeText(this, "Empty Input Detected", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(enteredUserName);
        userObserver.observe(this, user -> {
            if (user != null && enteredPass.equals(user.getPassword())) {
                Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show();
                startActivity(MainActivity.openMain(getApplicationContext(), user));
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
