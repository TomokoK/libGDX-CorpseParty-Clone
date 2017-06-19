package com.groupofseven.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Renderable;
import com.groupofseven.screen.Class1AScreen;

public class Player implements Renderable {	

	//store references
	private Sprite sprite;
	
	private final Seven app; //final reference to Game object
	
	private final PlayerInput input; //final referencce to player input

	public float lastYChange;

	public float lastXChange;
	
	private TiledMapTileLayer collisionLayer;
		
	//setup constructor 
	public Player(Seven app, TiledMapTileLayer collisionLayer) {
		this.app = app;
		input = new PlayerInput(this);
		this.collisionLayer = collisionLayer;
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
	/*
	 * NOTICE: Collision detection is VERY buggy. Sprite is unable to move around too much and can walk off the map,
	 * causing a nullpointerexception. If you switch screens with X and move around, the game will also crash
	 * with a nullpointerexception when you switch back to the original screen and try to move. Collision detection
	 * is hard, yo.
	 */
	//here be dragons
	public void move(int dx, int dy) {
		float oldX = getX(), oldY = getY();
		float tileWidth = Settings.TILE_SIZE, tileHeight = Settings.TILE_SIZE;
		boolean collideX = false, collideY = false;
		
		//begin movement stuff
		
		sprite.setX(sprite.getX() + (dx * Settings.TILE_SIZE));
		this.lastXChange = (sprite.getX() + (dx * Settings.TILE_SIZE));
	   
		if (this.getApp().getScreen().getClass() == Class1AScreen.class) {

		if (lastXChange < 0) {
			//top left
			collideX = collisionLayer.getCell((int) (lastXChange / tileWidth),
					(int) ((lastYChange + Settings.SPRITE_HEIGHT) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//mid left
			if(!collideX) {
			collideX = collisionLayer.getCell((int) (lastXChange / tileWidth),
					(int) ((lastYChange + Settings.SPRITE_HEIGHT / 2) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			}
			//bottom left
			if(!collideX) {
			collideX = collisionLayer.getCell((int) (lastXChange / tileWidth),
					(int) (lastYChange / tileHeight)).getTile().getProperties().containsKey("blocked");
			}
		} else if(lastXChange > 0) {
			//top right
			collideX = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH) / tileWidth),
					(int) ((lastYChange + Settings.SPRITE_HEIGHT) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//mid right
			if(!collideX) {
				collideX = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH) / tileWidth),
						(int) ((lastYChange + Settings.SPRITE_HEIGHT / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
			//bottom right
			if(!collideX) {
				collideX = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH) / tileWidth),
						(int) (lastYChange / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
		}
		
//		if(!collideX) {
//		sprite.setX(sprite.getX() + (dx * Settings.TILE_SIZE));
//		this.lastXChange = (sprite.getX() + (dx * Settings.TILE_SIZE));
//		}
		
		if(collideX) {
			sprite.setX(oldX);
		}}
		else {
			sprite.setX(sprite.getX() + (dx * Settings.TILE_SIZE));
			this.lastXChange = (sprite.getX() + (dx * Settings.TILE_SIZE));
		}
		
		sprite.setY(sprite.getY() + (dy * Settings.TILE_SIZE));
		this.lastYChange = (sprite.getY() + (dy * Settings.TILE_SIZE));
	   
		if (this.getApp().getScreen().getClass() == Class1AScreen.class) {

		if (lastYChange < 0) {
			//bottom left
			collideY = collisionLayer.getCell((int) (lastXChange / tileWidth),
					(int) (lastYChange / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//bottom mid
			if(!collideY) {
				collideY = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH / 2) / tileWidth),
						(int) (lastYChange / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
			//bottom right
			if(!collideY) {
				collideY = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH) / tileWidth),
						(int) (lastYChange / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
		} else if(lastYChange > 0) {
			//top left
			collideY = collisionLayer.getCell((int) (lastXChange / tileWidth),
					(int) ((lastYChange + Settings.SPRITE_HEIGHT) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//top middle
			if(!collideY) {
				collideY = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH / 2) / tileWidth),
						(int) ((lastYChange + Settings.SPRITE_HEIGHT) / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
			//top right
			if(!collideY) {
				collideY = collisionLayer.getCell((int) ((lastXChange + Settings.SPRITE_WIDTH) / tileWidth),
						(int) ((lastYChange + Settings.SPRITE_HEIGHT) / tileHeight))
						.getTile().getProperties().containsKey("blocked");
			}
			
			if(collideY) {
				sprite.setY(oldY);
			}
			
//			if(!collideY) {
//			sprite.setY(sprite.getY() + (dy * Settings.TILE_SIZE));
//			this.lastYChange = (sprite.getY() + (dy * Settings.TILE_SIZE));
//			}
			
		}}
		else {
			sprite.setY(sprite.getY() + (dy * Settings.TILE_SIZE));
			this.lastYChange = (sprite.getY() + (dy * Settings.TILE_SIZE));
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