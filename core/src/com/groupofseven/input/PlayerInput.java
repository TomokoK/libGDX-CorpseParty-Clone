package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;
import com.groupofseven.screen.SecondFloorScreen;

public class PlayerInput extends InputAdapter {

	private Player me;

	public boolean movingUp = false;
	public boolean movingDown = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;

	// store a reference of Player as this class.player = p
	public PlayerInput(Player p) {
		this.me = p;
	}

	// player input method
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			System.out.println("W pushed"); // debug line
			// this.me.move(0, 1);
			movingUp = true;
			movement(movingUp);
		}

		if (keycode == Keys.A) {
			System.out.println("A pushed"); // debug line
			// this.me.move(-1, 0);
			movingLeft = true;
			movement(movingLeft);
		}

		if (keycode == Keys.S) {
			System.out.println("S pushed"); // debug line
			// this.me.move(0, -1);
			movingDown = true;
			movement(movingDown);
		}

		if (keycode == Keys.D) {
			System.out.println("D pushed"); // debug line
			// this.me.move(1, 0);
			movingRight = true;
			movement(movingRight);
		}

		if (keycode == Keys.X) {
			// Only used for dev purposes, this will be removed when I add door
			// functionality.

			// if the current screen is Class1ASCreen
			if (me.getApp().getScreen().getClass() == Class1AScreen.class) {
				me.getApp().setScreen(new SecondFloorScreen(me.getApp()));
				me.x = 192;
				me.y = 744;
				me.currentSpeed = 0f;
			}
			// if the current screen is SecondFloorScreen
			else if (me.getApp().getScreen().getClass() == SecondFloorScreen.class) {
				me.getApp().setScreen(new Class1AScreen(me.getApp()));
				me.x = 312;
				me.y = 48;
				me.currentSpeed = 0f;
			}
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W) {
			me.currentSpeed = 0f;
			movingUp = false;
			movement(movingUp);
		}

		if (keycode == Keys.A) {
			me.currentSpeed = 0f;
			movingLeft = false;
			movement(movingLeft);
		}

		if (keycode == Keys.S) {
			me.currentSpeed = 0f;
			movingDown = false;
			movement(movingDown);
		}

		if (keycode == Keys.D) {
			me.currentSpeed = 0f;
			movingRight = false;
			movement(movingRight);
		}

		return false;

	}

	public void movement(boolean dir) {
		//while (true) { //uncommenting this causes java to hang
			if (dir = movingUp) {
				System.out.println("W pushed in movement()"); // debug line
				this.me.move(0, 1);
			}
			if (dir = movingDown) {
				System.out.println("S pushed in movement()"); // debug line
				this.me.move(0, -1);
			}
			if (dir = movingLeft) {
				System.out.println("A pushed in movement()"); // debug line
				this.me.move(-1, 0);
			}
			if (dir = movingRight) {
				System.out.println("D pushed in movement()"); // debug line
				this.me.move(1, 0);
			}
		//}
			return;
	}

}
