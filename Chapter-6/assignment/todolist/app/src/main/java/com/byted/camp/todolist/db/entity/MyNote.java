package com.byted.camp.todolist.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.byted.camp.todolist.beans.State;

@Entity(tableName = "ToDoList")
public class MyNote {
    @PrimaryKey
    public int tid;
    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "data")
    public String data;
    @ColumnInfo(name="priority")
    public int priority;
}
