package com.groupofseven.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Renderable;
import com.groupofseven.screen.Class1AScreen;

public class Player implements Renderable {

	// store references
	private final Seven app; // final reference to Game object

	private final PlayerInput input; // final reference to player input

	private TiledMapTileLayer collisionLayer; // reference to the map layer

	// x and y are used for sprite position
	public float spriteX;
	public float spriteY;

	// booleans handled by the PlayerInput class, used to movement
	public boolean movingUp = false;
	public boolean movingDown = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean movingNowhere = false;

	// used to set the speed of sprite sheet cycling
	public float currentSpeed;

	// used for the delay between movement when holding down a key
	public boolean lastMoveHappened = true;
	public float spriteDelay = 0.25f; // the lower the number, the faster the move speed

	// used to set which sprite row we use while moving
	public int direction = 0;

	// set the height and width of the tiles from the settings class
	public int tileWidth = Settings.TILE_SIZE, tileHeight = Settings.TILE_SIZE;

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

		float futureX; // will be calculated to simulate 1 tile in advance with
						// respect to dx

		// Calculate future x.
		// cases: dx == 0 -> future x is current x (no movement)
		// dx == 1 -> future x is trying to move right one tile
		// dx == -1 -> future x is trying to move left one tile
		if (dx == 1) {
			// case 1 ... simulation of 1 tile movement right
			futureX = spriteX + tileWidth;
			direction = 2;
			// debug line
			System.out.println("Future X is: " + futureX);
		} else if (dx == -1) {
			// case: -1 ... simulation of 1 tile movement left
			futureX = spriteX - tileWidth;
			direction = 1;
			System.out.println("Future X is: " + futureX);
		} else {
			// case: 0 or invalid dx value -> no movement
			futureX = spriteX;
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
			futureY = spriteY + tileHeight;
			direction = 3;
			System.out.println("Future Y is: " + futureY);
		} else if (dy == -1) {
			// move 1 time down
			futureY = spriteY - tileHeight;
			direction = 0;
			System.out.println("Future Y is: " + futureY);
		} else {
			// do not move
			futureY = spriteY;
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
			} else {
				collideX = true;
				collideY = true;
				// debug lines
				System.out.println("cell is blocked");
			}

			if (!collideX || !collideY) {
				currentSpeed = 1f;
				float alpha = 0f;
				float beta = 0f;
				float startTime = 0f;
				// TODO: sprite{X,Y} value is being increased by 6 on each keypress instead of 24
				// TODO: Sprite{X,Y} is increasing by 6 due to alpha and beta being 0.25. Set alpha/beta to 1 for full movement
				// TODO: It also looks like interpolation isn't happening
				//
				// setup timing values
				startTime += Gdx.graphics.getDeltaTime();
				float changeInTime = (startTime/spriteDelay);
				// debug lines
				System.out.println("startTime = " + startTime);
				System.out.println("changeInTime = " + changeInTime);
				System.out.println("Pre MathUtils.clamp X values: " + "spriteX = " + spriteX + " futureX = " + futureX);
				System.out.println("Pre MathUtils.clamp Y values: " + "spriteY = " + spriteY + " futureY = " + futureY);
				// set alpha
				System.out.println("Pre MathUtils.clamp alpha = " + alpha);
				System.out.println("Pre MathUtils.clamp startTime / changeInTime = " + (startTime/changeInTime));
				alpha = MathUtils.clamp((startTime/changeInTime), 0f, 1f); // Value is always 0.25f
				System.out.println("Post MathUtils.clamp alpha = " + alpha);
				System.out.println("Post MathUtils.clamp startTime / changeInTime = " + (startTime/changeInTime));
				// interpolate X
				System.out.println("Pre Interpolation.linear.apply X values: spriteX = " + spriteX + " futureX = " + futureX + " alpha = " + alpha);
				spriteX = Interpolation.linear.apply(spriteX, futureX, alpha);
				System.out.println("Post Interpolation.linear.apply X values: spriteX = " + spriteX + " futureX = " + futureX + " alpha = " + alpha);
				// set beta
				System.out.println("Pre MathUtils.clamp beta = " + beta);
				System.out.println("Pre MathUtils.clamp startTime / changeInTime = " + (startTime/changeInTime));
				beta = MathUtils.clamp((startTime/changeInTime), 0f, 1f); // Value is always 0.25f
				System.out.println("Post MathUtils.clamp beta = " + beta);
				System.out.println("Post MathUtils.clamp startTime / changeInTime = " + (startTime/changeInTime));
				// interpolate Y
				System.out.println("Pre Interpolation.linear.apply Y values: spriteY = " + spriteY + " futureY = " + futureY + " beta = " + beta);
				spriteY = Interpolation.linear.apply(spriteY, futureY, beta);
				System.out.println("Post Interpolation.linear.apply Y values: spriteY = " + spriteY + " futureY = " + futureY + " beta = " + beta);
				System.out.println("-------------------------------------------------------------------------------------------------------------");
			}
		}

		// handle the SecondFloorMap
		// TODO delete this section, do all checks in first bit above when all
		// collision detection is added.
		else {
			spriteX = spriteX + (dx * tileWidth);
			spriteY = spriteY + (dy * tileHeight);
			currentSpeed = 1f;
		}

	}

	public float getX() {
		return spriteX;
	}

	public float getY() {
		return spriteY;
	}

	public void loadGFX() {
		// This method is called in Seven.java
		// This loads the sprite into the GPU.

		// Load sprite sheet as a texture
		walkSheet = new Texture("sprites/Seiko.png");
		walkAnimation = new ArrayList<Animation<TextureRegion>>(4);

		// Use the split utility method to create a 2D array of TextureRegions
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / Settings.SPRITE_COLUMNS,
				walkSheet.getHeight() / Settings.SPRITE_ROWS);

		// Cycle through each picture on the selected sprite sheet row
		Animation tmpAnim;
		for (int i = 0; i < Settings.SPRITE_ROWS; i++) {
			tmpAnim = new Animation<TextureRegion>(0.15f, tmp[i]);
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

	public void movement() {
		if (lastMoveHappened == true) {
			if (movingNowhere) {
				// don't do things
			} else if (movingUp && !movingDown && !movingLeft && !movingRight) {
				System.out.println("W activated in movement()"); // debug line
				lastMoveHappened = false;
				move(0, 1);

				Timer.schedule(new Task() {
					@Override
					public void run() {
						lastMoveHappened = true;
					}
				}, spriteDelay);
			} else if (movingDown && !movingUp && !movingLeft && !movingRight) {
				System.out.println("S activated in movement()"); // debug line
				lastMoveHappened = false;
				move(0, -1);

				Timer.schedule(new Task() {
					@Override
					public void run() {
						lastMoveHappened = true;
					}
				}, spriteDelay);
			} else if (movingLeft && !movingDown && !movingUp && !movingRight) {
				System.out.println("A activated in movement()"); // debug line
				lastMoveHappened = false;
				move(-1, 0);

				Timer.schedule(new Task() {
					@Override
					public void run() {
						lastMoveHappened = true;
					}
				}, spriteDelay);
			} else if (movingRight && !movingUp && !movingDown && !movingLeft) {
				System.out.println("D activated in movement()"); // debug line
				lastMoveHappened = false;
				move(1, 0);

				Timer.schedule(new Task() {
					@Override
					public void run() {
						lastMoveHappened = true;
					}
				}, spriteDelay);
			}
			return;
		}
	}

	// implementation of render
	public void render(float delta, SpriteBatch batch) {
		stateTime += (Gdx.graphics.getDeltaTime() * currentSpeed); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		if (currentSpeed != 0f) {
			TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
			batch.draw(currentFrame, (spriteX - 11), spriteY); // Draw current frame at (X, Y)
			// X is offset by -11 as the source sprite sheet isn't a
			// power of two.
			//// System.out.println(stateTime); // debug line
		} else if (currentSpeed == 0f) {
			TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(3f, false); // Don't
			// draw the sprite mid animation if you are against a blocked tile
			batch.draw(currentFrame, (spriteX - 11), spriteY); // Draw current frame at (X, Y)
			// X is offset by -11 as the source sprite sheet isn't a
			// power of two.
			//// System.out.println("No stateTime!"); // debug line
		}

		// call the movement method every frame, allowing for continuous input
		movement();

	}

	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
	}

}
