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

// extend the AbstractScreen which has a reference of the Seven.java, aka our "Game Class"
public class Class1AScreen extends AbstractScreen {

    // init objects
    private Player me;

    private SpriteBatch batch;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private static int firstLinePlayed = 0;

    // define music for screen
    private Music mp3MainTheme = Gdx.audio.newMusic(Gdx.files.internal("music/11 Chapter 1 Main Theme.mp3"));
    // define intro voice line
    private Music voice = Gdx.audio.newMusic(Gdx.files.internal("VoiceActing/f_4.mp3"));

    // store a reference to Seven
    public Class1AScreen(Seven startClass) {
        super(startClass);
        batch = startClass.batch;
        me = startClass.me;
    }

    @Override
    public void dispose() {
        // dispose of the map
        map.dispose();
        // dispose of the renderer (the OrthogonalTiledMapRenderer)
        renderer.dispose();
        // if you don't dispose the music, it will never stop.
        // this fixes the multiple music bug I was struggling with.
        mp3MainTheme.dispose();
        voice.dispose();
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
        // start camera for the classroom map
        renderer.setView(camera);
        renderer.render();
        // set camera position to follow player coords
        camera.position.x = me.getX();
        camera.position.y = me.getY();
        // update the camera each render loop
        camera.update();
        // render sprite
        batch.begin();
        // allow the camera matrix to sync with the sprite matrix
        batch.setProjectionMatrix(camera.combined);
        // render the batch with delta time
        me.render(delta, batch);
        // end batch
        batch.end();
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
        map = new TmxMapLoader().load("maps/Class 1A.tmx");
        // render map
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        // sound options
        mp3MainTheme.setLooping(true);
        mp3MainTheme.setVolume(0.5f);
        voice.setVolume(0.5f);
        /*
         * First, we play the voice clip. Then, we check to see when the voice clip has
         * finished playing. Once the voice clip is playing, play the background music.
         *
         * If the voice line has already been played, skip right to the main theme.
         */
        if (firstLinePlayed == 0) {
            voice.play();
            firstLinePlayed++;
            voice.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    mp3MainTheme.play();
                    // experimental wait for voice line
                    // Set our input processor
                    Gdx.input.setInputProcessor(me.getInput());
                }
            });
        } else {
            mp3MainTheme.play();
            // experimental wait for voice line
            // Set our input processor
            Gdx.input.setInputProcessor(me.getInput());
        }
    }
}