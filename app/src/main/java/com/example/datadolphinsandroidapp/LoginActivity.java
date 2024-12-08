package com.example.datadolphinsandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginLogInButton);
        Button newUserButton = findViewById(R.id.loginNewUser);
        loginButton.setOnClickListener(v -> logIn());
        newUserButton.setOnClickListener(v -> newUser());
    }
    private void newUser() {
        Intent newUser = new Intent(LoginActivity.this, NewUserActivity.class);
        startActivity(newUser);
        Toast.makeText(this, "New User", Toast.LENGTH_SHORT).show();
    }

    private void logIn() {
        if(PasswordCheck()){
            Toast.makeText(this, "LOG IN GOOD", Toast.LENGTH_SHORT).show();
        }
        else{
            // Login failed Msg
            Toast.makeText(this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean PasswordCheck(){
        EditText username = findViewById(R.id.loginUsername);
        EditText pass = findViewById(R.id.loginPassword);
        if (username.getText().toString().equals(pass.getText().toString())){
            return true;
        }
        return false;
    }
}
