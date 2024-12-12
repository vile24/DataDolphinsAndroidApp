/**
 * Adapter for displaying a list of Stock items in RecyclerView
 */
package com.example.datadolphinsandroidapp.viewHolders;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.datadolphinsandroidapp.database.entities.Stock;


public class PortfolioAdapter extends ListAdapter<Stock, PortfolioViewHolder> {

    //Constructor
    public PortfolioAdapter(@NonNull DiffUtil.ItemCallback<Stock> diffCallback) {
        super(diffCallback);
    }
    //create new layout
    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PortfolioViewHolder.create(parent);
    }
    //shows data
    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        Stock currentStock = getItem(position);
        holder.bind(currentStock.getCompany(), String.valueOf(currentStock.getStockId())); // Pass name and stock ID/quantity
    }

    //checking items represent same stock
    public static class PortfolioLogDiff extends DiffUtil.ItemCallback<Stock> {
        @Override
        public boolean areItemsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {
            return oldItem.getStockId() == newItem.getStockId();
        }
        //checking content of items are the same
        @Override
        public boolean areContentsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {
            return oldItem.equals(newItem);
        }
    }
}
