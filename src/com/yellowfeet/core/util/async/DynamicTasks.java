package com.yellowfeet.core.util.async;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.yellowfeet.core.graphics.texture.TextureConfig;
import com.yellowfeet.core.graphics.texture.TextureLoader;
import com.yellowfeet.core.util.image.Image;

public final class DynamicTasks {
	private DynamicTasks() {}
	
	
	/*
	 * General function for generating loaders
	 */
	public static <T> IDynamicTask GenDynamicTask(Supplier<T> load, Consumer<T> onload) {
		IDynamicTask f = new IDynamicTask() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void finish(Object data) {
				onload.accept((T)data);
			}
			
			@Override
			public Object load() {
				return load.get();
			}
		};
		
		return f; 
	}
	
	public static IDynamicTask TextureDynamicTask(Supplier<Image> imagesupplier, TextureConfig tc, String name) {
		return GenDynamicTask(imagesupplier, (a) -> TextureLoader.LoadTexture(a, tc, name));
	}
}
