package com.groupofseven.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {
	void loadGFX(); // requires graphics context

	void update(float delta);

	void render(float delta, SpriteBatch batch);

	void dispose();
}
