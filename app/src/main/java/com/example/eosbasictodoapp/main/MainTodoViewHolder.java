package com.example.eosbasictodoapp.main;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.eosbasictodoapp.R;
import com.example.eosbasictodoapp.data.entity.TodoItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainTodoViewHolder extends RecyclerView.ViewHolder {

    private TextView todo_tv_title;
    private CheckBox todo_cb;

    public MainTodoViewHolder(@NonNull View itemView) {
        super(itemView);

        todo_tv_title = itemView.findViewById(R.id.todo_tv_title);
        todo_cb = itemView.findViewById(R.id.todo_cb);
    }

    public void onBind(TodoItem item) {
        todo_tv_title.setText(item.getTitle());
        todo_cb.setChecked(item.getChecked());
        if(item.getChecked()) {
            SpannableString contentSp = new SpannableString(item.getTitle());
            contentSp.setSpan(new StrikethroughSpan(), 0,
                    item.getTitle().length(), 0);
            todo_tv_title.setText(contentSp);
        }
    }
}
