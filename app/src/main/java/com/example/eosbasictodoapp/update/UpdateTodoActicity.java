package com.example.eosbasictodoapp.update;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.eosbasictodoapp.R;
import com.example.eosbasictodoapp.add.AddTodoActivity;
import com.example.eosbasictodoapp.data.database.MyDatabase;
import com.example.eosbasictodoapp.data.entity.TodoItem;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateTodoActicity extends AppCompatActivity {

    private TextInputLayout til_title, til_sDate, til_dDate, til_memo;
    private final int START_DATE = 0;
    private final int DUE_DATE = 1;
    private final int ERROR_CODE = -1;
    private TodoItem selectItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo_acticity);

        MyDatabase myDatabase = MyDatabase.getInstance(UpdateTodoActicity.this);

        try {
            selectItem = myDatabase.todoDao()
                    .getTodo(getIntent().getIntExtra("todo_id", -1));
        } catch (Exception e) {
            Log.d("no id", "no id");
            finish();
        }

        // TextInputLayout
        til_title = findViewById(R.id.update_til_title);
        til_sDate = findViewById(R.id.update_til_start_date);
        til_dDate = findViewById(R.id.update_til_end_date);
        til_memo = findViewById(R.id.update_til_memo);

        til_title.getEditText().setText(selectItem.getTitle());
        til_sDate.getEditText().setText(selectItem.getStart_date());
        til_dDate.getEditText().setText(selectItem.getDue_date());
        til_memo.getEditText().setText(selectItem.getMemo());

        // actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Update Todo");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        til_sDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(START_DATE);
            }
        });

        til_dDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(DUE_DATE);
            }
        });
    }

    private void showCalender(final int mode) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(UpdateTodoActicity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String month_s = new Integer(month + 1).toString();
                String day_s = new Integer(dayOfMonth).toString();

                if(month + 1 < 10) month_s = "0" + month_s;
                if(dayOfMonth < 10) day_s = "0" + day_s;

                String date = "" + year + "/" + month_s + "/" + day_s;
                if (mode == START_DATE) {
                    til_sDate.getEditText().setText(date);
                } else if (mode == DUE_DATE) {
                    til_dDate.getEditText().setText(date);
                } else { /* TODO: Error */ }
            }
        }, mYear, mMonth, mDay).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.save_todo:
                String title = til_title.getEditText().getText().toString();
                String sDate = til_sDate.getEditText().getText().toString();
                String dDate = til_dDate.getEditText().getText().toString();
                String memo = til_memo.getEditText().getText().toString();

                // title error
                if (title.equals("")) til_title.setError("필수 요소입니다.");
                else til_title.setError(null);

                // sDate error
                if (sDate.equals("")) til_sDate.setError("필수 요소입니다.");
                else til_sDate.setError(null);

                // dDate error
                if (dDate.equals("")) til_dDate.setError("필수 요소입니다.");
                else til_dDate.setError(null);

                if (!title.equals("") && !sDate.equals("") && !dDate.equals("")) {
                    if (sDate.compareTo(dDate) > 0) {
                        til_sDate.setError("시작 날짜가 더 느려요!");
                        til_dDate.setError("끝나는 날짜가 더 빨라요!");

                    } else {
                        selectItem.setTitle(title);
                        selectItem.setStart_date(sDate);
                        selectItem.setDue_date(dDate);
                        selectItem.setMemo(memo);

                        MyDatabase myDatabase = MyDatabase.getInstance(UpdateTodoActicity.this);
                        myDatabase.todoDao().updateTodo(selectItem);

                        Toast.makeText(UpdateTodoActicity.this, "저장 성공 헿", Toast.LENGTH_SHORT).show();

                        finish();
                        break;
                    }
                }
        }

        return super.onOptionsItemSelected(item);
    }
}
