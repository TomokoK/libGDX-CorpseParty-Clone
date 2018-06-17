package com.groupofseven.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.TimeUtils;
import com.groupofseven.model.Player;
import com.groupofseven.model.SoundActions;
import com.groupofseven.screen.Class1AScreen;

import java.util.Random;

public class Seven extends Game implements ApplicationListener {
    // init objects
    public TiledMap map;
    public SpriteBatch batch;
    public Player me;
    public SoundActions soundAction;

    // contains which body has the key
    public String bodyWithKey;

    // contains start time
    public long startTime;

    @Override
    public void create() {
        // log time elapsed
        long startTime = System.nanoTime();
        // set map for our player
        map = new TmxMapLoader().load("maps/Class1A.tmx");
        // start the sound controller object
        soundAction = new SoundActions();
        // set the player parameters
        me = new Player(this, (TiledMapTileLayer) map.getLayers().get(0));
        // run the loadGFX method in Player class, which loads the sprite into the GPU
        me.loadGFX();
        // set sprite position to a # divisible by the tile size
        me.spriteX = 168;
        me.spriteY = 96;
        // place the key randomly
        Random randomInt = new Random();
        int bodySelector = randomInt.nextInt(5);
        switch (bodySelector) {
            case 0:
                //bodyA
                bodyWithKey = "bodyA";
                break;
            case 1:
                //bodyB
                bodyWithKey = "bodyB";
                break;
            case 2:
                //bodyC
                bodyWithKey = "bodyC";
                break;
            case 3:
                //bodyD
                bodyWithKey = "bodyD";
                break;
            case 4:
                //bodyE
                bodyWithKey = "bodyE";
                break;
            default:
                Gdx.app.exit();
                System.exit(0);
                break;
        }
        // set the screen to be Class1AScreen
        Screen screen = new Class1AScreen(this);
        this.setScreen(screen);
    }

    @Override
    public void render() {
        // clear the screen each frame
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // call the currently active screen to render
        super.render();
    }
}
