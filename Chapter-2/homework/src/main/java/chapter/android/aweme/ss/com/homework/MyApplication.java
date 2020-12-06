package chapter.android.aweme.ss.com.homework;

import android.app.Application;

public class MyApplication extends Application
{
    private static final String VALUE = "";

    private String value;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量

    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}