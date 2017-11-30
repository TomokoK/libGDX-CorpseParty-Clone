package com.groupofseven.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Renderable;
import com.groupofseven.screen.Class1AScreen;

public class Player implements Renderable {

	// store references
	private final Seven app; // final reference to Game object

	private final PlayerInput input; // final reference to player input

	private TiledMapTileLayer collisionLayer;

	public float x;
	public float y;

	public float currentSpeed;

	public int direction = 0;

	// Objects here
	ArrayList<Animation<TextureRegion>> walkAnimation; // declare frame type (texture region)
	Texture walkSheet;
	SpriteBatch spriteBatch;

	// this float is used to track elapsed animation time
	float stateTime;

	// setup constructor
	public Player(Seven app, TiledMapTileLayer collisionLayer) {
		this.app = app;
		input = new PlayerInput(this);
		this.collisionLayer = collisionLayer;
	}

	public Seven getApp() {
		return app;
	}

	public PlayerInput getInput() {
		return input;
	}

	// method to move the sprite
	public void move(int dx, int dy) {
		int tileWidth = Settings.TILE_SIZE, tileHeight = Settings.TILE_SIZE;

		float futureX; // will be calculated to simulate 1 tile in advance with
						// respect to dx
		currentSpeed = 0f;

		// Calculate future x.
		// cases: dx == 0 -> future x is current x (no movement)
		// dx == 1 -> future x is trying to move right one tile
		// dx == -1 -> future x is trying to move left one tile
		if (dx == 1) {
			// case 1 ... simulation of 1 tile movement right
			futureX = x + tileWidth;
			direction = 2;
			currentSpeed = 0.25f;
			// debug line
			System.out.println("Future X is: " + futureX);
		} else if (dx == -1) {
			// case: -1 ... simulation of 1 tile movement left
			futureX = x - tileWidth;
			direction = 1;
			currentSpeed = 0.25f;
			System.out.println("Future X is: " + futureX);
		} else {
			// case: 0 or invalid dx value -> no movement
			futureX = x;
			System.out.println("Future X is the same: " + futureX);
		}

		float futureY; // will be calculated to simulate 1 tile in advance with
						// respect to dx

		// Calculate future y.
		// cases: dy == 0 -> future y is current y (no movement)
		// dy == 1 -> future y is going to move right one tile
		// dy == -1 -> future y is going to move left one tile
		if (dy == 1) {
			// move 1 tile up
			futureY = y + tileHeight;
			direction = 3;
			currentSpeed = 0.25f;
			System.out.println("Future Y is: " + futureY);
		} else if (dy == -1) {
			// move 1 time down
			futureY = y - tileHeight;
			direction = 0;
			currentSpeed = 0.25f;
			System.out.println("Future Y is: " + futureY);
		} else {
			// do not move
			futureY = y;
			System.out.println("Future Y is the same: " + futureY);
		}

		Cell cell = collisionLayer.getCell((int) futureX / tileWidth, (int) futureY / tileHeight);
		boolean collideX = false, collideY = false;
		// begin movement stuff

		if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
			// case: cell exists and the cell is not a blocked tile
			// post: if case is legal, future x is legal. else x is not legal
			// handle the Class1AMap

			if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
				collideX = false;
				collideY = false;
				// debug lines
				System.out.println("cell is not blocked");
				System.out.println("Walk animation size is: " + walkAnimation.size()); // this returns the size of the
																						// ArrayList, not sprite size in
																						// px.
			} else {
				collideX = true;
				collideY = true;
				// debug lines
				System.out.println("cell is blocked");
				System.out.println("Walk animation size is: " + walkAnimation.size()); // need to change
																						// walkAnimation.size() to
																						// something that returns sprite
																						// size, not array list size.
			}

			if (!collideX || !collideY) {
				x = (futureX);
				// debug line
				System.out.println("Current X is: " + futureX);
				y = (futureY);
				// debug line
				System.out.println("Current Y is: " + futureY);
			}
		}

		// handle the SecondFloorMap
		else {
			x = x + (dx * 24);
			y = y + (dy * 24);
			currentSpeed = 0.25f;
		}

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void loadGFX() {
		// This method is called in Seven.java
		// This loads the sprite into the GPU.

		// Load sprite sheet as a texture
		walkSheet = new Texture("sprites/Ayumi.png");
		walkAnimation = new ArrayList<Animation<TextureRegion>>(4);

		// Use the split utility method to create a 2D array of TextureRegions
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / Settings.SPRITE_COLUMNS,
				walkSheet.getHeight() / Settings.SPRITE_ROWS);

		// Cycle through each picture on the selected sprite sheet row
		Animation tmpAnim; for (int i = 0; i < Settings.SPRITE_ROWS; i++) {
			tmpAnim = new Animation<TextureRegion>(0.1f, tmp[i]);
			tmpAnim.setPlayMode(Animation.PlayMode.LOOP);
			walkAnimation.add(tmpAnim);
		}

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;

	}

	// implementation of update()
	public void update(float delta) {

	}

	// implementation of render
	public void render(float delta, SpriteBatch batch) {
		stateTime += (Gdx.graphics.getDeltaTime() * currentSpeed); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);

		batch.draw(currentFrame, (x - 11), y); // Draw current frame at (X, Y)
												// X is offset by -11 as the source sprite sheet isn't a
												// power of two.

	}

	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
	}

}
