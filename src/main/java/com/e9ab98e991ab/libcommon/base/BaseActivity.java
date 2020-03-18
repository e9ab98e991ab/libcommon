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
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.e9ab98e991ab.libcommon.library.focus.FocusLayout;
import com.e9ab98e991ab.libcommon.library.waterripple.WaterRippleView;
import com.e9ab98e991ab.libcommon.utils.Utils;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private Activity activity;
    private WaterRippleView waterRippleView;

    private boolean isFocus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }

        if (waterRippleView == null) {
            waterRippleView = new WaterRippleView(this);
            addContentView(waterRippleView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        initSet();
        initDate();
    }
    //焦点层 实现OnGlobalFocusChangeListener接口
    private FocusLayout mFocusLayout;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (isFocus()){
            mFocusLayout = new FocusLayout(this);
            bindListener();//绑定焦点变化事件
            addContentView(mFocusLayout,
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));//添加焦点层
        }

    }
    private void bindListener() {
        //获取根元素
        View mContainerView = this.getWindow().getDecorView();//.findViewById(android.R.id.content);
        //得到整个view树的viewTreeObserver
        ViewTreeObserver viewTreeObserver = mContainerView.getViewTreeObserver();
        //给观察者设置焦点变化监听
        viewTreeObserver.addOnGlobalFocusChangeListener(mFocusLayout);
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
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
    }
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

}
