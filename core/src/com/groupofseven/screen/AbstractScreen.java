package com.groupofseven.screen;

import com.badlogic.gdx.Screen;
import com.groupofseven.game.Seven;

public abstract class AbstractScreen implements Screen {

	protected final Seven app;

	public AbstractScreen(Seven app) {
		/** each class stores a final reference to Game object */
		this.app = app;
	}

	@Override
	public abstract void dispose();

	@Override
	public abstract void hide();

	@Override
	public abstract void pause();

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void resume();

	@Override
	public abstract void show();

	public Seven getApp() {
		return app;
	}
}
