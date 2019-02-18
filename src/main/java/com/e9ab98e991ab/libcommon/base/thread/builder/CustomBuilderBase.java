package com.e9ab98e991ab.libcommon.base.thread.builder;

import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxin 18-12-20 下午3:40
 * @version V1.0.0
 * @name CustomBuilderBase
 * 创建一个自定义线程池
 */
public class CustomBuilderBase extends BaseThreadPoolBuilder<ExecutorService> {
	/** 核心线程池大小 */
	private int mCorePoolSize = 1;
	/** 最大线程池大小 */
	private int mMaximumPoolSize = Integer.MAX_VALUE;
	/** 线程任务空闲保留时间 */
	private long mKeepAliveTime = 60;
	/** 线程任务空闲保留时间单位 */
	private TimeUnit mUnit = TimeUnit.SECONDS;
	/** 任务等待策略 */
	private BlockingQueue<Runnable> mWorkQueue = new SynchronousQueue<Runnable>();
    
	@Override
	protected ExecutorService create() {
		return new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime, mUnit, mWorkQueue);
	}
	
	@Override
	protected ThreadPoolType getType() {
		return ThreadPoolType.CUSTOM;
	}
	
	public CustomBuilderBase corePoolSize(int corePoolSize) {
		mCorePoolSize = corePoolSize;
		return this;
	}

	public CustomBuilderBase maximumPoolSize(int maximumPoolSize) {
		mMaximumPoolSize = maximumPoolSize;
		return this;
	}

	public CustomBuilderBase keepAliveTime(long keepAliveTime) {
		mKeepAliveTime = keepAliveTime;
		return this;
	}

	public CustomBuilderBase unit(TimeUnit unit) {
		mUnit = unit;
		return this;
	}

	public CustomBuilderBase workQueue(BlockingQueue<Runnable> workQueue) {
		mWorkQueue = workQueue;
		return this;
	}
}
