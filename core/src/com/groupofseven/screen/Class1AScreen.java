package com.groupofseven.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.groupofseven.game.Seven;
import com.groupofseven.model.Player;

// extend the AbstractScreen which has a reference of the Seven.java, aka our "Game Class"
public class Class1AScreen extends AbstractScreen {

	private Player me;
	
	SpriteBatch batch = new SpriteBatch();
			
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	public Music mp3music = Gdx.audio.newMusic(Gdx.files.internal("music/11 Chapter 1 Main Theme.mp3"));
		
	public Class1AScreen(Seven app) {
		super(app);
		
		me = app.me;
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		//if you don't dispose the music, it will never stop.
		//this fixes the multiple music bug I was struggling with.
		mp3music.dispose();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//start camera for the classroom map
		renderer.setView(camera);
		renderer.render();
		
		MapProperties prop = map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		int mapPixelWidth = mapWidth * tilePixelWidth;
		int mapPixelHeight = mapHeight * tilePixelHeight;
		
		// These values likely need to be scaled according to your world coordinates.
		// The left boundary of the map (x)
		int mapLeft = 0;
		// The right boundary of the map (x + width)
		int mapRight = 0 + mapWidth;
		// The bottom boundary of the map (y)
		int mapBottom = 0;
		// The top boundary of the map (y + height)
		int mapTop = 0 + mapHeight;
		// The camera dimensions, halved
		float cameraHalfWidth = camera.viewportWidth * .5f;
		float cameraHalfHeight = camera.viewportHeight * .5f;

		// Move camera after player as normal

		float cameraLeft = camera.position.x - cameraHalfWidth;
		float cameraRight = camera.position.x + cameraHalfWidth;
		float cameraBottom = camera.position.y - cameraHalfHeight;
		float cameraTop = camera.position.y + cameraHalfHeight;

		// Horizontal axis
		if(mapWidth < camera.viewportWidth)
		{
			camera.position.x = mapRight / 2;
		}
		else if(cameraLeft <= mapLeft)
		{
			camera.position.x = mapLeft + cameraHalfWidth;
		}
		else if(cameraRight >= mapRight)
		{
			camera.position.x = mapRight - cameraHalfWidth;
		}

		// Vertical axis
		if(mapWidth < camera.viewportHeight)
		{
			camera.position.y = mapTop / 2;
		}
		else if(cameraBottom <= mapBottom)
		{
			camera.position.y = mapBottom + cameraHalfHeight;
		}
		else if(cameraTop >= mapTop)
		{
		    camera.position.y = mapTop - cameraHalfHeight;
		}
		
		//camera.position.x = me.getX();
		//camera.position.y = me.getY();
		camera.update();
		
		//render sprite
		batch.begin();
		
		me.render(delta, batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		//set camera options here
		camera.viewportWidth = 480;
		camera.viewportHeight = 720;
//		camera.position.x = me.getX();
//		camera.position.y = me.getY();
//		camera.update();
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {		
		//set our map
		map = new TmxMapLoader().load("maps/Class 1A.tmx");
		
		//render map
		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();
				
		//play music
		mp3music.play();
		
		//set our input processor
		Gdx.input.setInputProcessor(me.getInput());
	}

}