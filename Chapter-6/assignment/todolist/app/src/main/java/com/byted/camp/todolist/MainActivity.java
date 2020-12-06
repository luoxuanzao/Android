package com.byted.camp.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.ToDoDatabase;
import com.byted.camp.todolist.db.ToDoDatabase_Impl;
import com.byted.camp.todolist.db.entity.MyNote;
import com.byted.camp.todolist.operation.activity.DatabaseActivity;
import com.byted.camp.todolist.operation.activity.DebugActivity;
import com.byted.camp.todolist.operation.activity.SettingActivity;
import com.byted.camp.todolist.ui.NoteListAdapter;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;
    private HandlerThread mWorkThread;
    private  MainHander mainHander;
    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private Note deleteNote;
    private Note updateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWorkThread = new HandlerThread("main_database");
        mWorkThread.start();
        mainHander = new MainHander(mWorkThread.getLooper());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                deleteNote = note;
                mainHander.sendEmptyMessage(2);
                mainHander.sendEmptyMessage(1);
            }

            @Override
            public void updateNote(Note note) {
                updateNote = note;
                mainHander.sendEmptyMessage(3);
                mainHander.sendEmptyMessage(1);
            }
        });
        recyclerView.setAdapter(notesAdapter);
        mainHander.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this,"正确返回了", LENGTH_SHORT).show();
            Message msg = mainHander.obtainMessage();
            msg.what = 1;
            mainHander.sendMessage(msg);
        }
    }

    private List<Note> loadNotesFromDatabase() {

        // TODO 从数据库中查询数据，并转换成 JavaBeans
        List<Note> result = new ArrayList<>();
        List<MyNote> getList = ToDoDatabase.getInstance(this).noteDao().getAll();
        if(getList==null||getList.size()==0){
            result=null;
        }
//        Collections.sort(getList, new Comparator<MyNote>() {
//            @Override
//            public int compare(MyNote o1, MyNote o2) {
//                return o1.priority-o2.priority;
//            }
//        });

    else{
        Collections.sort(getList, new Comparator<MyNote>() {
            @Override
            public int compare(MyNote o1, MyNote o2) {
                return o2.priority-o1.priority;
            }
        });
        Note temp;
        for(MyNote myNote:getList){
            temp=new Note(myNote.tid);
            temp.setContent(myNote.content);
            temp.setDate(myNote.data);
            temp.setState(State.from(Integer.parseInt(myNote.state)));
            temp.setPriority(myNote.priority);
            result.add(temp);
        }
        }
        final List<Note> finalResult = result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notesAdapter.refresh(finalResult);
            }
        });

        return result;
    }

    private void deleteNote() {
        // TODO 删除数据
        Toast.makeText(this,"lxzxixi", LENGTH_SHORT).show();
        MyNote delete =new MyNote();
        delete.priority=deleteNote.getPriority();
        delete.content=deleteNote.getContent();
        delete.data = deleteNote.getDate();
        delete.state=String.valueOf(deleteNote.getState().intValue);
        delete.tid= (int) deleteNote.id;
        ToDoDatabase.getInstance(this).noteDao().delete(delete);
        }

    private void updateNote() {
        // 更新数据
        MyNote update =new MyNote();
        update.priority=updateNote.getPriority();
        update.content=updateNote.getContent();
        update.data = updateNote.getDate();
        update.state=String.valueOf(updateNote.getState().intValue);
        update.tid= (int) updateNote.id;
        ToDoDatabase.getInstance(this).noteDao().update(update);

    }

    private class MainHander extends Handler {

        // 获得所有列表
        public static final int MSG_GET_ALL = 1;
        // 删除
        public static final int MSG_DEL_NOTE = 2;
        // 更新
        public static final int MSG_UPD_NOTE = 3;



        public MainHander(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_GET_ALL:
                    loadNotesFromDatabase();
                    break;
                case MSG_DEL_NOTE:
                    deleteNote();
                    break;
                case MSG_UPD_NOTE:
                    updateNote();
                    break;
                default:
                    break;
            }

        }
    }

}
