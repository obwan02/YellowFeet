package com.yellowfeet.core.graphics;

import java.nio.ByteBuffer;

import com.sun.istack.internal.Nullable;
import com.yellowfeet.core.Debug;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.graphics.basic.Vertex;
import com.yellowfeet.core.math.Transform;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.math.Vector3;

public class Sprite extends Object implements ISprite {
	
	private String _texName;
	private final Vertex[] _vertexData;
	
	public Transform transform;
	
	public Sprite(Vector2 size, @Nullable String texName, Color color) {
		transform = new Transform();
		_texName = texName;
		
		//Texture ID will be set by SpriteRenderer
		_vertexData = ISprite.GenSpriteVertexData(size, color);
	}
	
	public Sprite(Vector2 size, @Nullable String texName) {
		this(size, texName, Color.WHITE);
	}
	
	public void resize(Vector2 size) {
		float halfx = size.x / 2;
		float halfy = size.y / 2;
		_vertexData[0].pos.set(-halfx,  halfy);
		_vertexData[1].pos.set( halfx,  halfy);
		_vertexData[2].pos.set( halfx, -halfy);
		_vertexData[3].pos.set(-halfx, -halfy);
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
	
	public void setTextureName(String name) {
		_texName = name;
	}
	
	public String getTextureName() {
		return _texName;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void appendToBuffer(ByteBuffer buffer, int textureId) {
		Debug.Assert(buffer.capacity() % ISprite.SP_BYTE_COUNT == 0);
		Debug.Assert(buffer.position() % ISprite.SP_BYTE_COUNT == 0);
		
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
	
	public Vector2 getSize() {
		return _vertexData[0].pos.clone().absolute().scale(2);
	}
	
	@Override
	public Sprite clone() {
		Sprite sp = new Sprite(_vertexData[1].pos.clone().absolute().scale(2), _texName, _vertexData[0].color);
		sp.transform.position = transform.position.clone();
		sp.transform.rotation = transform.rotation;
		sp.transform.scale = transform.scale;
		return sp;
	}
	
}
