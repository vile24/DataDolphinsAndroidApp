package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.databinding.ActivitySellBinding;

public class SellActivity extends AppCompatActivity {
    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    public static final String EXTRA_QUANTITY = "com.example.datadolphinsandroidapp.EXTRA_QUANTITY";
    public static final String TAG = "SellActivity";

    private ActivitySellBinding binding;
    private StockRepository repository;

    private String ticker; // Ticker passed from Portfolio
    private int availableQuantity; // Total shares available for the ticker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up View Binding
        binding = ActivitySellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Repository
        repository = StockRepository.getRepository(getApplication());

        // Get Intent extras
        ticker = getIntent().getStringExtra(EXTRA_TICKER);
        availableQuantity = getIntent().getIntExtra(EXTRA_QUANTITY, 0);

        if (ticker == null || ticker.isEmpty() || availableQuantity <= 0) {
            Toast.makeText(this, "Invalid stock data!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display stock details in the UI
        binding.sellTickerInputEditText.setText(ticker);
        binding.availableSharePlaceholder.setText(String.valueOf(availableQuantity));

        // Handle Sell Button Click
        binding.sellButton.setOnClickListener(v -> sellStock());
    }

    private void sellStock() {
        try {
            int sellQuantity = Integer.parseInt(binding.sellQuantityInputEditText.getText().toString().trim());

            if (sellQuantity <= 0 || sellQuantity > availableQuantity) {
                Toast.makeText(this, "Invalid quantity!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate proceeds (example logic for stock price)
            double pricePerStock = 50.0; // Replace this with actual price logic
            double proceeds = sellQuantity * pricePerStock;

            // Update transaction database
            int remainingQuantity = availableQuantity - sellQuantity;

            // Create updated transaction (provide all 5 required parameters)
            Transaction updatedTransaction = new Transaction(
                    1,                 // Dummy userId (replace with actual user ID logic)
                    ticker,            // Ticker passed from intent
                    remainingQuantity, // Remaining quantity after sale
                    pricePerStock,     // Purchase price (example logic)
                    0.0                // Sell price (optional, adjust as needed)
            );

            // Save updated transaction to the database
            repository.updateTransaction(updatedTransaction);

            // Show success message and finish
            Toast.makeText(this, "Sold " + sellQuantity + " shares for $" + proceeds, Toast.LENGTH_SHORT).show();
            finish(); // Close SellActivity and return to Portfolio
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent sellIntentFactory(Context context, String ticker, int quantity) {
        Intent intent = new Intent(context, SellActivity.class);
        intent.putExtra(EXTRA_TICKER, ticker);
        intent.putExtra(EXTRA_QUANTITY, quantity);
        return intent;
    }
}
