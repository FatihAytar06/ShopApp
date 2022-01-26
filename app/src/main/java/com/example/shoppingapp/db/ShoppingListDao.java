package com.example.shoppingapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ShoppingListDao {

    @Query("Select * FROM Category")
    List<Category> getAllCategoriesList();

    @Insert
    void insertCategory(Category... categories);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("Select * FROM Items WHERE categoryId=:catId")
    List<Items> getAllItemsList(int catId);

    @Insert
    void insertItems(Items... items);

    @Update
    void updateItem(Items item);

    @Delete
    void deleteItem(Items item);
}
