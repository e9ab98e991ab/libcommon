package com.e9ab98e991ab.libcommon.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.e9ab98e991ab.libcommon.library.waterripple.WaterRippleView;
import com.parfoismeng.slidebacklib.SlideBack;
import com.parfoismeng.slidebacklib.callback.SlideBackCallBack;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
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
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
