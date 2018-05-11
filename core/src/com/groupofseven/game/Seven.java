package com.groupofseven.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;

public class Seven extends Game implements ApplicationListener {
    // init objects
    private Screen screen;
    private TiledMap map;
    public SpriteBatch batch;
    public Player me;
    public BitmapFont menuFont;
    private int[] layers = {0, 1};

    @Override
    public void create() {
        // font creation
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/FoxScriptNormal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        menuFont = generator.generateFont(parameter);
        generator.dispose();
        // set map for our player
        map = new TmxMapLoader().load("maps/Class1A.tmx");
        // debug line
        System.out.println(map.getLayers().get(layers[0]));
        System.out.println(map.getLayers().get(layers[1])); // "foreground" layer rendered above sprite
        // set the player parameters
        me = new Player(this, (TiledMapTileLayer) map.getLayers().get(layers[0]));
        // create a new spritebatch for the sprite
        batch = new SpriteBatch();
        // run the loadGFX method in Player class, which loads the sprite into the GPU
        me.loadGFX();
        // set sprite position to a # divisible by the tile size
        me.spriteX = 168;
        me.spriteY = 96;
        // set the screen to be Class1AScreen
        screen = new Class1AScreen(this);
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
