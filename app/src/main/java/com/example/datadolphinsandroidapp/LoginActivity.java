package com.example.datadolphinsandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.database.entities.User;

public class LoginActivity extends AppCompatActivity {
    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginLogInButton);
        Button newUserButton = findViewById(R.id.loginNewUser);
        loginButton.setOnClickListener(v -> logIn());
        newUserButton.setOnClickListener(v -> newUser());
        repository = UserRepository.getRepository(getApplication());
    }
    private void newUser() {
        Intent newUser = new Intent(LoginActivity.this, NewUserActivity.class);
        startActivity(newUser);
        Toast.makeText(this, "New User", Toast.LENGTH_SHORT).show();
    }

    private void logIn() {
        EditText userNameIn =  findViewById(R.id.loginUsername);
        EditText passwordIn= (findViewById(R.id.loginPassword));
        String username = userNameIn.getText().toString();
        String pass = passwordIn.getText().toString();

        if(username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "empty filed", Toast.LENGTH_SHORT).show();
        }
        else if(PasswordCheck(username,pass)){
            Toast.makeText(this, "LOG IN GOOD", Toast.LENGTH_SHORT).show();
        }
        else{
            // Login failed Msg
            Toast.makeText(this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean PasswordCheck(String username, String pass){

        LiveData<User> userObserver = repository.getUserByUserName(username);
        User thisUser = repository.getUserByUserName(username).getValue();

        assert thisUser != null;
        if (thisUser.getPassword().equals(pass)){
            return true;
        }
        return false;
    }
}
