package com.e9ab98e991ab.libcommon.library.waterripple;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxin 18-12-20 下午9:06
 * @version V1.0.0
 * @name WaterRippleView
 * @mail godfeer@aliyun.com
 * @description  点击水波纹实现类 TODO 待优化 目前内部配置属性
 */
public class WaterRippleView extends View {
    /***
     * 点击横坐标
     */
    private float mActionDownX;
    /**
     * 点击纵坐标
     */
    private float mActionDownY;
    private List<Water> mWater;
    /**
     * 增加笔画宽度
     */
    private float increaseStrokeWidth = 0.1f;
    /**
     * 画笔宽度
     */
    private float baseStrokeWidth = 2;
    /**
     * 增加半径
     */
    private float increaseRadius = 15;
    /**
     * 基础半径
     */
    private float baseRadius = 2;
    /**
     * 最大边界
     */
    private int destiny = 30;

    private boolean fixed = true;
    private float fixedSize = 10;

    public WaterRippleView(Context context) {
        this(context, null);

    }

    public WaterRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public WaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWater = new CopyOnWriteArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float mRadius;
    private boolean mIsAnimation;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Water w : mWater) {
            if (w.getP().getAlpha() == 0) {
                mWater.remove(w);
            } else {
                if (w.isAnimation()) {
                    canvas.drawCircle(w.getX(), w.getY(), w.getRadius(), w.getP());
                } else {
                    radiusAnimation(w, 1, TimeUnit.SECONDS);
                }
            }
        }
    }

    private void radiusAnimation(Water w, long time, TimeUnit unit) {
        PropertyValuesHolder radius = PropertyValuesHolder.ofFloat("radius", getFloat(PaintType.PaintTypeMode.TYPE_RADIUS));
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", getFloat(PaintType.PaintTypeMode.TYPE_ALPHA));
        PropertyValuesHolder strokeWidth = PropertyValuesHolder.ofFloat("strokeWdith", getFloat(PaintType.PaintTypeMode.TYPE_STROKE_WIDTH));
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(radius, alpha, strokeWidth).setDuration(getTime(time, unit));
        valueAnimator.addUpdateListener(new MyAnimatorListener(w));
        valueAnimator.start();
    }

    public long getTime(long time, TimeUnit unit) {
        return unit.toMillis(time);
    }


    class MyAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
        private Water w;

        public MyAnimatorListener(Water w) {
            this.w = w;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float aniRadius = (float) valueAnimator.getAnimatedValue("radius");
            float aniAlpha = (float) valueAnimator.getAnimatedValue("alpha");
            float aniStrokeWdith = (float) valueAnimator.getAnimatedValue("strokeWdith");
//            Log.d("MyAnimatorListener", "aniAlpha:" + aniAlpha);
//            Log.d("MyAnimatorListener", "aniRadius:" + aniRadius);
//            Log.d("MyAnimatorListener", "aniStrokeWdith:" + aniStrokeWdith);
            w.setRadius(aniRadius);
            w.setAnimation(true);
            w.getP().setStrokeWidth(aniStrokeWdith);
            w.getP().setAlpha((int) aniAlpha);
            w.setStrokeWidth(aniStrokeWdith);
            w.setAlpha((int) aniAlpha);
            postInvalidate();
        }
    }


    private float[] getFloat(@PaintType.PaintTypeMode String type) {
        float[] floats = new float[destiny];
        if (type.equals(PaintType.PaintTypeMode.TYPE_RADIUS)) {
            for (int i = 0; i < destiny; i++) {
                if (fixed) {
                    floats[i] = (float) (baseRadius * (i + increaseRadius));
                } else {
                    floats[i] = fixedSize;
                }
            }
        } else if (type.equals(PaintType.PaintTypeMode.TYPE_ALPHA)) {
            for (int i = 0; i < destiny; i++) {
                floats[i] = (float) (255 - (i + 1) * (255 / destiny));
            }
            floats[destiny - 1] = 0;
        } else if (type.equals(PaintType.PaintTypeMode.TYPE_STROKE_WIDTH)) {
            for (int i = 0; i < destiny; i++) {
                floats[i] = (float) (baseStrokeWidth + (1 + i) * increaseStrokeWidth);
            }
        }
        return floats;
    }

    private boolean mIsActionDown;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /*
         * 多点触控
         */
        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            mActionDownX = event.getX(i);
            mActionDownY = event.getY(i);
            mWater.add(new Water(0, false, getPaint(getRadomColor()), 0, 0, mActionDownX, mActionDownY));
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    mWater.add(new Water(0, false, getPaint(getRadomColor()), 0, 0, mActionDownX, mActionDownY));

                }
            }, 150);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    mWater.add(new Water(0, false, getPaint(getRadomColor()), 0, 0, mActionDownX, mActionDownY));

                }
            }, 350);

            postInvalidate();
        }

        /*
         * 单点触控
         */
  /* int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
           *//**//**//**//*     invalidate();
                break;*//**//**//**//*
            case MotionEvent.ACTION_MOVE:
                mActionDownX = event.getX();
                mActionDownY = event.getY();
                mWater.add(new Water(0, false, getPaint(getRadomColor()), 0, 0, mActionDownX, mActionDownY));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }*/

        return super.dispatchTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);


    }

    @PaintType.PaintColor
    public int getRadomColor() {
        int[] colors = {
                Color.parseColor("#FF0000"),
                Color.parseColor("#FF7F00"),
                Color.parseColor("#FFFF00"),
                Color.parseColor("#00FF00"),
                Color.parseColor("#00FFFF"),
                Color.parseColor("#0000FF"),
                Color.parseColor("#8B00FF")
        };
        int i = new Random().nextInt(colors.length);
        return colors[i];
    }

    private Paint getPaint(@PaintType.PaintColor int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setColor(color);
        //虚线配置
        //paint.setPathEffect(new DashPathEffect(new float[]{100, 100}, 0));

        return paint;
    }

    private int getPaintAlpha() {

        return 0;
    }

}