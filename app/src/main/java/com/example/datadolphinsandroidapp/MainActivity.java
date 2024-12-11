package com.example.datadolphinsandroidapp;

import static android.os.Build.USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
    }

    public static Intent openMain(Context context, String user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER, user);
        //intent.getDataString();
        return intent;
    }

}