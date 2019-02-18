package com.e9ab98e991ab.libcommon.base.thread;

import java.util.concurrent.ExecutorService;

/**
 * @author gaoxin 18-12-20 下午3:43
 * @version V1.0.0
 * @name BaseThreadTaskObject
 */
public abstract class BaseThreadTaskObject implements Runnable {
	private ExecutorService mExecutorService = null;
	
	private String mPoolName = null;
	
	public BaseThreadTaskObject() {
		init();
    }

	public BaseThreadTaskObject(String poolName) {
		mPoolName = poolName;
		init();
    }
	
	private void init() {
		mExecutorService = ThreadPoolHelp.Builder.cached().name(mPoolName).builder();
	}
	
	public void start() {
		mExecutorService.execute(this);
	}
}
