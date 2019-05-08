package com.e9ab98e991ab.libcommon.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.e9ab98e991ab.libcommon.library.waterripple.WaterRippleView;
import com.e9ab98e991ab.libcommon.utils.Utils;
import com.parfoismeng.slidebacklib.SlideBack;
import com.parfoismeng.slidebacklib.callback.SlideBackCallBack;

import javax.inject.Inject;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends AppCompatActivity {
//    @Inject
//    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private Activity activity;
    private WaterRippleView waterRippleView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 侧滑返回
         */
        //Slidr.attach(this);
        /***
         * DataBing
         */
        SlideBack.register(this, true, this::finish);

        setContentView(getLayoutId());

        if (waterRippleView == null) {
            waterRippleView = new WaterRippleView(this);
            addContentView(waterRippleView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        initSet();
        initDate();


    }



    /***
     * 绑定layout
     * @return
     */
    public abstract int getLayoutId();



    /***
     * 初始执行方法
     */
    public abstract void initSet();

    /***
     * 初始数据执行方法
     */
    public abstract void initDate();



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SlideBack.unregister(this);
    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return dispatchingAndroidInjector;
//
//    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    @SuppressLint("WrongConstant")
    public void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     * @param fragment
     * @param frameId
     */
    public void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();

    }




    /**
     * 隐藏fragment
     * @param fragment
     */
    public void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();

    }

    /***
     * 沉浸式状态栏
     */
    protected void immersiveStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 移除fragment
     * @param fragment
     */
    public void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 弹出栈顶部的Fragment
     */
    public void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
