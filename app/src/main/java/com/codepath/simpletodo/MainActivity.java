package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements EditTodoItemDialog.EditTodoItemDialogListener{

    ArrayList<TodoItem> todoItems;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;
    TodoItem selectedTodoItem;

    // This is used so that we can match the activity result to the correct activity result handler
    private final int REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        todoItems = (ArrayList<TodoItem>)TodoItem.listAll(TodoItem.class);
        itemsAdapter = new TodoItemsAdapter(this, todoItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        TodoItem itemToRemove = todoItems.remove(position);
                        itemToRemove.delete();
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          selectedTodoItem = itemsAdapter.getItem(position);
                          showEditItemDialog();
                    }
                }
        );
    }

    private void showEditItemDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoItemDialog editTodoItemDialog = EditTodoItemDialog.newInstance("Edit Item");
        editTodoItemDialog.show(fm, "fragment_edit_item");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract the new activity name value from result extras
            String newActivityName = data.getExtras().getString("activity_name");
            int activityPosition = data.getExtras().getInt("activity_position");
            //items.set(activityPosition, newActivityName);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        selectedTodoItem.name = inputText;
        selectedTodoItem.save();
        itemsAdapter.notifyDataSetChanged();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoItem newTodoItem = new TodoItem(itemText);
        itemsAdapter.add(newTodoItem);
        newTodoItem.save();

        // reset the text field
        etNewItem.setText(R.string.enter_a_new_item);
    }
}
