package com.groupofseven.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {
	public void loadGFX(); // requires graphics context

	public void update(float delta);

	public void render(float delta, SpriteBatch batch);

	public void dispose();
}