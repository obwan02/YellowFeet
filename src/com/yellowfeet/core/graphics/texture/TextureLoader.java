package com.yellowfeet.core.graphics.texture;

import java.util.HashMap;

import com.sun.istack.internal.Nullable;
import com.yellowfeet.core.util.image.Image;

public final class TextureLoader {

	//So cannot be instantiated
	private TextureLoader() {}
	
	private static final Texture DEFAULT_TEXTURE;
	private static final HashMap<String, Texture> TextureMap = new HashMap<String, Texture>();

	static {		
		try(Image i = new Image("res/default.png"))	{ DEFAULT_TEXTURE = new Texture(i, TextureConfig.DEFAULT_CONFIG); }
		TextureMap.put(null, DEFAULT_TEXTURE);
	}
	
	public static void LoadTexture(Image image, TextureConfig config, String string) {
		if(config == null) config = TextureConfig.DEFAULT_CONFIG;
		TextureMap.put(string, new Texture(image, config));
	}
	
	public static void LoadTexture(Image image, String string) {
		TextureMap.put(string, new Texture(image, TextureConfig.DEFAULT_CONFIG));
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
