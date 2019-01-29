package com.yellowfeet.core.graphics.texture;

import static org.lwjgl.opengl.GL30.*;

import com.yellowfeet.core.util.image.Image;

public class Texture {

	private final int _id;
	
	public Texture(Image image, TextureConfig config) {
		_id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, _id);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, config.magFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, config.wrapTypeS);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, config.wrapTypeT);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.width, image.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getBuffer());
		glGenerateMipmap(GL_TEXTURE_2D);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, _id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void delete() {
		glDeleteTextures(_id);
	}
	
	public static void activeTexture(Texture t, int i) {
		glActiveTexture(GL_TEXTURE0 + i);
		glBindTexture(GL_TEXTURE_2D, (t == null) ? -1 : t._id);
		//unbind();
	}
	
}
