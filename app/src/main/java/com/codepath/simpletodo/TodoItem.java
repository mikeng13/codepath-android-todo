package com.codepath.simpletodo;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by mng on 1/8/15.
 */
public class TodoItem extends SugarRecord<TodoItem>{
    String name;
    String dueDate;

    public TodoItem() {

    }

    public TodoItem(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }
}
