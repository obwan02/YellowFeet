package com.yellowfeet.core.util.async;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.yellowfeet.core.debug.Debug;
import com.yellowfeet.extra.annotations.General;

@General
public final class TaskManager {
	
	private ConcurrentLinkedQueue<Future<Runnable>> _queue;
	private ExecutorService _service;

	@General 
	public TaskManager() {
		_service = Executors.newCachedThreadPool();
		_queue = new ConcurrentLinkedQueue<Future<Runnable>>();
	};
	
	@General
	public void finishFinished(int max) {
		Iterator<Future<Runnable>> iter = _queue.iterator();
		
		int count = 0;
		
		for(Future<Runnable> i = iter.next(); iter.hasNext(); i = iter.next()) {
			
			if(count >= max) return;
			
			if(i.isDone()) {
				
				try {
					i.get().run();
				} catch (InterruptedException | ExecutionException e) {
					Debug.Warn("Task got interrupted, or failed to execute");
				}
				
				count++;
				iter.remove();
			}
		}
	}
	
	@General
	public void finishFinished() {
		finishFinished(Integer.MAX_VALUE);
	}
	
	@General
	public void assignTask(Runnable task, Runnable oncomplete) {
		Callable<Runnable> func = () -> {
			task.run();
			return oncomplete;
		};
		
		_queue.add(_service.submit(func));
	}
	
	@General
	public void stop() {
		_service.shutdown();
	}
	
}
