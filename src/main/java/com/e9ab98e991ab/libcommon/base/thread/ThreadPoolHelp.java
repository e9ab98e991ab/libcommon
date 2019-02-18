package com.e9ab98e991ab.libcommon.base.thread;

import com.e9ab98e991ab.libcommon.base.thread.builder.CachedBuilderBase;
import com.e9ab98e991ab.libcommon.base.thread.builder.CustomBuilderBase;
import com.e9ab98e991ab.libcommon.base.thread.builder.FixedBuilderBase;
import com.e9ab98e991ab.libcommon.base.thread.builder.ScheduledBuilderBase;
import com.e9ab98e991ab.libcommon.base.thread.builder.SingleBuilderBase;
import com.e9ab98e991ab.libcommon.base.thread.builder.BaseThreadPoolBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxin 18-12-20 下午3:43
 * @version V1.0.0
 * @name ThreadPoolHelp
 */
class ThreadPoolHelp {
	
	public static class Builder {
		/** 线程名称 */
		private String mName = null;
		/** 线程类型 */
		private ThreadPoolType mType = null;
		/** 固定线程池  */
		private int mSize = 1;
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
		
		private BaseThreadPoolBuilder<ExecutorService> mBaseThreadPoolBuilder = null;
		
		public Builder(ThreadPoolType type) {
			mType = type;
		}
		
		public Builder(ThreadPoolType type, int size) {
			mType = type;
			mSize = size;
		}
		
		public Builder(ThreadPoolType type, 
				int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
			mType = type;
			mCorePoolSize = corePoolSize;
			mMaximumPoolSize = maximumPoolSize;
			mKeepAliveTime = keepAliveTime;
			mUnit = unit;
			mWorkQueue = workQueue;
		}
		
		public static Builder cached() {
			return new Builder(ThreadPoolType.CACHED);
		}
		
		public static Builder fixed(int size) {
			return new Builder(ThreadPoolType.FIXED, size);
		}
		
		public static Builder single() {
			return new Builder(ThreadPoolType.SINGLE);
		}
		
		public static Builder schedule(int size) {
			return new Builder(ThreadPoolType.SCHEDULED, size);
		}
		
		public static Builder custom(int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
			return new Builder(ThreadPoolType.CUSTOM, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		public Builder name(String name) {
			mName = name;
			return this;
		}
		
		public ExecutorService builder() {
			createThreadPoolBuilder();
			return mBaseThreadPoolBuilder.builder();
		}
		
		public ScheduledExecutorService scheduleBuilder() {
			createThreadPoolBuilder();
			if (mBaseThreadPoolBuilder.builder() instanceof ScheduledExecutorService) {
				return (ScheduledExecutorService) mBaseThreadPoolBuilder.builder();
			}
			return null;
		}
		
		private void createThreadPoolBuilder() {
			if (mType == ThreadPoolType.CACHED) {
				mBaseThreadPoolBuilder = new CachedBuilderBase().poolName(mName);
			} else if (mType == ThreadPoolType.FIXED) {
				mBaseThreadPoolBuilder = new FixedBuilderBase().setSize(mSize).poolName(mName);
			} else if (mType == ThreadPoolType.SCHEDULED) {
				mBaseThreadPoolBuilder = new ScheduledBuilderBase().poolName(mName);
			} else if (mType == ThreadPoolType.SINGLE) {
				mBaseThreadPoolBuilder = new SingleBuilderBase().poolName(mName);
			} else if (mType == ThreadPoolType.CUSTOM) {
				mBaseThreadPoolBuilder = new CustomBuilderBase().corePoolSize(mCorePoolSize).maximumPoolSize(mMaximumPoolSize).keepAliveTime(mKeepAliveTime).unit(mUnit).workQueue(mWorkQueue).poolName(mName);
			}
		}
	}
}
