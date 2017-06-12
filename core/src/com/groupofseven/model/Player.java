package com.groupofseven.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupofseven.game.Settings;
import com.groupofseven.model.Renderable;

public class Player implements Renderable {	
	// store all player data here
	
	/** player Sprite */
	private Sprite sprite;
	
	public int x;
	public int y;
		
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(int dx, int dy) {
		System.out.println(Settings.TILE_SIZE);
		System.out.println(x);
		System.out.println(y);
		x += dx;
		y += dy;
	}
	
	public int getX() {
		System.out.println("getX() = " + x);
		return x;
	}
	
	public int getY() {
		System.out.println("getY() = " + y);
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
		System.out.println("BEFORE batch.draw tile size is " + Settings.TILE_SIZE);
		System.out.println("BEFORE batch.draw x is " + x);
		System.out.println("BEFORE batch.draw y is " + y);
		batch.draw(sprite,
				getX()*Settings.TILE_SIZE, 
				getY()*Settings.TILE_SIZE, 
				Settings.SPRITE_WIDTH, 
				Settings.SPRITE_HEIGHT);
		System.out.println("AFTER batch.draw tile size is " + Settings.TILE_SIZE);
		System.out.println("AFTER batch.draw x is " + x);
		System.out.println("AFTER batch.draw y is " + y);
		batch.end();
	}

	public void dispose() {

	}

}