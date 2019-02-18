package com.e9ab98e991ab.libcommon.base.thread.builder;


import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gaoxin 18-12-20 下午3:40
 * @version V1.0.0
 * @name CachedBuilderBase
 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * 			        线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
 */
public class CachedBuilderBase extends BaseThreadPoolBuilder<ExecutorService> {
	@Override
	protected ExecutorService create() {
		return Executors.newCachedThreadPool();
	}

	@Override
	protected ThreadPoolType getType() {
		return ThreadPoolType.CACHED;
	}
}
