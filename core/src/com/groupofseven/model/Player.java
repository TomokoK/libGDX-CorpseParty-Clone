package com.groupofseven.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Renderable;

public class Player implements Renderable {	

	//store references
	private Sprite sprite;
	
	private final Seven app; //final reference to Game object
	
	private final PlayerInput input;

	public float lastYChange;

	public float lastXChange;
		
	public Player(Seven app) {
		this.app = app;
		input = new PlayerInput(this);
	}
	
	public Sprite getSprite() {
		//set properties of the sprite here
		sprite.setSize(32, 48); // set the size of the sprite
		return sprite;
	}

	public Seven getApp() {
		return app;
	}

	public PlayerInput getInput() {
		return input;
	}

	//method to move the sprite
	public void move(int dx, int dy) {
		sprite.setX(sprite.getX() + (dx * Settings.TILE_SIZE));
		this.lastXChange = (sprite.getX() + (dx * Settings.TILE_SIZE));
		sprite.setY(sprite.getY() + (dy * Settings.TILE_SIZE));
		this.lastYChange = (sprite.getY() + (dy * Settings.TILE_SIZE));
		}
	
	public float getLastY() {
		return lastYChange;
	}
	
	public float getLastX() {
		return lastXChange;
	}
	
	public float getX() {
		return sprite.getX();
	}
	
	public float getY() {
		return sprite.getY();
	}
	
	public void loadGFX() {

		// init sprite here
		sprite = new Sprite(new Texture("sprites/AyumiNoAnims.png"));
						
	}

	// implementation of update()
	public void update(float delta) {
		
	}

	// implementation of render
	public void render(float delta, SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void dispose() {

	}

}