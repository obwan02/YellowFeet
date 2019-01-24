package com.yellowfeet.core.graphics;

import static com.yellowfeet.core.graphics.basic.Vertex.BYTE_SIZE;
import static com.yellowfeet.core.graphics.basic.Vertex.genVertex;
import static com.yellowfeet.core.math.Vector2.V2;
import static com.yellowfeet.core.math.Vector3.V3;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import com.sun.istack.internal.Nullable;
import com.yellowfeet.core.Debug;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.graphics.basic.Vertex;
import com.yellowfeet.core.graphics.basic.VertexAttrib;
import com.yellowfeet.core.math.Transform;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.math.Vector3;

public final class Sprite extends Object implements IRenderable {
	
	/*
	 * The attribute pointers for each attribute of the vertex.
	 */
	public static final VertexAttrib SHADER_POS_ATTRIB = new VertexAttrib("pos", 0, 3, GL30.GL_FLOAT, false, BYTE_SIZE, 0);
	public static final VertexAttrib SHADER_TEX_ATTRIB = new VertexAttrib("tex", 1, 2, GL30.GL_FLOAT, false, BYTE_SIZE, 12);
	public static final VertexAttrib SHADER_TID_ATTRIB = new VertexAttrib("tid", 2, 1, GL30.GL_INT  , false, BYTE_SIZE, 20);
	public static final VertexAttrib SHADER_COL_ATTRIB = new VertexAttrib("col", 3, 4, GL30.GL_FLOAT, false, BYTE_SIZE, 24);
	
	
	/*
	 * Anchor is automatically bottom right
	 */
	protected static final int SP_VERT_COUNT = 4;
	protected static final int SP_BYTE_COUNT = BYTE_SIZE * SP_VERT_COUNT; //BYTE_SIZE is from Vertex class
	protected static final int SP_FLOAT_COUNT = SP_BYTE_COUNT / 4; //4 is byte size of float
	protected static final int SP_INDEX_COUNT = 6;
	
	private String _texName;
	private final Vertex[] _vertexData;
	
	public Transform transform;
	
	public Sprite(Vector2 size, @Nullable String texName, Color color) {
		transform = new Transform();
		_texName = texName;
		
		float halfx = size.x / 2;
		float halfy = size.y / 2;
		//Texture ID will be set by SpriteRenderer
		_vertexData = new Vertex[] { 
				genVertex(V3(-halfx, -halfy, 0), V2(0, 0), 0, color),
				genVertex(V3( halfx, -halfy, 0), V2(1, 0), 0, color),
				genVertex(V3( halfx,  halfy, 0), V2(1, 1), 0, color),
				genVertex(V3(-halfx,  halfy, 0), V2(0, 1), 0, color),					
		};
	}
	
	public Sprite(Vector2 size, @Nullable String texName) {
		this(size, texName, Color.Get(Color.WHITE));
	}
	
	public void resize(Vector2 size) {
		float halfx = size.x / 2;
		float halfy = size.y / 2;
		_vertexData[0].pos.set(-halfx, -halfy);
		_vertexData[1].pos.set(halfx, -halfy);
		_vertexData[2].pos.set(halfx,  halfy);
		_vertexData[3].pos.set(-halfx,  halfy);
	}
	
	public Vertex getFurthestVertex(Vector2 dir) {
		int result = 0;
		Vector3 pos = _vertexData[result].pos;
		float maxDot = _vertexData[result].pos.dot(dir);
		
		transform.reloadMatrix();
		for(int i = 1; i < _vertexData.length; i++) {
			Vector3 n = transform.apply(_vertexData[i].pos);
			float dot = n.dot(dir);
			if(dot > maxDot) {
				result = i;
				pos = n;
				maxDot = dot;
			}
		}
		
		return new Vertex(pos, _vertexData[result].texCoords, 0, _vertexData[result].color);
	}
	
	public Vertex[] getVertexData() {
		return _vertexData;
	}
	
	public void setTextureName(String name) {
		_texName = name;
	}
	
	public String getTextureName() {
		return _texName;
	}
	
	public void appendToBuffer(ByteBuffer buffer, int textureId) {
		Debug.Assert(buffer.capacity() % SP_BYTE_COUNT == 0);
		Debug.Assert(buffer.position() % SP_BYTE_COUNT == 0);
		
		transform.reloadMatrix();
		for(Vertex v : _vertexData) {
			Vector3 newPos = transform.apply(v.pos);
			buffer.putFloat(newPos.x).putFloat(newPos.y).putFloat(newPos.z); //Position
			buffer.putFloat(v.texCoords.x).putFloat(v.texCoords.y); //Texture Coords
			buffer.putInt(textureId); //Texture Id
			
			buffer.putFloat(v.color.r).putFloat(v.color.g); 
			buffer.putFloat(v.color.b).putFloat(v.color.a); //Colour
		}
	}
	
	@Override
	public Sprite clone() {
		Sprite sp = new Sprite(_vertexData[2].pos.clone().scale(2), _texName, _vertexData[0].color);
		sp.transform.position = transform.position.clone();
		sp.transform.rotation = transform.rotation;
		sp.transform.scale = transform.scale;
		return sp;
	}
	
}
