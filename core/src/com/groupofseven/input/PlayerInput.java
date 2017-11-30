package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;
import com.groupofseven.screen.SecondFloorScreen;

public class PlayerInput extends InputAdapter {

	private Player me;
	
	//public static float moveSpeed = 0f;

	// store a reference of Player as this class.player = p
	public PlayerInput(Player p) {
		this.me = p;
	}

	// player input method
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			this.me.move(0, 1);
			//moveSpeed = 0.25f;
		}

		if (keycode == Keys.A) {
			this.me.move(-1, 0);
			//moveSpeed = 0.25f;
		}

		if (keycode == Keys.S) {
			this.me.move(0, -1);
			//moveSpeed = 0.25f;
		}

		if (keycode == Keys.D) {
			this.me.move(1, 0);
			//moveSpeed = 0.25f;
		}

		if (keycode == Keys.X) {
			// Only used for dev purposes, this will be removed when I add door functionality.

			// if the current screen is Class1ASCreen
			if (me.getApp().getScreen().getClass() == Class1AScreen.class) {
				me.getApp().setScreen(new SecondFloorScreen(me.getApp()));
				me.x = 192;
				me.y = 744;
			}
			// if the current screen is SecondFloorScreen
			else if (me.getApp().getScreen().getClass() == SecondFloorScreen.class) {
				me.getApp().setScreen(new Class1AScreen(me.getApp()));
				me.x = 312;
				me.y = 48;
			}
		}

		//moveSpeed = 0f;
		return false;
	}

}
