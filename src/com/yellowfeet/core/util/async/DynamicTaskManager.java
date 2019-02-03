package com.yellowfeet.core.util.async;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.yellowfeet.core.debug.Debug;
import com.yellowfeet.extra.annotations.General;

/*
 * This class is a thread-safe task executor.
 * It accepts tasks through assignTask(a, b), and 
 * will run them on its own worker threads. The callbacks
 * are then run with the return value, from the thread that
 * call complete().
 */

@General
public class DynamicTaskManager {

	//Holds the callback and return value of the Supplier
	private class ReturnVal {
		public final Consumer<Object> callback;
		public final Object   returnVal;
		
		public ReturnVal(Consumer<Object> func, Object returnVal){
			callback = func;
			this.returnVal = returnVal;
		}
	}
	
	private final ExecutorService _service;
	private final ConcurrentLinkedQueue<Future<ReturnVal>> _queue;
	
	@General
	public DynamicTaskManager() {
		_service = Executors.newCachedThreadPool();
		_queue = new ConcurrentLinkedQueue<Future<ReturnVal>>();
	}
	
	/*
	 * @description completes the assigned tasks. The callback that was assigned will be run
	 * on the thread that called it.
	 */
	@General
	public void complete(int max) {
		
		Iterator<Future<ReturnVal>> iter = _queue.iterator();
		int count = 0;
		
		while(iter.hasNext()) {
			
			Future<ReturnVal> i = iter.next();
			if(count >= max) return;
			
			if(i.isDone()) {
				
				try {
					ReturnVal rv = i.get();
					rv.callback.accept(rv.returnVal);
				} catch (InterruptedException | ExecutionException e) {
					Debug.Warn("Task got interrupted, or failed to execute");
				}
				
				count++;
				iter.remove();
			}
			
		}
	}
	
	@General
	public void assign(Supplier<Object> func, Consumer<Object> callback) {
		Future<ReturnVal> result = _service.submit(() -> {
				Object o = func.get();
				return new ReturnVal(callback, o);
			});
		
		_queue.add(result);
	}
	
	public void stop() {
		_service.shutdown();
	}
	
}
