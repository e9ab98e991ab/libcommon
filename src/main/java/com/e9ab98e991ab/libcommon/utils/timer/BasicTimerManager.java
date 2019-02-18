package com.e9ab98e991ab.libcommon.utils.timer;


import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


final class BasicTimerManager {
    private static BasicTimerManager sInstance;
    /***
     * 必须使用并发类
     */
    private Map<BasicTimer, BasicTimerInfo> mInfos = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    private static final int BASE_INTERAL_MILLISEC = 100;

    private BasicTimerManager(){

        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                doRun();
            }
        },0, BASE_INTERAL_MILLISEC, TimeUnit.MILLISECONDS);

    }

    public static BasicTimerManager getInstance(){
        if (sInstance == null){
            sInstance = new BasicTimerManager();
        }

        return sInstance;
    }

    public void addTimer(BasicTimer timer, BasicTimer.BasicTimerCallback cb){
        BasicTimerInfo info = new BasicTimerInfo();
        info.cb = cb;
        info.init = Integer.MAX_VALUE;
        info.count = Integer.MAX_VALUE;

        mInfos.put(timer, info);
    }

    public void start(BasicTimer timer, int millisec){
        startTimer(timer, millisec, false);
    }

    public void startOneshot(BasicTimer timer, int millisec){
        startTimer(timer, millisec, true);
    }

    public void stop(BasicTimer timer){
        if (mInfos.containsKey(timer)){
            BasicTimerInfo info = mInfos.get(timer);
            info.init = Integer.MAX_VALUE;
            info.count = Integer.MAX_VALUE;
        }
    }

    public void remove(BasicTimer timer){
        if (mInfos.containsKey(timer)){
            mInfos.remove(timer);
        }
    }

    public boolean isRunning(BasicTimer timer){
        boolean running = false;
        if (mInfos.containsKey(timer)){
            BasicTimerInfo info = mInfos.get(timer);

            running = !(info.init == Integer.MAX_VALUE && info.count == Integer.MAX_VALUE);
        }
        return running;
    }

    private void startTimer(BasicTimer timer, int millisec, boolean oneshot){
        if (mInfos.containsKey(timer)){
            BasicTimerInfo info = mInfos.get(timer);
            info.count = millisec / BASE_INTERAL_MILLISEC;
            info.init = millisec / BASE_INTERAL_MILLISEC;
            info.oneshot = oneshot;

            //保证不能为0
            info.count = Math.max(info.count, 1);
            info.count = Math.max(info.init, 1);
        }
    }

    private void doRun(){
        List<Object> ls = new ArrayList<>();

        for (Map.Entry entry: mInfos.entrySet()){
            BasicTimerInfo info = (BasicTimerInfo)entry.getValue();
            if(--info.count == 0) {
                if (info.oneshot){
                    info.count = Integer.MAX_VALUE;
                    info.init = Integer.MAX_VALUE;
                }else {
                    info.count = info.init;
                }
                //先保存所有到时的定时器，统一发送到主线程处理
                ls.add(entry);
            }
        }

        if (!ls.isEmpty()){
            Message message = new Message();
            message.what = 0;
            message.obj = ls;
            mHander.sendMessage(message);
        }
    }

    private Handler mHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            List<Object> ls = (List<Object>) msg.obj;
            for (Object o: ls){
                Map.Entry entry = (Map.Entry)o;
                BasicTimer timer = (BasicTimer)entry.getKey();
                BasicTimerInfo info = (BasicTimerInfo)entry.getValue();

                ((BasicTimer.BasicTimerCallback)info.cb).onTimer();
                if (info.oneshot){ //单次定时到时后，删除定时器对象
                    remove(timer);
                }
            }

            return false;
        }
    });
}

