package com.yellowfeet.core.graphics.texture;

import java.util.HashMap;

import com.sun.istack.internal.Nullable;
import com.yellowfeet.core.util.image.Image;

public final class TextureLoader {

	//So cannot be instantiated
	private TextureLoader() {}
	
	public final static TextureConfig DEFAULT_CONFIG = new TextureConfig(TextureConfig.ZoomFilter.BILINEAR, TextureConfig.WrapType.REPEAT);
	private static final Texture DEFAULT_TEXTURE;
	private static final HashMap<String, Texture> TextureMap = new HashMap<String, Texture>();

	static {		
		try(Image i = new Image("res/default.png")){			
			DEFAULT_TEXTURE = new Texture(i, DEFAULT_CONFIG);
		}
		
		TextureMap.put(null, DEFAULT_TEXTURE);
	}
	
	public static void LoadTexture(Texture tex, String string) {
		TextureMap.put(string, tex);
	}
	
	public static void ClearTexture(String name) {
		TextureMap.get(name).delete();
		TextureMap.remove(name);
	}
	
	public static Texture GetTexture(@Nullable String name) {
		return TextureMap.get(name);
	}
	
	public static void ClearAll() {
		for(String name : TextureMap.keySet()) {
			TextureMap.get(name).delete();
		}
		
		TextureMap.clear();
	}

}
