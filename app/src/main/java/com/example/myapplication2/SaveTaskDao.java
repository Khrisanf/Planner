package com.example.myapplication2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SaveTaskDao {
    @Insert
    void insert(SaveTask task);

    @Update
    void update(SaveTask task);

    @Delete
    void delete(SaveTask task);

    @Query("SELECT * FROM task_table")  
    LiveData<List<SaveTask>> getAllTasks();
}
