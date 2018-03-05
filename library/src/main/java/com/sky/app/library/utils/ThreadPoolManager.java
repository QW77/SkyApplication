package com.sky.app.library.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理
 * @author wjx
 */
public class ThreadPoolManager {

	private static ThreadPoolManager tpm;
	private ExecutorService service;

	private ThreadPoolManager() {
		// 返回java虚拟机可用处理器的数目
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num * 2);
	}

	/**
	 * 实例化
	 * @return
	 */
	public static ThreadPoolManager getInstance() {
		if (null == tpm) {
			synchronized (ThreadPoolManager.class){
				if (null == tpm){
					tpm = new ThreadPoolManager();
				}
			}
		}
		return tpm;
	}

	/**
	 * 添加任务
	 * @param task
	 */
	public void addTask(Runnable task) {
		service.submit(task);
	}
}
