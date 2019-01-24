package com.yellowfeet.core.graphics;

import java.util.ArrayList;
import java.util.Collection;

import com.yellowfeet.core.util.ArrayUtil;
import com.yellowfeet.core.util.Sortable;

public final class SpriteOrderer implements Sortable<Sprite> {

	private ArrayList<Sprite> _sprites;
	
	public SpriteOrderer() {
		_sprites = new ArrayList<>();
	}
	
	public void register(Sprite sprite) {
		_sprites.add(sprite);
		ArrayUtil.InsertionSort(_sprites, this);
	}
	
	public void massRegister(Sprite[] sprites) {
		for (Sprite sprite : sprites) _sprites.add(sprite);
		ArrayUtil.QuickSort(_sprites, this);
	}

	public void massRegister(Collection<? extends Sprite> sprites) {
		_sprites.addAll(sprites);
		ArrayUtil.QuickSort(_sprites, this);
	}
	
	public void reorder() {
		ArrayUtil.InsertionSort(_sprites, this);
	}
	
	public void remove(Sprite sp) {
		_sprites.remove(sp);
	}
	
	public void submitAll(IRenderer sp) {
		for (Sprite sprite : _sprites) {
			sp.submit(sprite);
		}
	}
	
	public void removeAll() {
		_sprites.clear();
	}
	
	@Override
	public float getValue(Sprite a) {
		return a.transform.position.z;
	}

}
