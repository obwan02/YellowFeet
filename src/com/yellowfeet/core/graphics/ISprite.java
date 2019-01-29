package com.yellowfeet.core.graphics;

import static com.yellowfeet.core.graphics.basic.Vertex.BYTE_SIZE;
import static com.yellowfeet.core.graphics.basic.Vertex.genVertex;
import static com.yellowfeet.core.math.Vector2.V2;
import static com.yellowfeet.core.math.Vector3.V3;

import org.lwjgl.opengl.GL30;

import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.graphics.basic.Vertex;
import com.yellowfeet.core.graphics.basic.VertexAttrib;
import com.yellowfeet.core.math.Vector2;

public interface ISprite extends IRenderable {
	
	public Vector2 getSize();
	
	//Static methods
	
	public static Vertex[] GenSpriteVertexData(Vector2 size, Color color) {
		float halfx = size.x / 2;
		float halfy = size.y / 2;
		return new Vertex[] { 
				genVertex(V3(-halfx,  halfy, 0), V2(0, 1), 0, color),
				genVertex(V3( halfx,  halfy, 0), V2(1, 1), 0, color),
				genVertex(V3( halfx, -halfy, 0), V2(1, 0), 0, color),
				genVertex(V3(-halfx, -halfy, 0), V2(0, 0), 0, color),					
		};
	}

	/*
	 * Anchor is automatically bottom right
	 */
	int SP_VERT_COUNT = 4;
	int SP_BYTE_COUNT = BYTE_SIZE * SP_VERT_COUNT; //BYTE_SIZE is from Vertex class
	int SP_FLOAT_COUNT = SP_BYTE_COUNT / 4; //4 is byte size of float
	int SP_INDEX_COUNT = 6;
	
	/*
	 * The attribute pointers for each attribute of the vertex.
	 */
	VertexAttrib SHADER_POS_ATTRIB = new VertexAttrib("pos", 0, 3, GL30.GL_FLOAT, false, BYTE_SIZE, 0);
	VertexAttrib SHADER_TEX_ATTRIB = new VertexAttrib("tex", 1, 2, GL30.GL_FLOAT, false, BYTE_SIZE, 12);
	VertexAttrib SHADER_TID_ATTRIB = new VertexAttrib("tid", 2, 1, GL30.GL_INT  , false, BYTE_SIZE, 20);
	VertexAttrib SHADER_COL_ATTRIB = new VertexAttrib("col", 3, 4, GL30.GL_FLOAT, false, BYTE_SIZE, 24);
}
