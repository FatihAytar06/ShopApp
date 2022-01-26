package com.example.shoppingapp;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.db.Category;
import com.example.shoppingapp.db.Items;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private Context mContext;
    private List<Items> itemsList;
    private HandleItemsClick clickListener;

    public ItemListAdapter(Context mContext, HandleItemsClick handleItemsClick) {
        this.mContext = mContext;
        this.clickListener = handleItemsClick;
        notifyDataSetChanged();
    }
    public void setItemsList(List<Items> list){
        this.itemsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemNameTextView.setText(itemsList.get(position).itemName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(itemsList.get(position));
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeItem(itemsList.get(position));
            }
        });

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.editItem(itemsList.get(position));
            }
        });
        if (this.itemsList.get(position).completed){
            holder.itemNameTextView.setPaintFlags(holder.itemNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else{
            holder.itemNameTextView.setPaintFlags(0);
        }

    }


    @Override
    public int getItemCount() {
        if (itemsList.size()>0)
            return itemsList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        ImageView removeItem,editItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.categoryName);
            removeItem = itemView.findViewById(R.id.removeCategory);
            editItem = itemView.findViewById(R.id.editCategory);
        }
    }

    public interface HandleItemsClick{
        void itemClick(Items item);
        void removeItem(Items item);
        void editItem(Items item);
    }
}
