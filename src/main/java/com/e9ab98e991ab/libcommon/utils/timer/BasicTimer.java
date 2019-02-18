package com.e9ab98e991ab.libcommon.utils.timer;


/***
 *  使用方式
 *单次定时器
   BasicTimer.startOneshot(1500, new BasicTimer.BasicTimerCallback() {
      @Override
      public void onTimer() {

           }
      }
   );


 循环定时器
   BasicTimer vesselBasicTimer = new BasicTimer(new BasicTimer.BasicTimerCallback() {
      @Override
      public void onTimer() {

      }
      });
   vesselBasicTimer.start(5000);


 */
public final class BasicTimer {//对外接口

    static private BasicTimerManager mManager = BasicTimerManager.getInstance();

    public interface BasicTimerCallback{
        void onTimer();
    }

    //注意：不可使用对象调用startOneshot()
    public static void startOneshot(int millisec, BasicTimerCallback cb){
        mManager.startOneshot(new BasicTimer(cb), millisec);
    }

    public BasicTimer(BasicTimerCallback cb){
        mManager.addTimer(this, cb);
    }

    public void start(int millisec){
        mManager.start(this, millisec);
    }

    public void stop(){
        mManager.stop(this);
    }

    //调用cancel()后，定时器将不再可用
    public void cancel(){
        mManager.remove(this);
    }

    public boolean isRunning(){
        return mManager.isRunning(this);
    };

}

