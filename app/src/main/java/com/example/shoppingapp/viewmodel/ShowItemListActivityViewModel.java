package com.example.shoppingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapp.db.AppDatabase;
import com.example.shoppingapp.db.Items;

import java.util.List;

public class ShowItemListActivityViewModel extends AndroidViewModel {


    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;

    public ShowItemListActivityViewModel(@NonNull Application application) {
        super(application);

        listOfItems = new MutableLiveData<>();
        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getItemsListObserver(){
        return listOfItems;
    }

    public void getAllItemsList(int categoryID){
        List<Items> itemList = appDatabase.shoppingListDao().getAllItemsList(categoryID);

        if (itemList.size()>0){
            listOfItems.postValue(itemList);
        }
        else{
            listOfItems.postValue(null);
        }
    }

    public void insertItem(Items item){

        appDatabase.shoppingListDao().insertItems(item);
        getAllItemsList(item.categoryId);
    }
    public void updateItem(Items item){

        appDatabase.shoppingListDao().updateItem(item);
        getAllItemsList(item.categoryId);
    }
    public void deleteItem(Items item){

        appDatabase.shoppingListDao().deleteItem(item);
        getAllItemsList(item.categoryId);
    }
}
