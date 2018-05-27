package com.groupofseven.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.groupofseven.game.Seven;
import com.groupofseven.model.Player;
import com.groupofseven.model.SoundActions;

//extend the AbstractScreen which has a reference of the Seven.java, aka our "Game Class"
public class SecondFloorScreen extends AbstractScreen {

    // init objects
    private Player me;

    private SpriteBatch batch;

    private SoundActions soundAction;

    // update the array with layer numbers when adding more layers
    private int[] FGLayer = {1};
    private int[] BGLayer = {0};

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    // store a reference to seven
    public SecondFloorScreen(Seven startClass) {
        super(startClass);
        batch = startClass.batch;
        me = startClass.me;
        soundAction = startClass.soundAction;
    }

    @Override
    public void dispose() {
        // dispose of the map
        map.dispose();
        // dispose of the renderer
        renderer.dispose();
        // if you don't dispose the music, it will never stop.
        if (me.publicFutureMap.equalsIgnoreCase("Class 3A")) {
            soundAction.disposeAudio("main theme");
        } else if (me.publicFutureMap.equalsIgnoreCase("Class 5A")) {
            soundAction.disposeAudio("main theme");
        }
        // dispose the sprite
        batch.dispose();
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
//		camera.viewportWidth = width;
//		camera.viewportHeight = height;
//      camera.viewportWidth = 640;
//      camera.viewportHeight = 480;
//		camera.update();
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        // create a new spritebatch for the sprite
        batch = new SpriteBatch();
        // set our map
        map = new TmxMapLoader().load("maps/SecondFloor.tmx");
        // render the map
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.viewportWidth = 640;
        camera.viewportHeight = 480;
        camera.zoom = 0.75f;
        // set input processor
        Gdx.input.setInputProcessor(me.getInput());
    }

}
