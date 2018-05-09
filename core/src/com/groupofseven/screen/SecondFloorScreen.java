package com.groupofseven.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.groupofseven.game.Seven;
import com.groupofseven.model.Player;

//extend the AbstractScreen which has a reference of the Seven.java, aka our "Game Class"
public class SecondFloorScreen extends AbstractScreen {

	// init objects
	private Player me;

	private SpriteBatch batch = new SpriteBatch();

	// update the array with layer numbers when adding more layers
	private int[] FGLayer = {1};
	private int[] BGLayer = {0};

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	// define music for screen
	private Music mp3music = Gdx.audio.newMusic(Gdx.files.internal("music/01 Puzzled.mp3"));

	// store a reference to seven
	public SecondFloorScreen(Seven app) {
		super(app);
		me = app.me;
	}

	@Override
	public void dispose() {
		// dispose of the map
		map.dispose();
		// dispose of the renderer
		renderer.dispose();
		// if you don't dispose the music, it will never stop.
		// this fixes the multiple music bug I was struggling with.
		mp3music.dispose();
	}

	@Override
	public void hide() {
		// call the disposal method
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		// start camera for the second floor map
		renderer.setView(camera);
		renderer.render(BGLayer);
		// render sprite
		batch.begin();
		// allow the camera matrix to sync with the sprite matrix
		batch.setProjectionMatrix(camera.combined);
		// render the batch with delta time
		me.render(delta, batch);
		// end batch
		batch.end();
        // render foreground
        renderer.render(FGLayer);
        // set camera position to follow player coords
        camera.position.x = me.getX();
        camera.position.y = me.getY();
        // update the camera each render loop
        camera.update();
	}

	@Override
	public void resize(int width, int height) {
		// set camera options here
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		// set our map
		map = new TmxMapLoader().load("maps/Second floor.tmx");
		// render the map
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		// play music
		mp3music.setLooping(true);
		mp3music.setVolume(0.5f);
		mp3music.play();
		// set input processor
		Gdx.input.setInputProcessor(me.getInput());
	}

}
