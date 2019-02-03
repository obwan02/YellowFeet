package com.yellowfeet.core.util.async;

import com.yellowfeet.extra.annotations.Internal;
import com.yellowfeet.extra.annotations.UserDefined;

@UserDefined
public interface IDynamicTask {
	
	//Called to load all data (called on worker thread)
	@Internal
	@UserDefined
	public Object load();	

	//Called in update to work with the loaded data (so OpenGL calls work)
	@Internal
	@UserDefined
	public void finish(Object data);
	

	
}
