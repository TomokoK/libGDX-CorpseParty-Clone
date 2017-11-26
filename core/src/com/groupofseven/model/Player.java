package com.groupofseven.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private Sprite sprite;

	private final Seven app; // final reference to Game object

	private final PlayerInput input; // final reference to player input

	private TiledMapTileLayer collisionLayer;
	
	// Objects here
	Animation<TextureRegion> walkAnimation; // declare frame type (texture region)
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

	public Sprite getSprite() {
		// set properties of the sprite here
		//sprite.setSize(32, 48); // set the size of the sprite
		return sprite;
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

		// Calculate future x.
		// cases: dx == 0 -> future x is current x (no movement)
		// dx == 1 -> future x is trying to move right one tile
		// dx == -1 -> future x is trying to move left one tile
		if (dx == 1) {
			// case 1 ... simulation of 1 tile movement right
			futureX = sprite.getX() + tileWidth;
			System.out.println(futureX);
		} else if (dx == -1) {
			// case: -1 ... simulation of 1 tile movement left
			futureX = sprite.getX() - tileWidth;
			System.out.println(futureX);
		} else {
			// case: 0 or invalid dx value -> no movement
			futureX = sprite.getX();
			System.out.println(futureX);
		}

		float futureY; // will be calculated to simulate 1 tile in advance with
						// respect to dx

		// Calculate future y.
		// cases: dy == 0 -> future y is current y (no movement)
		// dy == 1 -> future y is going to move right one tile
		// dy == -1 -> future y is going to move left one tile
		if (dy == 1) {
			// move 1 tile up
			futureY = sprite.getY() + tileHeight;
			System.out.println(futureY);
		} else if (dy == -1) {
			// move 1 time down
			futureY = sprite.getY() - tileHeight;
			System.out.println(futureY);
		} else {
			// do not move
			futureY = sprite.getY();
			System.out.println(futureY);
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
				System.out.println("cell is not blocked");
			} else {
				collideX = true;
				collideY = true;
				System.out.println("cell is blocked");
			}

			if (!collideX || !collideY) {
				sprite.setX(futureX);
				System.out.println(futureX);
				sprite.setY(futureY);
				System.out.println(futureY);
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
		// sprite = new Sprite(new Texture("sprites/AyumiNoAnims.png"));
		
		// Load sprite sheet as a texture
		walkSheet = new Texture(Gdx.files.internal("sprites/Ayumi.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / Settings.SPRITE_COLUMNS,
				walkSheet.getHeight() / Settings.SPRITE_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[Settings.SPRITE_COLUMNS * Settings.SPRITE_ROWS];
		int index = 0;
		for (int i = 0; i < Settings.SPRITE_ROWS; i++) {
			for (int j = 0; j < Settings.SPRITE_COLUMNS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

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
		//sprite.draw(batch);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.end();
	}

	public void dispose() {

	}

}
