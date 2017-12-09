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
	public boolean none = false;

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
		}

		if (keycode == Keys.A) {
			System.out.println("A pushed"); // debug line
			// this.me.move(-1, 0);
			movingLeft = true;
		}

		if (keycode == Keys.S) {
			System.out.println("S pushed"); // debug line
			// this.me.move(0, -1);
			movingDown = true;
		}

		if (keycode == Keys.D) {
			System.out.println("D pushed"); // debug line
			// this.me.move(1, 0);
			movingRight = true;
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
		}

		if (keycode == Keys.A) {
			me.currentSpeed = 0f;
			movingLeft = false;
		}

		if (keycode == Keys.S) {
			me.currentSpeed = 0f;
			movingDown = false;
		}

		if (keycode == Keys.D) {
			me.currentSpeed = 0f;
			movingRight = false;
		}

		return false;

	}

	public void movement() {
		while (true) { // uncommenting this causes java to hang
			if (none) {
				//don't do things
				break;
			}
			if (movingUp) {
				// while (true) { //uncommenting causes java to hang
				System.out.println("W pushed in movement()"); // debug line
				this.me.move(0, 1);
				// Thread.sleep(500); // this will cause the entire program to stop since it's
				// not multi-threaded.
				// }
			}
			if (movingDown) {
				// while (true) { //uncommenting causes java to hang
				System.out.println("S pushed in movement()"); // debug line
				this.me.move(0, -1);
				// Thread.sleep(500);
				// }
			}
			if (movingLeft) {
				// while (true) { //uncommenting causes java to hang
				System.out.println("A pushed in movement()"); // debug line
				this.me.move(-1, 0);
				// Thread.sleep(500);
				// }
			}
			if (movingLeft == true) {
				// while (true) { //uncommenting causes java to hang
				System.out.println("D pushed in movement()"); // debug line
				this.me.move(1, 0);
				// Thread.sleep(500);
				// }
			}
		}
		return;
	}

}
