package com.groupofseven.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;

public class Seven extends Game implements ApplicationListener {

	private Screen screen;
	
	SpriteBatch batch;
	
	Sprite sprite;
	
	//init player at (0,0)
	public Player me = new Player(this);
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		//run the loadGFX method in Player class, which loads the sprite into the GPU
		me.loadGFX();
		
		me.getSprite().setPosition(0,0);
		
		//set the screen to be Class1AScreen
		screen = new Class1AScreen(this);
		this.setScreen(screen);
	}

	@Override
	public void render(float delta) {
		//define delta
		float delta = Gdx.graphics.getDeltaTime();
		
		//clear the screen each frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}