package com.groupofseven.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.model.Player;

public class SecondFloorScreen extends AbstractScreen {
	
	private Player player;
	
	private PlayerInput input;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Screen screen;
	
	public 	Music mp3music = Gdx.audio.newMusic(Gdx.files.internal("music/01 Puzzled.mp3"));

	
	public SecondFloorScreen(Seven app) {
		super(app);
		
		player = new Player(0, 0);
		
		input = new PlayerInput(player);
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
		
		this.app.playerObject.render(delta, null);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {		
		map = new TmxMapLoader().load("maps/Second floor.tmx");
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
				
		mp3music.play();
		
		InputProcessor inputProcessorOne = new PlayerInput(player);
		//does this line actually work? (see below)
		InputProcessor inputProcessorTwo = new SecondFloorScreen(app);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		//NOTICE: using the above variables (inputProcessorTwo, inputProcessorOne) in place of (this) and (input)
		//below will mess about with the program (e.g. mp3music.stop() never triggers, just keeps recreating
		//mp3music.play().)
		inputMultiplexer.addProcessor(inputProcessorOne);
		inputMultiplexer.addProcessor(inputProcessorTwo);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.X) {
			mp3music.stop();
			
			screen = new Class1AScreen(this.app);

			this.app.setScreen(screen);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
