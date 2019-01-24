package com.yellowfeet.core.graphics.basic;

import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.math.Vector3;


public class Vertex {
	
	/*
	 * Texture ID is never set until sent to the renderer
	 */
	
	public static final int BYTE_SIZE = 12 + 8 + 4 + 16;
	
	public Vector3 pos;
	public Vector2 texCoords; //Texture Coords
	public int     tid; //Texture ID
	public Color   color;
	
	public static Vertex genVertex(Vector3 pos, Vector2 texCoords, int tid, Color col) {
		return new Vertex(pos, texCoords, tid, col);
	}
	
	public Vertex(Vector3 pos, Vector2 tex, int tid, Color col) {
		this.pos = pos; this.texCoords = tex; this.tid = tid; this.color = col;
	}
}
