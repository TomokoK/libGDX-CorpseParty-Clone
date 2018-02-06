package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.TimeUtils;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;
import com.groupofseven.screen.SecondFloorScreen;

public class PlayerInput extends InputAdapter {

	private Player me;

	// store a reference of Player as this class.player = p
	public PlayerInput(Player p) {
		this.me = p;
	}

	// player input method
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			System.out.println("W pushed"); // debug line
			this.me.movingNowhere = false;
			this.me.movingUp = true;
//			this.me.startTime = TimeUtils.millis();
//			System.out.println("startTime has been set to: " + this.me.startTime);
		}

		if (keycode == Keys.A) {
			System.out.println("A pushed"); // debug line
			this.me.movingNowhere = false;
			this.me.movingLeft = true;
//			this.me.startTime = TimeUtils.millis();
//			System.out.println("startTime has been set to: " + this.me.startTime);
		}

		if (keycode == Keys.S) {
			System.out.println("S pushed"); // debug line
			this.me.movingNowhere = false;
			this.me.movingDown = true;
//			this.me.startTime = TimeUtils.millis();
//			System.out.println("startTime has been set to: " + this.me.startTime);
		}

		if (keycode == Keys.D) {
			System.out.println("D pushed"); // debug line
			this.me.movingNowhere = false;
			this.me.movingRight = true;
//			this.me.startTime = TimeUtils.millis();
//			System.out.println("startTime has been set to: " + this.me.startTime);
		}

		if (keycode == Keys.X) {
			// Only used for dev purposes, this will be removed when I add door
			// functionality.

			// if the current screen is Class1ASCreen
			if (me.getApp().getScreen().getClass() == Class1AScreen.class) {
				me.getApp().setScreen(new SecondFloorScreen(me.getApp()));
				me.spriteX = 192;
				me.spriteY = 744;
				me.currentSpeed = 0f;
			}
			// if the current screen is SecondFloorScreen
			else if (me.getApp().getScreen().getClass() == SecondFloorScreen.class) {
				me.getApp().setScreen(new Class1AScreen(me.getApp()));
				me.spriteX = 312;
				me.spriteY = 48;
				me.currentSpeed = 0f;
			}
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W) {
			me.currentSpeed = 0f;
			this.me.movingUp = false;
			this.me.movingNowhere = true;
		}

		if (keycode == Keys.A) {
			me.currentSpeed = 0f;
			this.me.movingLeft = false;
			this.me.movingNowhere = true;
		}

		if (keycode == Keys.S) {
			me.currentSpeed = 0f;
			this.me.movingDown = false;
			this.me.movingNowhere = true;
		}

		if (keycode == Keys.D) {
			me.currentSpeed = 0f;
			this.me.movingRight = false;
			this.me.movingNowhere = true;
		}

		return false;

	}

}
