package com.example.shoppingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapp.db.AppDatabase;
import com.example.shoppingapp.db.Category;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Category>> listOfCategory;
    private AppDatabase appDatabase;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        listOfCategory = new MutableLiveData<>();
        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getCategoryListObserver(){
        return listOfCategory;
    }

    public void getAllCategoryList(){
        List<Category> categoryList = appDatabase.shoppingListDao().getAllCategoriesList();

        if (categoryList.size()>0){
            listOfCategory.postValue(categoryList);
        }
        else{
            listOfCategory.postValue(null);
        }
    }

    public void insertCategory(String catName){
        Category category = new Category(catName);

        appDatabase.shoppingListDao().insertCategory(category);
        getAllCategoryList();
    }
    public void updateCategory(Category category){

        appDatabase.shoppingListDao().updateCategory(category);
        getAllCategoryList();
    }
    public void deleteCategory(Category category){

        appDatabase.shoppingListDao().deleteCategory(category);
        getAllCategoryList();
    }
}
