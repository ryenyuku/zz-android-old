package com.adityakapal362.zrytezeneindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import tw.music.streamer.R;

public class ZryteZeneIndicator extends LinearLayout {

    private ArrayList<View> bar_view = new ArrayList<>();
    private Handler bar_handler = new Handler();
    private Runnable bar_runnable;
    private int bar_color = Color.parseColor("#40C4FF");
    private int bar_duration = 150;
    private int bar_max_sizex = (int) getDip(5);
    private int bar_max_sizey = (int) getDip(15);
    private int bar_distance = (int) getDip(3);
    private boolean anim_start = false;

    public ZryteZeneIndicator(Context a, int b) {
        this(a, null, 0, b);
    }

    public ZryteZeneIndicator(Context a) {
        this(a, null);
    }

    public ZryteZeneIndicator(Context a, AttributeSet b) {
        this(a, b, 0);
    }

    public ZryteZeneIndicator(Context a, AttributeSet b, int c) {
        this(a, b, c, 10);
    }

    public ZryteZeneIndicator(Context a, AttributeSet b, int c, int d) {
        super(a, b, c);

        TypedArray typedArray = a.obtainStyledAttributes(b, R.styleable.ZryteZeneIndicator, c, 0);
        boolean start = typedArray.getBoolean(R.styleable.ZryteZeneIndicator_autoStart, anim_start);
        int duration = typedArray.getInteger(R.styleable.ZryteZeneIndicator_barDuration, bar_duration);
        int color = typedArray.getColor(R.styleable.ZryteZeneIndicator_barColor, bar_color);
        typedArray.recycle();

        bar_color = color;
        bar_duration = duration;

        setGravity(Gravity.CENTER);
        for (int i = 0; i < d; i++) {
            View v = new View(a);
            v.setLayoutParams(new LinearLayout.LayoutParams(bar_max_sizex, (int) getDip(1)));
            v.setBackgroundColor(bar_color);
            bar_view.add(v);
            ((LinearLayout.LayoutParams) v.getLayoutParams()).setMargins(bar_distance, bar_distance, bar_distance, bar_distance);
            addView(v);
        }
        bar_runnable = new Runnable() {
            public void run() {
                animateAllBars();
                bar_handler.postDelayed(bar_runnable, bar_duration);
            }
        };

        if (start) {
            start();
        }
    }

    protected void onDraw(Canvas a) {
    }

    public void start() {
        if (anim_start == false) {
            anim_start = true;
            bar_handler.postDelayed(bar_runnable, bar_duration);
        }
    }

    public void stop() {
        if (anim_start) {
            anim_start = false;
            bar_handler.removeCallbacks(bar_runnable);
        }
    }

    public void reset() {
        stop();
        for (int i = 0; i < bar_view.size(); i++) {
            bar_view.get(i).animate().scaleY((int) getDip(1)).setDuration(bar_duration);
        }
    }

    public boolean isRunning() {
        return anim_start;
    }

    public void animateAllBars() {
        for (int i = 0; i < bar_view.size(); i++) {
            bar_view.get(i).animate().scaleY(getRandom(0, bar_max_sizey)).setDuration(bar_duration);
        }
    }

    public void setBarsColor(int a) {
        for (int i = 0; i < bar_view.size(); i++) {
            bar_view.get(i).setBackgroundColor(a);
        }
    }

    public void setBarsColor(ArrayList<Integer> a) {
        for (int i = 0; i < bar_view.size(); i++) {
            bar_view.get(i).setBackgroundColor(a.get(i));
        }
    }

    public void setBarsColor(int a, int b, int c, int d, int e) {
        for (int i = 0; i < bar_view.size(); i++) {
            GradientDrawable s = new GradientDrawable();
            s.setShape(GradientDrawable.RECTANGLE);
            s.setCornerRadii(new float[]{(float) b, (float) b, (float) c, (float) c, (float) d, (float) d, (float) e, (float) e});
            s.setColor(a);
            bar_view.get(i).setBackground(s);
        }
    }

    public void setBarsColor(ArrayList<Integer> a, int b, int c, int d, int e) {
        for (int i = 0; i < bar_view.size(); i++) {
            GradientDrawable s = new GradientDrawable();
            s.setShape(GradientDrawable.RECTANGLE);
            s.setCornerRadii(new float[]{(float) b, (float) b, (float) c, (float) c, (float) d, (float) d, (float) e, (float) e});
            s.setColor(a.get(i));
            bar_view.get(i).setBackground(s);
        }
    }

    public void setDuration(int a) {
        bar_duration = a;
    }

    public int getBarTotal() {
        return bar_view.size();
    }

    public int getBarSizeX() {
        return bar_max_sizex;
    }

    public void setBarSizeX(int a) {
        bar_max_sizex = a;
        for (int i = 0; i < bar_view.size(); i++) {
            bar_view.get(i).setLayoutParams(new LinearLayout.LayoutParams(a, (int) getDip(1)));
            ((LinearLayout.LayoutParams) bar_view.get(i).getLayoutParams()).setMargins(bar_distance, bar_distance, bar_distance, bar_distance);
            bar_view.get(i).requestLayout();
        }
    }

    public int getBarSizeY() {
        return bar_max_sizey;
    }

    public void setBarSizeY(int a) {
        bar_max_sizey = a;
    }

    public int getBarDuration() {
        return bar_duration;
    }

    public int getBarDistance() {
        return bar_distance;
    }

    public void setBarDistance(int a) {
        bar_distance = a;
        for (int i = 0; i < bar_view.size(); i++) {
            ((LinearLayout.LayoutParams) bar_view.get(i).getLayoutParams()).setMargins(a, a, a, a);
            bar_view.get(i).requestLayout();
        }
    }

    private int getRandom(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    private float getDip(int i) {
        return TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

}
