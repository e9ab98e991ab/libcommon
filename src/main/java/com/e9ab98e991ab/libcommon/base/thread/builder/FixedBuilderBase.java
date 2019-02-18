package com.e9ab98e991ab.libcommon.base.thread.builder;


import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gaoxin 18-12-20 下午3:41
 * @version V1.0.0
 * @name FixedBuilderBase
 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
 */
public class FixedBuilderBase extends BaseThreadPoolBuilder<ExecutorService> {
	/** 固定线程池  */
	private int mSize = 1;
	
	@Override
	public ExecutorService create() {
		return Executors.newFixedThreadPool(mSize);
	}

	@Override
	protected ThreadPoolType getType() {
		return ThreadPoolType.FIXED;
	}
	
	public FixedBuilderBase setSize(int size) {
		mSize = size;
		return this;
	}
}
