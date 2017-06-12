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
	
	public int x;
	public int y;
		
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
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
//		System.out.println(Settings.TILE_SIZE);
//		System.out.println(x);
//		System.out.println(y);
		batch.end();
	}

	public void dispose() {

	}

}