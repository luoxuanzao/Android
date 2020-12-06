package com.byted.camp.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.byted.camp.todolist.db.ToDoDatabase;
import com.byted.camp.todolist.db.entity.MyNote;

import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private EditText priorityText;

    private Button addBtn;
    private HandlerThread mWorkThread;
    private TodoHander mWorkHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();

        priorityText = findViewById(R.id.edit_priority);

        mWorkThread = new HandlerThread("todo_database");
        mWorkThread.start();
        mWorkHander = new TodoHander(mWorkThread.getLooper());

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                CharSequence content1 = priorityText.getText();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(content1)){
                    Toast.makeText(NoteActivity.this,
                            "No priority", LENGTH_SHORT).show();
                    return ;
                }

               mWorkHander.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean saveNote2Database(String content,String priority) {
        // TODO 插入一条新数据，返回是否插入成功
        MyNote myNote =new MyNote();
        myNote.tid=TodoListApplication.id;
        TodoListApplication.id++;
        myNote.content = content;
        myNote.data= new Date().toString();
        myNote.state= "0";
        Log.d("priority",priority);
        myNote.priority = Integer.parseInt(priority);
        ToDoDatabase.getInstance(this).noteDao().insert(myNote);
//        Toast.makeText(this,"调到这里了", LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }
  private class TodoHander extends Handler {

        // 增加数据
        public static final int MSG_ADD_DATA = 1;
//        // 删除数据
//        public static final int MSG_DELETE_DATA = 2;
//        // 修改数据
//        public static final int MSG_UPDATE_DATA = 3;
//        // 查询数据
//        public static final int MSG_QUERY_DATA = 4;
//

        public TodoHander(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_ADD_DATA:

                    saveNote2Database(editText.getText().toString().trim(),priorityText.getText().toString().trim());
                    break;
                default:
                    break;
            }
        }
    }
}
