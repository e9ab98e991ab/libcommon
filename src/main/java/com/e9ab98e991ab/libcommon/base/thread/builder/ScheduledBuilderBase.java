package com.e9ab98e991ab.libcommon.base.thread.builder;


import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author gaoxin 18-12-20 下午3:40
 * @version V1.0.0
 * @name ScheduledBuilderBase
 * 创建一个定长线程池，支持定时及周期性任务执行。
 */
public class ScheduledBuilderBase extends BaseThreadPoolBuilder<ExecutorService> {
	/** 固定线程池大小 */
	private int mSize = 1;
	
    private ScheduledExecutorService mExecutorService = null;
    
	@Override
	protected ScheduledExecutorService create() {
		return Executors.newScheduledThreadPool(mSize);
	}
	
	@Override
	protected ThreadPoolType getType() {
		return ThreadPoolType.SCHEDULED;
	}
	
	@Override
	public ScheduledExecutorService builder() {
		if (mThreadPoolMap.get(getType() + "_" + mPoolName) != null) {
			mExecutorService = (ScheduledExecutorService)mThreadPoolMap.get(getType() + "_" + mPoolName);
		} else {
			mExecutorService = create();
			mThreadPoolMap.put(getType() + "_" + mPoolName, mExecutorService);
		}
		return mExecutorService;
	}
	
	public ScheduledExecutorService getExecutorService() {
		return mExecutorService;
	}
	
	public ScheduledBuilderBase size(int size) {
		this.mSize = size;
		return this;
	}
}
