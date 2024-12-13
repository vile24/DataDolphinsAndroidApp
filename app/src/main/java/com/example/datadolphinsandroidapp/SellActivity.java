package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.database.entities.User;
import com.example.datadolphinsandroidapp.databinding.ActivitySellBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class SellActivity extends AppCompatActivity {
    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    public static final String EXTRA_QUANTITY = "com.example.datadolphinsandroidapp.EXTRA_QUANTITY";
    public static final String USER = "com.example.datadolphinsandroidapp.SellActivity.USER";
    public static final String TAG = "SellActivity";

    private ActivitySellBinding binding;
    private StockRepository repository;
    private Transaction transaction;
    private int userId;

    private String ticker; // Ticker passed from Portfolio
    private int availableQuantity; // Total shares available for the ticker
    private int quantity;
    private User user;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up View Binding
        binding = ActivitySellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Repository
        repository = StockRepository.getRepository(getApplication());

        // Get Intent extras
        Intent intent = getIntent();
        ticker = intent.getStringExtra(EXTRA_TICKER);
        availableQuantity = intent.getIntExtra(EXTRA_QUANTITY, 0);
        String userName = intent.getStringExtra(USER);

        userRepository = UserRepository.getRepository(getApplication());

        userRepository.getUserByUserName(userName).observe(this, user -> {
            this.user = user;
            loadAvailableShares();
        });


        if (ticker == null || ticker.isEmpty() || availableQuantity <= 0) {
            Toast.makeText(this, "Invalid stock data!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display stock details in the UI
        binding.sellTickerInputEditText.setText(ticker);
//        binding.availableSharePlaceholder.setText(String.valueOf(availableQuantity));

        // Handle Sell Button Click
        binding.sellButton.setOnClickListener(v -> sellStock());


        binding.sellQuantityInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try {
                    quantity = Integer.parseInt(binding.sellQuantityInputEditText.getText().toString());
                } catch (NumberFormatException e) {
                    quantity = 0;
//               Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show();
                }
                double proceeds = quantity * transaction.getPurchasePrice();
                binding.sellCostPlaceHolder.setText(String.format(Locale.US, "$%.2f", proceeds));
            }
        });
    }

    private void loadAvailableShares() {
        repository.getTransactionsByUserId(user.getUserId()).observe(this, transactions -> {
            for (Transaction userTransaction : transactions) {
                if (userTransaction.getTicker().equals(ticker)) {
                    transaction = userTransaction;
                    binding.availableSharePlaceholder.setText(String.valueOf(transaction.getQuantity()));
                }
            }
        });
    }

    private void sellStock() {
        try {
            int sellQuantity = Integer.parseInt(binding.sellQuantityInputEditText.getText().toString().trim());

            if (sellQuantity <= 0 || sellQuantity > availableQuantity) {
                Toast.makeText(this, "Invalid quantity!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate proceeds (example logic for stock price)
            double pricePerStock = transaction.getPurchasePrice();
            double proceeds = sellQuantity * pricePerStock;

            // Update transaction database
            int remainingQuantity = availableQuantity - sellQuantity;

            // Create updated transaction (provide all 5 required parameters)
//            Transaction updatedTransaction = new Transaction(
//                    1,                 // Dummy userId (replace with actual user ID logic)
//                    ticker,            // Ticker passed from intent
//                    remainingQuantity, // Remaining quantity after sale
//                    pricePerStock,     // Purchase price (example logic)
//                    0.0                // Sell price (optional, adjust as needed)
//            );
            if (remainingQuantity == 0) {
                repository.deleteTransaction(transaction);
            }
            else {
                transaction.setQuantity(remainingQuantity);
                // Save updated transaction to the database
                repository.updateTransaction(transaction);
            }

            // Update user cash balance for sale
            double updatedBalance = user.getCash_balance() + proceeds;
            user.setCash_balance(user.getCash_balance() + proceeds);
            userRepository.insertUser(user);

            // Show success message and finish
            Toast.makeText(this, "Sold " + sellQuantity + " shares for $" + proceeds, Toast.LENGTH_SHORT).show();
            finish(); // Close SellActivity and return to Portfolio
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show();
        }
    }


    public static Intent sellIntentFactory(Context context, String ticker, int quantity, String userName) {
        Intent intent = new Intent(context, SellActivity.class);
        intent.putExtra(EXTRA_TICKER, ticker);
        intent.putExtra(EXTRA_QUANTITY, quantity);
        intent.putExtra(USER, userName);
        return intent;
    }
}
