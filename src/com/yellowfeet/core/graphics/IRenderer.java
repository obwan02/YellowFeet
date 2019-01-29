package com.yellowfeet.core.graphics;

public interface IRenderer<T extends IRenderable> {

	public void begin();
	public void submit(T s);
	public void flush();
	public void destroy();
}
