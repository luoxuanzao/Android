package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
     * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {
    private ViewGroup viewGroup;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise2);
        viewGroup=findViewById(R.id.exercises2);
        textView = findViewById(R.id.tv_center);
        int total = getAllChildViewCount(viewGroup)+1;
        textView.setText(""+total);
    }

    public int getAllChildViewCount(View view) {
        //todo 补全你的代码
        if(view instanceof ViewGroup){
            ViewGroup v = (ViewGroup)view;
            int num = v.getChildCount();
            int result = 0;
            for(int i=0;i<num;i++){
                result+=getAllChildViewCount(v.getChildAt(i))+1;
            }
            int a = v.getChildCount();
            return result;
        }else{
            return 0;
        }

    }
}
