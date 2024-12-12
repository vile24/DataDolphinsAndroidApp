package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.databinding.ActivityBuyBinding;

import java.util.Locale;

public class BuyActivity extends AppCompatActivity implements LifecycleOwner {

    // Key to get the ticker
    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    private ActivityBuyBinding binding;
    private StockRepository repository;

    public static final String TAG = "DAC_STOCK";

    // Instance variable
    String ticker = "AAPL";
    int quantity = 0;

    int userId;


    Stock stock;
    double totalCost = 0.0;   // cost * qty

    // This is dummy data for test user.
    double balance = 100000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the ticker passed from TransactionActivity
        Intent intent = getIntent();
        initBalanceDisplay();
        // Key to get pass
        ticker = intent.getStringExtra(EXTRA_TICKER);
        if (ticker != null) {
            Log.d(TAG, "Ticker received: " + ticker);  // debug log
            // You can now use `ticker` to perform database queries or update UI
            binding.stock.setText(ticker); // Example: prepopulate stock field
        }

        getInformationFromDisplay();
       // updateDisplay();

        // set repository
        repository = StockRepository.getRepository(getApplication());

        // Trigger database initialization. run a query so it is created immediately
        repository.getAllStocks().observe(this, stocks -> {
            if (stocks != null && !stocks.isEmpty()) {
                Log.d(TAG, "Stocks retrieved from database: " + stocks.size());
                for (Stock stock : stocks) {
                    Log.d(TAG, "Stock: " + stock.getTicker() + ", " + stock.getCompany() + ", " + stock.getCost());
                }
            } else {
                Log.d(TAG, "No stocks found in database.");
            }
        });

        // Display est. totalCost after user input ticker & qty
        binding.quantityInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    verifyTicker();
            }
        });

        binding.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : make a confirm buy
                if (balance < totalCost) {
                    Toast.makeText(BuyActivity.this, "Not Enough Balance!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(BuyActivity.this, "Buy Successful!", Toast.LENGTH_LONG).show();
                    balance = balance - totalCost;
                    initBalanceDisplay();
                    insertBuyStock();
                }
            }
        });

    }

    private void verifyTicker() {
        // Get ticker input from activity_buy.xml
        ticker = binding.tickerInputEditText.getText().toString().trim();

        // Validate ticker input
        if (ticker.isEmpty()) {
            toastMaker("Ticker should not be blank.");
            return;
        }

        // Get quantity input
        try {
            quantity = Integer.parseInt(binding.quantityInputEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            toastMaker("Please enter a valid quantity.");
            return;
        }

        // Query the database for the ticker
        repository.getStockByTicker(ticker).observe(this, stock -> {
            if (stock != null) {
                this.stock = stock;
                totalCost = stock.getCost() * quantity;

                // Update the cost placeholder with the calculated total
                binding.costPlaceholder.setText(String.format(Locale.US, "$%.2f", totalCost));
            } else {
                toastMaker(String.format("%s is not a valid ticker", ticker));
                // Reset cursor to the beginning of the input
                binding.tickerInputEditText.setSelection(0);
                // Clear the costPlaceholder field
               // binding.costPlaceholder.setText("");
            }
        });
    }

    private void insertBuyStock() {
        repository.insertTransactions(new Transaction(userId, stock.getTicker(), quantity, stock.getCost(), 0));
    }

    private void getInformationFromDisplay() {
        // Grab user "company ticker" input and store in mTicker
        ticker = binding.stock.getText().toString(); // Fix: added mTicker

        // mQuantity needs to try/catch because input might be a string instead of a number
        try {
            quantity = Integer.parseInt(binding.quantityInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.i(TAG, "Error reading value from quantity edit text.");
        }
    }

    // Set initial balance in display
    private void initBalanceDisplay() {
        binding.availableCashBalancePlaceholder.setText(String.format(Locale.US, "$%.2f", balance));;
        binding.tickerInputEditText.setText("");
        binding.quantityInputEditText.setText("");
        binding.costPlaceholder.setText("$0.0");
    }

    public void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // creating an Intent to navigate from TransactionActivity to BuyActivity.
    public static Intent buyIntentFactory(Context context) {
        return new Intent(context, BuyActivity.class);
    }

}