package com.yellowfeet.core.components;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public final class ResourceLoader {
	
	//Should be 1 for steadier frame rates during loading
	public static final int FINISHED_LOADS_PER_UPDATE = 2;
	
	private final ExecutorService _loadingService;
	private final ArrayList<Future<ResourceLoadedReturn>> _loads;
	
	public final class ResourceLoadedReturn {
		
		public final Resource resource;
		private final ByteBuffer data;
		
		public ResourceLoadedReturn(Resource r, ByteBuffer data) {
			this.resource = r;
			this.data = data;
		}
	}
	
	public ResourceLoader() {
		_loadingService = Executors.newCachedThreadPool();
		_loads = new ArrayList<Future<ResourceLoadedReturn>>();
	}
	
	public void finishLoadedResources() {
		ListIterator<Future<ResourceLoadedReturn>> iter = _loads.listIterator();
		int count = 0;
		while(iter.hasNext()) {
			
			if(count >= FINISHED_LOADS_PER_UPDATE) break;
				
			Future<ResourceLoadedReturn> f = iter.next();
			if(f.isDone()) {
				try {
					ResourceLoadedReturn rlr = f.get();
					rlr.resource.onLoad(rlr.data);
					count++;
				} catch (InterruptedException e) {
					System.err.println("Failed to load resource.");
					e.printStackTrace();
				} catch (ExecutionException e) {
					System.err.println("Failed to load resource.");
					e.printStackTrace();
				} finally {
					iter.remove();
				}
				
				
			}
		}
	}
	
	public void loadResouce(Resource r) {
		Callable<ResourceLoadedReturn> function = () -> {
			ByteBuffer buffer = r.loadData();
			return new ResourceLoadedReturn(r, buffer);
		};
		
		_loads.add(_loadingService.submit(function));
	}
	
	public void stop() {
		_loadingService.shutdown();
	}

	public void forceStop() {
		_loadingService.shutdownNow();
	}
	
}
