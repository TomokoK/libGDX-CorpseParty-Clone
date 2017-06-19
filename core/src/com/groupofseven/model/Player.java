package com.groupofseven.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Renderable;
import com.groupofseven.screen.Class1AScreen;

public class Player implements Renderable {

	// store references
	private Sprite sprite;

	private final Seven app; // final reference to Game object

	private final PlayerInput input; // final referencce to player input

	public float lastYChange = 384;

	public float lastXChange = 312;

	private TiledMapTileLayer collisionLayer;

	// setup constructor
	public Player(Seven app, TiledMapTileLayer collisionLayer) {
		this.app = app;
		input = new PlayerInput(this);
		this.collisionLayer = collisionLayer;
	}

	public Sprite getSprite() {
		// set properties of the sprite here
		sprite.setSize(32, 48); // set the size of the sprite
		return sprite;
	}

	public Seven getApp() {
		return app;
	}

	public PlayerInput getInput() {
		return input;
	}

	// method to move the sprite
	/*
	 * NOTICE: Collision detection is VERY buggy. Sprite is unable to move
	 * around too much and can walk off the map, causing a nullpointerexception.
	 * If you switch screens with X and move around, the game will also crash
	 * with a nullpointerexception when you switch back to the original screen
	 * and try to move. Collision detection is hard, yo.
	 */
	// here be dragons
	public void move(int dx, int dy) {
		float tileWidth = Settings.TILE_SIZE, tileHeight = Settings.TILE_SIZE;
		float futureX = getLastX() + tileWidth;
		float futureY = getLastY() + tileHeight;
		Cell cell = collisionLayer.getCell((int) futureX, (int) futureY);
		boolean collideX = false, collideY = false;
		// begin movement stuff

		if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
			// case: cell exists and the cell is not a blocked tile
			// post: if case is legal, future x is legal. else x is not legal
			//handle the Class1AMap
			if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
					collideX = false;
				} else {
					collideX = true;
				}
		
			if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
					collideY = false;
				} else {
					collideY = true;
				}
			
			if (!collideX && !collideY) {
				sprite.setX(sprite.getX() + (dx * 24));
				sprite.setY(sprite.getY() + (dx * 24));
			}
		}
		
			//handle the SecondFloorMap
			else {
				sprite.setX(sprite.getX() + (dx * 24));
				sprite.setY(sprite.getY() + (dy * 24));
			}
		
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