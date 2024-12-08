package com.example.datadolphinsandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import android.util.Log;

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
            return;
        }
        PasswordCheck(username,pass,isValid ->{
            if (isValid){
                    Toast.makeText(LoginActivity.this, "LOG IN GOOD", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PasswordCheck(String username, String pass, PasswordCheckCallback callback){

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {

            if (user != null) {
                if (pass.equals(user.getPassword())) {
                    callback.onResult(true);
                } else {
                    callback.onResult(false);
                }
            }else{
                Toast.makeText(this, "User "+ username + " not found", Toast.LENGTH_SHORT).show();
                callback.onResult(false);
            }
        });
    }
    public interface PasswordCheckCallback {
        void onResult(boolean isValid);
    }
}
