package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.entities.User;

public class NewUserActivity extends AppCompatActivity {
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user); // Set content view first

        // Initialize buttons
        Button newUserDoneButton = findViewById(R.id.newUserDone);
        Button newUserBackButton = findViewById(R.id.newUserBack);

        // Set click listener for done button
        newUserDoneButton.setOnClickListener(v -> newUser());

        // Initialize repository
        repository = UserRepository.getRepository(getApplication());

        // Set click listener for back button
        newUserBackButton.setOnClickListener(v -> {
            Intent intent = LoginActivity.loginIntentFactory(NewUserActivity.this);
            startActivity(intent);
        });
    }

    private void newUser() {
        if ((findViewById(R.id.newUserPassword1)==(findViewById(R.id.newUserPassword1)))){
            EditText userNameInput = findViewById(R.id.newUserUserName);
            EditText passwordInput = findViewById(R.id.newUserPassword1);

            String username = userNameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
            } else {
                Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "New User created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "New user creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent createNewUserIntent(Context context) {
        return new Intent(context, NewUserActivity.class);
    }
}
