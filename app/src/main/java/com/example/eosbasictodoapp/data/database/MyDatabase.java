package com.example.eosbasictodoapp.data.database;

import android.content.Context;

import com.example.eosbasictodoapp.data.dao.TodoDao;
import com.example.eosbasictodoapp.data.entity.TodoItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {TodoItem.class})
public abstract class MyDatabase extends RoomDatabase {
    abstract public TodoDao todoDao();

    private static MyDatabase myDatabase;

    public static MyDatabase getInstance(Context context) {
        if(myDatabase == null) {
            myDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "myDatabase.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return myDatabase;
    }
}
