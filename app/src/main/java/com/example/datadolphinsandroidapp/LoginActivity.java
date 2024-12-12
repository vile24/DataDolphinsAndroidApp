package com.example.datadolphinsandroidapp;
import static android.os.Build.USER;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import com.example.datadolphinsandroidapp.database.entities.User;
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;


public class LoginActivity extends AppCompatActivity {
    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = UserRepository.getRepository(getApplication());
        Button loginButton = findViewById(R.id.loginLogInButton);
        Button newUserButton = findViewById(R.id.loginNewUser);
        loginButton.setOnClickListener(v -> logIn());
        newUserButton.setOnClickListener(v -> newUser());
    }

    private void newUser() {
        Intent newUser = new Intent(LoginActivity.this, NewUserActivity.class);
        Toast.makeText(this, "New User", Toast.LENGTH_SHORT).show();
        startActivity(newUser);
    }

    private void logIn() {
        EditText userNameIn =  findViewById(R.id.loginUsername);
        EditText passwordIn= (findViewById(R.id.loginPassword));
        String username = userNameIn.getText().toString();
        String pass = passwordIn.getText().toString();

        if(username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        passwordCheck(username,pass, isValid ->{
            if (isValid){
                    Toast.makeText(LoginActivity.this, "LOG IN GOOD", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void passwordCheck(String username, String pass, PasswordCheckCallback callback){

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {

            if (user != null) {
                if (pass.equals(user.getPassword())) {
                    callback.onResult(true);

                    Intent intent = MainActivity.openMain(getApplicationContext(),user.getUser_name());
                    startActivity(intent);

                    // loginActivity(repository.getUserByUserName(username));
                } else {
                    callback.onResult(false);
                }
            }else{
                Toast.makeText(this, "User "+ username + " not found", Toast.LENGTH_SHORT).show();
                callback.onResult(false);
            }
            userObserver.removeObservers(this);
        });
    }

//    Static Intent loginIntentFactory(Context context){
//        return new Intent(context, LoginActivity.class);
//    }


    public interface PasswordCheckCallback {
        void onResult(boolean isValid);
    }

    public static Intent LoginActivityintent (Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        // intent.getDataString();
        return intent;
    }

}
