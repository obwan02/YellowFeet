package com.yellowfeet.core.graphics;

import java.nio.ByteBuffer;

import com.yellowfeet.core.Debug;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.graphics.basic.Vertex;
import com.yellowfeet.core.graphics.texture.TextureMap;
import com.yellowfeet.core.math.Transform;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.math.Vector3;

public class AnimatedSprite implements ISprite {

	public Transform transform;
	private TextureMap _textures;
	private final Vertex[] _vertexData;
	private int _index;
	
	public AnimatedSprite(Vector2 size, TextureMap tm, Color color) {
		_index = 0;
		_textures = tm;
		_vertexData = ISprite.GenSpriteVertexData(size, color);
	}

	public AnimatedSprite(Vector2 size, TextureMap tm) {
		this(size, tm, Color.WHITE);
	}
	
	@Override
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

	@Override
	public String getTextureName() {
		return _textures.get(_index);
	}
	
	public int select(int i) {
		_index = i % _textures.getSize();
		return _index;
	}
	
	public int advance() {
		return select(_index + 1);
	}

	@Override
	public Vector2 getSize() {
		return _vertexData[0].pos.clone().absolute().scale(2);
	}

	@Override
	public Transform getTransform() {
		return transform;
	}

}
