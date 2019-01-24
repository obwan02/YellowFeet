package com.yellowfeet.core.graphics.texture;

import static org.lwjgl.opengl.GL30.*;

public final class TextureConfig {

	public enum ZoomFilter {
		NEAREST,
		BILINEAR
	}
	
	public enum WrapType {
		REPEAT,
		MIRRORED_REPEAT,
		EDGE_CLAMP,
		BORDER_CLAMP
	}
	
	protected final int magFilter;
	protected final int wrapTypeS;
	protected final int wrapTypeT;
	
	public TextureConfig(ZoomFilter mag, WrapType x, WrapType y) {
		magFilter = ParseZoomFilter(mag);
		wrapTypeS = ParseWrapType(x);
		wrapTypeT = ParseWrapType(y);
	}
	
	public TextureConfig(ZoomFilter mag, WrapType type) {
		this(mag, type, type);
	}
	
	private static int ParseZoomFilter(ZoomFilter filter) {
		switch(filter) {
		case NEAREST:
			return GL_NEAREST;
		case BILINEAR:
			return GL_LINEAR;
		default:
			throw new RuntimeException("Invalid enum.");
		}
	}
	
	private static int ParseWrapType(WrapType type) {
		switch(type) {
		case REPEAT:
			return GL_REPEAT;
		case MIRRORED_REPEAT:
			return GL_MIRRORED_REPEAT;
		case EDGE_CLAMP:
			return GL_CLAMP_TO_EDGE;
		case BORDER_CLAMP:
			return GL_CLAMP_TO_BORDER;
		default:
			throw new RuntimeException("Invalid enum.");
		}
	}
}
