package com.example.eosbasictodoapp.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.eosbasictodoapp.R;
import com.example.eosbasictodoapp.add.AddTodoActivity;
import com.example.eosbasictodoapp.data.database.MyDatabase;
import com.example.eosbasictodoapp.data.entity.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_rcv;
    private FloatingActionButton main_fab;
    private MainTodoAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("TODO APP");
        }

        main_rcv = findViewById(R.id.main_rcv);
        main_fab = findViewById(R.id.main_fab_intent);

        adapter = new MainTodoAdapter();

        main_rcv.setAdapter(adapter);
        main_rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        final MyDatabase myDatabase = MyDatabase.getInstance(this);
        List<TodoItem> list_item = myDatabase.todoDao().getAllTodo();
        adapter.submitAll(list_item);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long time  = System.currentTimeMillis();
                TodoItem temp = new TodoItem(time.toString());
                myDatabase.todoDao().insertTodo(temp);
                adapter.addItem(temp);
            }
        });

        // 지울것
        FloatingActionButton test = findViewById(R.id.main_fab_intent);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_item:
                adapter.removeAllItem();
                MyDatabase myDatabase = MyDatabase.getInstance(this);
                myDatabase.todoDao().deleteAllTodo();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyDatabase myDatabase = MyDatabase.getInstance(this);
        adapter.submitAll(myDatabase.todoDao().getAllTodo());

    }

    @Override
    protected void onResume() {
        super.onResume();

        MyDatabase myDatabase = MyDatabase.getInstance(this);
        adapter.submitAll(myDatabase.todoDao().getAllTodo());
    }
}
