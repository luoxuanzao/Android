package com.bytedance.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyView extends VideoView {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
}
