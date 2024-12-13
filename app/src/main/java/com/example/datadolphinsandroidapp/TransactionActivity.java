package com.example.datadolphinsandroidapp;

import static android.os.Build.USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.database.entities.User;
import com.example.datadolphinsandroidapp.databinding.ActivityTransactionBinding;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;


public class TransactionActivity extends AppCompatActivity {
    private ActivityTransactionBinding binding;
    private StockRepository stockRepository;

    private TransactionAdapter adapter;

    double portfolioBalance = 0.0;

    static String USER = "com.example.datadolphinsandroidapp.TransactionActivity.user";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stockRepository = StockRepository.getRepository(getApplication());

        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER);

        UserRepository userRepository = UserRepository.getRepository(getApplication());

        userRepository.getUserByUserName(userName).observe(this, user -> {
            this.user = user;
            binding.cashBalancePlaceHolder.setText(formatMoney(user.getCash_balance()));;
            binding.totalBalancePlaceHolder.setText(formatMoney(portfolioBalance + user.getCash_balance()));
            loadTransactions();
        });



        // Set up RecyclerView
        adapter = new TransactionAdapter(this::navigateToSellActivity);
        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionRecyclerView.setAdapter(adapter);



//        intent = getIntent();
//        userName = intent.getStringExtra(USER);
//
//        userRepository = UserRepository.getRepository(getApplication());
//
//        userRepository.getUserByUserName(userName).observe(this, user -> {
//            this.user = user;
//        });


        // Set up a button to navigate to BuyActivity
        binding.buyStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BuyActivity (no ticker passed from TransactionActivity)
                Intent intent = BuyActivity.buyIntentFactory(TransactionActivity.this, user.getUser_name());
                startActivity(intent);
            }
        });
    }

    private void loadTransactions() {
        // run query get stock and qty
        stockRepository.getStocksWithQuantities(user.getUserId()).observe(this, stockWithQuantities -> {
            Log.d("TransactionActivity", "Number of items: " + stockWithQuantities.size());
            portfolioBalance = 0;
            for (StockWithQuantity stock : stockWithQuantities) {
                Log.d("TransactionActivity", "Ticker: " + stock.getTicker() + ", Quantity: " + stock.getQuantity());
                // TODO: Fix calculation on the portfolio - something is off
                portfolioBalance += stock.getPurchasePrice() * stock.getQuantity();
            }
            adapter.submitList(stockWithQuantities);

            binding.portfolioBalancePlaceHolder.setText(formatMoney(portfolioBalance));

            double cashBalance = 0;
            if (user != null) {
                cashBalance = user.getCash_balance();
            }
            binding.totalBalancePlaceHolder.setText(formatMoney(portfolioBalance + cashBalance));
        });
    }

    // creating an Intent to navigate from TransactionActivity to BuyActivity.
    public static Intent transactionIntentFactory(Context context, String userName) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra(USER, userName);
        // intent.getDataString();
        return intent;
    }

    private void navigateToSellActivity(StockWithQuantity stock) {
        Intent intent = SellActivity.sellIntentFactory(this, stock.getTicker(), stock.getQuantity(), user.getUser_name());
        startActivity(intent);
    }

//    private void initBalanceDisplay() {
//        if (user != null) {
//            binding.cashBalancePlaceHolder.setText(formatMoney((user.getCash_balance()));
//        }
//        binding.costPlaceholder.setText("$0.0");
//    }

    private String formatMoney(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);

    }

}