package com.example.datadolphinsandroidapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewUserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Button newUserdoneButton = findViewById(R.id.newUserDone);
        Button newUserBackButton = findViewById(R.id.newUserBack);
        newUserdoneButton.setOnClickListener(v -> newUser());
        newUserBackButton.setOnClickListener(v -> finish());
    }
    private void newUser() {
        if (testPasswordMatch()) {
            makeNewUser();
            finish();
        }
    }
    private void makeNewUser() {
        Toast.makeText(this, "New User made", Toast.LENGTH_SHORT).show();
    }
    private boolean testPasswordMatch() {
        EditText pass1 = findViewById(R.id.newUserPassword1);
        EditText pass2 = findViewById(R.id.newUserPassword2);
        if(pass1.getText().toString().equals(pass2.getText().toString())){
            return true;
        }else {
            Toast.makeText(this, "Pass don't match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
