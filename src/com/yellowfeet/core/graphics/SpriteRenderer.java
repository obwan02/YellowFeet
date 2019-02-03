package com.yellowfeet.core.graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import org.lwjgl.system.MemoryStack;

import com.yellowfeet.core.debug.Debug;
import com.yellowfeet.core.graphics.basic.IBO;
import com.yellowfeet.core.graphics.basic.VAO;
import com.yellowfeet.core.graphics.basic.VBO;
import com.yellowfeet.core.graphics.basic.Vertex;
import com.yellowfeet.core.graphics.texture.Texture;
import com.yellowfeet.core.graphics.texture.TextureLoader;
import com.yellowfeet.core.util.Counter;

public class SpriteRenderer implements IRenderer<ISprite> {
	
	public static final int SPR_MAX_SPRITES = 2500;
	public static final int SPR_MAX_VERTS = SPR_MAX_SPRITES * 4;
	public static final int SPR_MAX_INDICES = SPR_MAX_SPRITES * 6;
	public static final int SPR_MAX_VBO_BYTES = SPR_MAX_VERTS * Vertex.BYTE_SIZE;
	public static final int SPR_MAX_IBO_BYTES = SPR_MAX_INDICES * IBO.BYTE_SIZE;
	
	public static final String SPR_SAMPLERS_NAME = "samplers";
	
	private final VAO _vao;
	private final VBO _vbo;
	private ByteBuffer _vboMappedBuffer;
	
	private final IBO _ibo;
	
	//Holds the current count for the index buffer
	private final Counter _iboCounter;
	
	private Shader _shaderRef;	
	private ICamera _camera;
	private boolean _isSubmitting;
	
	/*
	 * _currentActiveTexture is the texture unit that will be assigned to the
	 * next texture.
	 * 
	 * _activeTextures contains the HashMap between the texture names and texture unit id.
	 * and resets every loop.
	 */
	private int _currentActiveTexture;
	private HashMap<String, Integer> _activeTextures;
	
	public SpriteRenderer(ICamera camera, Shader shader) {
		_isSubmitting = false; _vboMappedBuffer = null;
		_shaderRef = shader;
		_camera = camera;
		_activeTextures = new HashMap<>();
		_currentActiveTexture = 0;
		_iboCounter = new Counter(SPR_MAX_INDICES);
		_vboMappedBuffer = null;
		
		_vao = new VAO();
		_vbo = new VBO(SPR_MAX_VBO_BYTES, GL_STREAM_DRAW);
		_ibo = new IBO(SPR_MAX_IBO_BYTES, GL_STATIC_DRAW);
		initIbo();
		
		_vao.specifyAttribPointer(_vbo, ISprite.SHADER_POS_ATTRIB, true);
		_vao.specifyAttribPointer(_vbo, ISprite.SHADER_TEX_ATTRIB, true);
		_vao.specifyAttribPointer(_vbo, ISprite.SHADER_TID_ATTRIB, true);
		_vao.specifyAttribPointer(_vbo, ISprite.SHADER_COL_ATTRIB, true);
	}
	
	public SpriteRenderer(ICamera camera) {
		this(camera, ShaderGenerator.GenerateDefault());
	}

	private void initIbo() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			
			ShortBuffer buff = stack.mallocShort(SPR_MAX_INDICES);
			int offset = 0;
			//Follows order of 0,1,2,2,3,0
			for(int i = 0; i < SPR_MAX_SPRITES; i++) {
				buff.put((short) offset);
				buff.put((short) (offset+1));
				buff.put((short) (offset+2));
				buff.put((short) (offset+2));
				buff.put((short) (offset+3));
				buff.put((short) offset);
				offset += 4;
			}
			buff.rewind();
			//Send data over
			_ibo.bufferSubData(buff, 0);
		}
	}

	//Finds the corresponding texture id, and if it
	//Doesn't exist, assigns it.
	//Will start a new cycle if too many textures added
	private int spriteTextureId(ISprite sprite) {
		int resultId;
		String texName = sprite.getTextureName();
		
		Integer temp = _activeTextures.get(texName);
		if(temp != null) {
			resultId = temp;
		} else {
			
			/* If texture isn't active in the current draw loop we must:
			 * -Add it to the active textures (and check if there aren't too many textures loaded)
			 * -Make the texture active with the current active texture.
			 * -Set the corresponding sampler in the shader
			 */
			if(_activeTextures.size() > 32) {
				flush();
				begin();
			}
			
			//Increase activeTexture
			resultId = _currentActiveTexture++;
			
			//Get the texture
			Texture newTex = TextureLoader.GetTexture(texName);
			if(newTex == null) newTex = TextureLoader.GetTexture(null);

			//Make the texture active 
			Texture.activeTexture(newTex, resultId);
			_activeTextures.put(texName, resultId);	
			//Set the relevant texture unit
			_shaderRef.setUniformInt(SPR_SAMPLERS_NAME + "[" + String.valueOf(resultId) + "]", resultId);
		}
		
		return resultId;
	}
	
	public void begin() {
		_currentActiveTexture = 0;
		_activeTextures.clear();
		_vbo.orphan();
		_iboCounter.reset();
		_vboMappedBuffer = _vbo.remap(_vboMappedBuffer);
		_vboMappedBuffer.clear();
		_isSubmitting = true;
		submit(_camera.getBackground());
	}
	
	public void submit(ISprite sprite) {
		Debug.Assert(_isSubmitting);
		
		int textureId = spriteTextureId(sprite);
		
		if(!_iboCounter.checkFit(ISprite.SP_INDEX_COUNT)) {
			flush();
			begin();
		}
		
		//Auto increases the buffer
		sprite.appendToBuffer(_vboMappedBuffer, textureId);
		_iboCounter.increase(ISprite.SP_INDEX_COUNT);
	}
	
	public boolean isSubmitting() {
		return _isSubmitting;
	}
	
	public void flush() {
		//Set the uniforms
		_shaderRef.setUniformMat4f("projection_matrix", _camera.getProjectionMatrix());
		_shaderRef.setUniformMat4f("world_matrix", _camera.getWorldMatrix());
		
		_shaderRef.bind();
		_vao.bind();
		_vbo.unmap();
		_vbo.bind();
		_ibo.bind();
		glDrawElements(GL_TRIANGLES, _iboCounter.get(), GL_UNSIGNED_SHORT, 0);
		_vao.unbind();
		_vbo.unbind();
		_ibo.unbind();
		_shaderRef.unbind();
		
		_isSubmitting = false;
	}

	@Override
	public void destroy() {
		_vbo.delete();
		_vao.delete();
	}

}
