package com.groupofseven.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;

public class Seven extends Game implements ApplicationListener {

	private Screen screen;
	
	public Player playerObject = new Player();
	
	@Override
	public void create() {
		//set the screen to be Class1AScreen
		screen = new Class1AScreen(this);
		this.setScreen(screen);
		
		playerObject.loadGFX();
	}
	
	@Override
	public void render() {
		//clear the screen each frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		//reference player
		float delta = Gdx.graphics.getDeltaTime();
		playerObject.render(delta, null);
		
		//calls the render method of the active screen
		super.render();
	}
	
}