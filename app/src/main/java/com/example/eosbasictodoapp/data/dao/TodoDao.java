package com.example.eosbasictodoapp.data.dao;

import com.example.eosbasictodoapp.data.entity.TodoItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TodoDao {
    @Insert
    void insertTodo(TodoItem item);

    @Delete
    void deleteTodo(TodoItem item);

    @Update
    void updateTodo(TodoItem item);

    @Query("SELECT * FROM Todo")
    List<TodoItem> getAllTodo();

    @Query("SELECT * FROM Todo WHERE id = :id")
    TodoItem getTodo(int id);
}
