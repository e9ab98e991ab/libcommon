package com.e9ab98e991ab.libcommon.base.thread.builder;

import com.e9ab98e991ab.libcommon.base.thread.ThreadPoolType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gaoxin 18-12-20 下午3:41
 * @version V1.0.0
 * @name SingleBuilderBase
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class SingleBuilderBase extends BaseThreadPoolBuilder<ExecutorService> {
	@Override
	protected ExecutorService create() {
		return Executors.newSingleThreadExecutor();
	}
	
	@Override
	protected ThreadPoolType getType() {
		return ThreadPoolType.SINGLE;
	}
}
