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
import androidx.lifecycle.LiveData;
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
        EditText pass1Input = findViewById(R.id.newUserPassword1);
        EditText pass2Input = findViewById(R.id.newUserPassword2);
        EditText userNameInput = findViewById(R.id.newUserUserName);
        String username = userNameInput.getText().toString().trim();
        String password1 = pass1Input.getText().toString().trim();
        String password2 = pass2Input.getText().toString().trim();

        //Check pass1&2 match
        if (!password1.equals(password2)) {
            Toast.makeText(this, "Passwords dont match", Toast.LENGTH_SHORT).show();
            return;
        }

        // check if any feild is empyy
        if (username.isEmpty() || password1.isEmpty()) {
            Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if username is already in use
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Username is already in use", Toast.LENGTH_SHORT).show();
            } else {
                // Only create the user if the username is available
                User newUser = new User(username, password1);
                repository.insertUser(newUser);
                Toast.makeText(this, "New User created", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public static Intent createNewUserIntent(Context context) {
        return new Intent(context, NewUserActivity.class);
    }
}
