package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class newAppearance  extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_appearance);
        textView=findViewById(R.id.newText);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);

        textView.setText("index为:"+index);
    }


}
