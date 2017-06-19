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

		float futureX; // will be calculated to simulate 1 tile in advance with
						// respect to dx

		// Calculate future x.
		// cases: dx == 0 -> future x is current x (no movement)
		// dx == 1 -> future x is trying to move right one tile
		// dx == -1 -> future x is trying to move left one tile
		if (dx == 1) {
			// case 1 ... simulation of 1 tile movement right
			futureX = sprite.getX() + 24;
		} else if (dx == -1) {
			// case: -1 ... simulation of 1 tile movement left
			futureX = sprite.getX() - 24;
		} else {
			// case: 0 or invalid dx value -> no movement
			futureX = sprite.getX();
		}

		float futureY; // will be calculated to simulate 1 tile in advance with
						// respect to dx

		// Calculate future y.
		// cases: dy == 0 -> future y is current y (no movement)
		// dy == 1 -> future y is going to move right one tile
		// dy == -1 -> future y is going to move left one tile
		if (dy == 1) {
			// move 1 tile up
			futureY = sprite.getY() + 24;
		} else if (dx == -1) {
			// move 1 time down
			futureY = sprite.getY() - 24;
		} else {
			// do not move
			futureY = sprite.getY();
		}

		Cell cell = collisionLayer.getCell((int) futureX, (int) futureY);
		boolean collideX = false, collideY = false;
		// begin movement stuff

		if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
			// case: cell exists and the cell is not a blocked tile
			// post: if case is legal, future x is legal. else x is not legal
			// handle the Class1AMap
			if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
				collideX = false;
				collideY = false;
			} else {
				collideX = true;
				collideY = true;
			}

			if (!collideX && !collideY) {
				sprite.setX(futureX);
				sprite.setY(futureY);
			}
		}

		// handle the SecondFloorMap
		else {
			sprite.setX(sprite.getX() + (dx * 24));
			sprite.setY(sprite.getY() + (dy * 24));
		}

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