package com.yellowfeet.core.graphics;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.opengl.GL30;

public final class ShaderGenerator {
	private ShaderGenerator () {}
	
	private static final String DEFAULT_SHADER_NAME = "testShader";
	private static final String DEFAULT_SHADER_LOCATION = "res/shaders";
	
	private static final String SHADER_VERTEX_EXT = ".vert";
	private static final String SHADER_FRAGMENT_EXT = ".frag";
	
	public static ShaderPart[] GeneratePartsFromName(String name, String folder) {
		File ffolder = new File(folder);
		File[] files = ffolder.listFiles();
		
		ArrayList<ShaderPart> parts = new ArrayList<>();
		for(File f : files) {
			if(f.getName().endsWith(SHADER_VERTEX_EXT))   parts.add(new ShaderPart(f, GL30.GL_VERTEX_SHADER));
			if(f.getName().endsWith(SHADER_FRAGMENT_EXT)) parts.add(new ShaderPart(f, GL30.GL_FRAGMENT_SHADER));
		}
		
		return parts.toArray(new ShaderPart[0]);
	}
	
	public static ShaderPart[] GeneratePartsFromName(String name) {
		return GeneratePartsFromName(name, DEFAULT_SHADER_LOCATION);
	}
	
	public static Shader GenerateShader(String name, String folder) {
		ShaderPart[] parts = GeneratePartsFromName(name, folder);
		return new Shader(parts);
	}
	
	public static Shader GenerateShader(String name) {
		ShaderPart[] parts = GeneratePartsFromName(name);
		return new Shader(parts);
	}
	
	public static Shader GenerateDefault() {
		ShaderPart[] parts = GeneratePartsFromName(DEFAULT_SHADER_NAME, "res");
		return new Shader(parts);
	}
}
