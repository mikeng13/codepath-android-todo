package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mng on 1/8/15.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemsAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        // Lookup view for data population
        TextView todoName = (TextView) convertView.findViewById(R.id.todoItemName);
        TextView todoDueDate = (TextView) convertView.findViewById(R.id.todoItemDueDate);
        // Populate the data into the template view using the data object
        todoName.setText(todoItem.name);
        todoDueDate.setText(todoItem.dueDate);
        // Return the completed view to render on screen
        return convertView;
    }
}
