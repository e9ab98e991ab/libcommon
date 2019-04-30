package com.e9ab98e991ab.libcommon.utils.toast;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.e9ab98e991ab.libcommon.R;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * @author gaoxin 18-12-24 下午5:25
 * @version V1.0.0
 * @name ToastyUtils
 * @mail godfeer@aliyun.com
 * @description Toast辅助相关类
 */
public class ToastyUtils {


    private static Config config = new Config();

    public ToastyUtils() {
        resetToasty();
    }

    /***
     * 失败
     * @param msg X
     */
    public static void error(@StringRes int msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_clear_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 失败
     * @param msg X
     */
    public static void error(CharSequence msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_clear_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 成功
     * @param msg X
     */
    public static void success(@StringRes int msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_check_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 成功
     * @param msg X
     */
    public static void success(CharSequence msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_check_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 信息
     * @param msg X
     */
    public static void info(@StringRes int msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_info_outline_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 信息
     * @param msg X
     */
    public static void info(@NonNull CharSequence msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_info_outline_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 警告
     * @param msg X
     */
    public static void warning(@StringRes int msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_error_outline_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 警告
     * @param msg X
     *
     */
    public static void warning(CharSequence msg) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), R.drawable.ic_error_outline_white_48dp), Color.parseColor("#FFA900"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    /***
     * 普通
     * @param msg X
     */
    public static void normal(@StringRes int msg) {
        Toasty.custom(getConfig().getContext(), msg, null, Color.parseColor("#353A3E"), Toast.LENGTH_SHORT, false, getConfig().getShouldTint()).show();
    }

    public static void normal(@NonNull CharSequence msg) {
        Toasty.custom(getConfig().getContext(), msg, null, Color.parseColor("#353A3E"), Toast.LENGTH_SHORT, false, getConfig().getShouldTint()).show();
    }

    /***
     * 普通带图标
     * @param msg X
     */
    public static void normal(@StringRes int msg, @DrawableRes int icon) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), icon), Color.parseColor("#353A3E"), Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
    }

    public static void custom(@StringRes int msg, @DrawableRes int icon,
                              @ColorInt int tintColor, int duration,
                              boolean withIcon, boolean shouldTint) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), icon),
                tintColor, duration, withIcon, shouldTint).show();
        resetToasty();
    }

    public static void custom(@StringRes int msg, @DrawableRes int icon,
                              @ColorInt int tintColor) {
        Toasty.custom(getConfig().getContext(), msg, Toasty.getDrawable(getConfig().getContext(), icon),
                tintColor, Toast.LENGTH_SHORT, true, getConfig().getShouldTint()).show();
        resetToasty();

    }
    public static Config getConfig() {
        return config ;
    }

    /***
     * Use this if you want to use the conf
     */
    private static void resetToasty() {
        Toasty.Config.reset();
    }

    public static class Config {
        private boolean shouldTint;
        private int textSize;
        private Context context;

        private Config() {
        }

        public Config setShouldTint(boolean shouldTint) {
            this.shouldTint = shouldTint;
            return this;
        }

        public Config setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        private boolean getShouldTint() {
            return shouldTint;
        }

        public int getTextSize() {
            return textSize;
        }


        public Config init(Context context) {
            this.context = context;
            return this;
        }

        public Context getContext() {
            return context;
        }
    }
}
