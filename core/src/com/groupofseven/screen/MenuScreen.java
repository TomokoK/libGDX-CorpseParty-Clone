package com.groupofseven.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupofseven.game.Seven;
import com.groupofseven.model.Player;

public class MenuScreen extends AbstractScreen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Player me;

    public MenuScreen(Seven startClass) {
        super(startClass);
        batch = startClass.batch;
        font = startClass.menuFont;
        me = startClass.me;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Hello World", 250, 250);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(me.getInput());
    }
}
