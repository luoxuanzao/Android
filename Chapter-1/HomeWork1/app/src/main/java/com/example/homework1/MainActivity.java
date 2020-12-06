package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private SeekBar s1;
    private ImageView imageView;
    private Switch aSwitch;
    private int pro;
    private HashMap<Integer,Integer>photos;
    private HashMap<Integer,String>names;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            pro = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int temp = pro / 20;
            if (pro - temp * 20 < (temp + 1) * 20) {
                seekBar.setProgress(temp * 20);
                pro = temp * 20;

            } else {
                seekBar.setProgress((temp + 1) * 20);
                pro = (temp + 1) * 20;
                temp = temp + 1;
            }
            Integer resId = photos.get(temp+1);
            String name = names.get(temp+1);

            if (resId != null) {
                imageView.setImageResource(resId);
                if(aSwitch.isChecked()){
                    Toast.makeText(MainActivity.this,"这是"+name, LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    protected  void init(){
        s1=findViewById(R.id.seekBar);
        s1.setOnSeekBarChangeListener(onSeekBarChangeListener);
        imageView = findViewById(R.id.imageView);
        aSwitch=findViewById(R.id.switch1);
        imageView.setImageResource(R.drawable.cat1);
        photos=new HashMap<>();
        names=new HashMap<>();
        photos.put(1,R.drawable.cat1);
        photos.put(2,R.drawable.cat2);
        photos.put(3,R.drawable.cat3);
        photos.put(4,R.drawable.cat4);
        photos.put(5,R.drawable.cat5);
        photos.put(6,R.drawable.cat6);
        names.put(1,"大黑");
        names.put(2,"大黄");
        names.put(3,"老婆");
        names.put(4,"年糕");
        names.put(5,"灰灰");
        names.put(6,"三花花");


    }
}