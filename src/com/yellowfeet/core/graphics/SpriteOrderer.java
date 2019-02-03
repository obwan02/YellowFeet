package com.yellowfeet.core.graphics;

import java.util.ArrayList;
import java.util.Collection;

import com.yellowfeet.core.util.ArrayUtil;
import com.yellowfeet.core.util.Sortable;

public final class SpriteOrderer implements Sortable<ISprite> {

	private ArrayList<ISprite> _sprites;
	
	public SpriteOrderer() {
		_sprites = new ArrayList<>();
	}
	
	public void register(ISprite sprite) {
		_sprites.add(sprite);
		ArrayUtil.InsertionSort(_sprites, this);
	}
	
	public void massRegister(ISprite[] sprites) {
		for (ISprite sprite : sprites) _sprites.add(sprite);
		ArrayUtil.QuickSort(_sprites, this);
	}

	public void massRegister(Collection<ISprite> sprites) {
		_sprites.addAll(sprites);
		ArrayUtil.QuickSort(_sprites, this);
	}
	
	public void reorder() {
		ArrayUtil.InsertionSort(_sprites, this);
	}
	
	public void remove(ISprite sp) {
		_sprites.remove(sp);
	}
	
	public void submitAll(IRenderer<ISprite> sp) {
		for (ISprite sprite : _sprites) {
			sp.submit(sprite);
		}
	}
	
	public void removeAll() {
		_sprites.clear();
	}
	
	@Override
	public float getValue(ISprite a) {
		return a.getTransform().position.z;
	}

}
