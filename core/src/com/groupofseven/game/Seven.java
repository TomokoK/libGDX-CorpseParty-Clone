package com.groupofseven.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;

public class Seven extends Game implements ApplicationListener {

	//init objects
	private Screen screen;
	
	private TiledMap map;
	
	SpriteBatch batch;
	
	Sprite sprite;
	
	public Player me;
	
	int[] layers = {0, 1};
		
	@Override
	public void create() {
		//set map for our player 
		map = new TmxMapLoader().load("maps/Class 1A.tmx");

		//debug line
		System.out.println(map.getLayers().get(layers[0]));
		
		//set the player parameters
		me = new Player(this, (TiledMapTileLayer) map.getLayers().get(layers[0]));
		//create a new spritebatch for the sprite
		batch = new SpriteBatch();
		
		//run the loadGFX method in Player class, which loads the sprite into the GPU
		me.loadGFX();
		
		//set sprite position to 275, 55
		////me.getSprite().setPosition(336, 408);
		me.getSprite().setPosition(216, 120);
		
		//set the screen to be Class1AScreen
		screen = new Class1AScreen(this);
		this.setScreen(screen);
	}

	@Override
	public void render() {		
		//clear the screen each frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//call the currently active screen to render
		super.render();
	}
	
}