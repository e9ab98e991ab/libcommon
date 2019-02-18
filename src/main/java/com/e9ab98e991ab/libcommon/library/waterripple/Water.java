package com.e9ab98e991ab.libcommon.library.waterripple;

import android.graphics.Paint;

/**
 * @author gaoxin 18-12-20 下午9:06
 * @version V1.0.0
 * @name Water
 * @mail godfeer@aliyun.com
 * @description 这个是保存小圆的参数设置的javabean
 */
public class Water {

    private float x;   //y坐标|y coordinate
    private float y;   //x坐标|x coordinate
    private Paint p;    //画笔 ｜paint
    private boolean isAnimation;    //是否设置动画监听 |whether animation listener
    private float radius;           //半径|circle radius
    private float strokeWidth;      //画笔宽度 | paint stroke width;
    private int alpha;              //设置alpha  | circler alpha

    public Water() {
    }

    Water(int alpha, boolean isAnimation, Paint p, float radius, float strokeWidth, float x, float y) {
        this.alpha = alpha;
        this.isAnimation = isAnimation;
        this.p = p;
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        this.x = x;
        this.y = y;
    }

    public int getAlpha() {
        return alpha;
    }

    void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    boolean isAnimation() {
        return isAnimation;
    }

    void setAnimation(boolean animation) {
        isAnimation = animation;
    }

    Paint getP() {
        return p;
    }

    void setP(Paint p) {
        this.p = p;
    }

    float getRadius() {
        return radius;
    }

    void setRadius(float radius) {
        this.radius = radius;
    }

    float getStrokeWidth() {
        return strokeWidth;
    }

    void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    float getX() {
        return x;
    }

    void setX(float x) {
        this.x = x;
    }

    float getY() {
        return y;
    }

    void setY(float y) {
        this.y = y;
    }

}
