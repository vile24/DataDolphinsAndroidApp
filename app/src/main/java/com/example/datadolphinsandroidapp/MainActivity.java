package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.entities.User;
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Set up a button to navigate to BuyActivity
        binding.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BuyActivity (no ticker passed from MainActivity)
                Intent intent = BuyActivity.buyIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        // Set up a button to navigate to BuyActivity
        binding.SellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SellActivity (no ticker passed from MainActivity)
                Intent intent = SellActivity.sellIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        binding.portBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SellActivity (no ticker passed from MainActivity)
                Intent intent = LoginActivity.loginIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent openMain(Context context, User user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("USER", user.getUser_name());
        // intent.getDataString();
        return intent;
    }

}