package com.yellowfeet.core.graphics;

public interface IRenderer {

	public void begin();
	public void submit(IRenderable s);
	public void flush();
	public void destroy();
}
