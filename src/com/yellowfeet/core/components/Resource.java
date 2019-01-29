package com.yellowfeet.core.components;

import java.nio.ByteBuffer;

import com.yellowfeet.extra.annotations.EngineMethod;

public interface Resource {
	
	//Called to load all data into a ByteBuffer (called on worker thread)
	@EngineMethod
	public ByteBuffer loadData();
	
	//Called in update to work with the loaded data (so OpenGL calls work)
	@EngineMethod
	public void onLoad(ByteBuffer data);
}
