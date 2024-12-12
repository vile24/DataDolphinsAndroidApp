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
        setContentView(R.layout.activity_new_user);
        Button newUserdoneButton = findViewById(R.id.newUserDone);
        Button newUserBackButton = findViewById(R.id.newUserBack);
        newUserdoneButton.setOnClickListener(v -> newUser());

        newUserdoneButton.setOnClickListener(v -> newUser());
        repository = UserRepository.getRepository(getApplication());


        newUserBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(NewUserActivity.this);
                startActivity(intent);
            }
        });
    }
    private void newUser() {
        if (testPasswordMatch()) {
            makeNewUser();
            Toast.makeText(this, "New User made", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "new user Fail", Toast.LENGTH_SHORT).show();
        }
    }
    private void makeNewUser() {
        EditText userNameInput = (EditText) findViewById(R.id.newUserUserName);
        EditText password = (EditText) findViewById(R.id.newUserPassword1);
        User newUser = new User(userNameInput.getText().toString(), password.getText().toString());
        repository.insertUser(newUser);
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




    public static Intent createNewUserIntent(Context context) {
        return new Intent(context, NewUserActivity.class);
    }
}
