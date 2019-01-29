package com.yellowfeet.core.graphics.texture;

import com.yellowfeet.core.Debug;
import com.yellowfeet.core.util.image.Image;

public final class TextureMap {
	
	private final String[] _texNames;
	
	private TextureMap(String[] textures) {
		_texNames = textures;
	}
	
	public String get(int i) {
		if(i >= 0 && i < _texNames.length)
			throw new IndexOutOfBoundsException();
		
		return _texNames[i];
	}
	
	public int getSize() {
		return _texNames.length;
	}

	/*
	 * @param("data") the image that is to be split and loaded
	 * @param("subwidth") @param("subheight") the width and height of each texture
	 */
	public static TextureMap LoadMap(Image data, int subwidth, int subheight, String name) {
		Image[] images = ParseImage(data, subwidth, subheight);
		String[] textures = new String[images.length];
		
		for(int i = 0; i < images.length; i++) {
			String tname = name + "_" + String.valueOf(i);
			TextureLoader.LoadTexture(new Texture(images[i], TextureLoader.DEFAULT_CONFIG), tname);
			textures[i] = tname;
			images[i].free();
		}
		
		return new TextureMap(textures);
	}

	public static TextureMap LoadMap(Image[] data, String name) {
		String[] textures = new String[data.length];
		
		for(int i = 0; i < data.length; i++) {
			String tname = name + "_" + String.valueOf(i);
			TextureLoader.LoadTexture(new Texture(data[i], TextureLoader.DEFAULT_CONFIG), tname);
			textures[i] = tname;
		}
		
		return new TextureMap(textures);
	}
	
	/*
	 * Parses the map in row-major order.
	 */
	public static Image[] ParseImage(Image image, int subwidth, int subheight) {
		Debug.WarnAssert((image.width % subwidth) == 0, "Provided image width is not directly compatible provided sprite size");
		Debug.WarnAssert((image.height % subheight) == 0, "Provided image height is not directly compatible provided sprite size");
		
		int wcount = image.width / subwidth;
		int hcount = image.height / subheight;
		
		Image[] result = new Image[wcount * hcount];
		for(int i = 0; i < hcount; i++) {
			for(int j = 0; j < wcount; j++) {
				result[i * hcount + j] = image.makeSubImage(j * subwidth, i * subheight, subwidth, subheight);
			}
		}
		
		return result;
	}
}
