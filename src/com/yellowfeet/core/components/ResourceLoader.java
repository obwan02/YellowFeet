package com.yellowfeet.core.components;

import com.yellowfeet.core.util.async.DynamicTaskManager;
import com.yellowfeet.core.util.async.IDynamicTask;

public class ResourceLoader {
	private final DynamicTaskManager _manager;

	public ResourceLoader() {
		_manager = new DynamicTaskManager();
	}
	
	public void load(IDynamicTask task) {
		_manager.assign(task::load, task::finish);
	}
	
	public void complete() {
		_manager.complete(1);
	}
	
	public final void finish() {
		_manager.stop();
	}
}
