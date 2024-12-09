package com.example.datadolphinsandroidapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserPortfolioActivity extends AppCompatActivity {

    static String USER = "Username Tag";

    private final String loggedInUserId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button backButton = findViewById(R.id.portfolioBackButton);
        backButton.setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        updateUserdata();
    }

    @SuppressLint("SetTextI18n")
    private void updateUserdata() {
        // SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.username),Context.MODE_PRIVATE);

        TextView portfolioTitle = findViewById(R.id.portfolioTitle);
        portfolioTitle.setText("User" + "'s Portfolio");

        TextView portfolioText = findViewById(R.id.portfolioText);
        portfolioText.setText(
                "Username:       " + "USERNAME" +"\n" +
                "Portfolio Value " + "$10000" +"\n" +
                "Balance         " + "$10000" +"\n" +
                "Total Value     " + "$10000" + "\n"
        );

    }

    public static Intent openPortfolio(Context context, String user){
        Intent intent = new Intent(context, UserPortfolioActivity.class);
        intent.putExtra(USER, user);
        //intent.getDataString();
        return intent;
    }

}