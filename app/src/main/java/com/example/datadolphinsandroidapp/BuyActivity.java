package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.databinding.ActivityBuyBinding;

import java.util.Locale;

public class BuyActivity extends AppCompatActivity {

    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    private ActivityBuyBinding binding;
    private StockRepository repository;

    public static final String TAG = "DAC_STOCK";

    String ticker = "";
    int quantity = 0;

    double totalCost = 0.0;   // cost * qty
    double balance = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the ticker passed from MainActivity
        Intent intent = getIntent();
        initBalanceDisplay();
        ticker = intent.getStringExtra(EXTRA_TICKER);
        if (ticker != null) {
            Log.d(TAG, "Ticker received: " + ticker);
            // You can now use `ticker` to perform database queries or update UI
            binding.stock.setText(ticker); // Example: prepopulate stock field
        }

        getInformationFromDisplay();
//        updateDisplay();

        repository = StockRepository.getRepository(getApplication());

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
                }
            }
        });

    }

    private void verifyTicker() {
        // Get ticker input from activity_buy.xml
        String ticker = binding.tickerInputEditText.getText().toString().trim();

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
                double cost = stock.getCost();
                totalCost = cost * quantity;

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

    // this is just for testing, putting stock in the cost placeholder.
//    public void updateDisplay() {
//        String currentInfo = binding.companyTickerEditText.getText().toString();
//        Log.d(TAG,"Current info: "+currentInfo);
//        String newDisplay = String.format(Locale.US, "%s", mTicker);
//        binding.costPlaceholder.setText(newDisplay);
//    }

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

    // creating an Intent to navigate from MainActivity to BuyActivity.
    public static Intent buyIntentFactory(Context context) {
        return new Intent(context, BuyActivity.class);
    }

}