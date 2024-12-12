/**
 * Holds and binds stock data in RecyclerView
 */
package com.example.datadolphinsandroidapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datadolphinsandroidapp.R;

public class PortfolioViewHolder extends RecyclerView.ViewHolder {

    private final TextView stockNameTextView;
    private final TextView stockQuantityTextView;
    //Constructor
    public PortfolioViewHolder(@NonNull View itemView) {
        super(itemView);

        stockNameTextView = itemView.findViewById(R.id.recyclerItemUserStockTextEdit);
        stockQuantityTextView = itemView.findViewById(R.id.recyclerItemStockQuantityTextView);
    }

    //binding data from stocks to TextView
    public void bind(String stockName, String stockQuantity) {
        //set stock name
        stockNameTextView.setText(stockName);
        //set stock quantity
        stockQuantityTextView.setText("Quantity: " + stockQuantity);
    }
    //creating a new instance of PortfolioViewHolder
    public static PortfolioViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.portfolio_recycler_item, parent, false);
        return new PortfolioViewHolder(view);
    }
}
