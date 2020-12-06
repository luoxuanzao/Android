package com.byted.camp.todolist.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.byted.camp.todolist.db.entity.MyNote;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM ToDoList")
    List<MyNote> getAll();

    @Insert
    void insert(MyNote notes);

    @Update
    void update(MyNote myNote);

    @Delete
    void delete(MyNote myNote);

}
