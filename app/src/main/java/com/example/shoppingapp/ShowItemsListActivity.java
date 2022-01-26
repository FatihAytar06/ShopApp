package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoppingapp.db.Items;
import com.example.shoppingapp.viewmodel.ShowItemListActivityViewModel;

import java.util.List;

public class ShowItemsListActivity extends AppCompatActivity implements ItemListAdapter.HandleItemsClick {

    private int category_id;
    private ItemListAdapter itemListAdapter;
    private ShowItemListActivityViewModel viewModel;
    RecyclerView recyclerView;
    private Items itemToUpdate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);
        category_id = getIntent().getIntExtra("category_id",0);
        String categoryName = getIntent().getStringExtra("category_name");

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText addNewItemInput = findViewById(R.id.addNewItemInput);
        ImageView saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = addNewItemInput.getText().toString();
                if (TextUtils.isEmpty(itemName)){
                    Toast.makeText(ShowItemsListActivity.this, "Enter Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (itemToUpdate==null){
                    saveNewItem(itemName);
                }
                else{
                    updateNewItem(itemName);
                }

            }
        });
        initViewModel();
        initRecyclerView();
        viewModel.getAllItemsList(category_id);
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter = new ItemListAdapter(this,this);
        recyclerView.setAdapter(itemListAdapter);

    }
    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(ShowItemListActivityViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items == null){
                    recyclerView.setVisibility(View.GONE);
                    findViewById(R.id.noResult).setVisibility(View.VISIBLE);
                }
                else{
                    itemListAdapter.setItemsList(items);
                    findViewById(R.id.noResult).setVisibility(View.GONE);
                }
            }
        });

    }



    private void saveNewItem(String itemName){
        Items item = new Items();
        item.itemName = itemName;
        item.categoryId = category_id;
        viewModel.insertItem(item);
        ((EditText) findViewById(R.id.addNewItemInput)).setText("");
    }

    @Override
    public void itemClick(Items item) {
        if (item.completed){
            item.completed = false;
        }
        else{
            item.completed=true;
        }

        viewModel.updateItem(item);
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItem(item);
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void editItem(Items item) {
        viewModel.updateItem(item);
        itemListAdapter.notifyDataSetChanged();
    }

    private void updateNewItem(String itemName){
        itemToUpdate.itemName = itemName;
        viewModel.updateItem(itemToUpdate);
        ((EditText) findViewById(R.id.addNewItemInput)).setText("");
        itemToUpdate=null;
    }

}