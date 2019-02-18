package com.e9ab98e991ab.libcommon.base.thread.builder;

import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author gaoxin 18-12-20 下午3:41
 * @version V1.0.0
 * @name BaseThreadPoolBuilder
 */
public abstract class BaseThreadPoolBuilder<T extends ExecutorService> {
	static Map<String, ExecutorService> mThreadPoolMap = new ConcurrentHashMap<String, ExecutorService>();

	String mPoolName = "default";
	protected abstract T create();
	protected abstract ThreadPoolType getType();

	public ExecutorService builder() {
		ExecutorService mExecutorService = null;
		if (mThreadPoolMap.get(getType() + "_" + mPoolName) != null) {
			mExecutorService = mThreadPoolMap.get(getType() + "_" + mPoolName);
		} else {
			mExecutorService = create();
			mThreadPoolMap.put(getType() + "_" + mPoolName, mExecutorService);
		}
		return mExecutorService;
	}
	
	public BaseThreadPoolBuilder<T> poolName(String poolName) {
		if (poolName != null && poolName.length() > 0) {
			mPoolName = poolName;
		}
		return this;
	}
}
