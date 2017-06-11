package com.groupofseven.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupofseven.game.Settings;
import com.groupofseven.model.Renderable;

public class Player implements Renderable {	
	// store all player data here
	
	/** player Sprite */
	Sprite sprite;
	
	private int x;
	private int y;
	
	private void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	private int getX() {
		return x;
	}
	
	private int getY() {
		return y;
	}
	
	public void loadGFX() {

		// init sprite here
		sprite = new Sprite(new Texture("sprites/Ayumi.png"));
				
	}

	// implementation of update()
	public void update(float delta) {
		
	}

	// implementation of render
	public void render(float delta, SpriteBatch batch) {
		update(delta);
		batch = new SpriteBatch();
		batch.begin();
		batch.draw(sprite,
				getX()*Settings.TILE_SIZE, 
				getY()*Settings.TILE_SIZE, 
				Settings.SPRITE_WIDTH, 
				Settings.SPRITE_HEIGHT);
		batch.end();
	}

	public void dispose() {

	}

}