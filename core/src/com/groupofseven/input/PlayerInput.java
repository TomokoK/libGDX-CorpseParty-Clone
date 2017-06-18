package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;
import com.groupofseven.screen.SecondFloorScreen;

public class PlayerInput extends InputAdapter {

	private Player me;
			
	//store a reference of Player as this class.player = p
	public PlayerInput(Player p) {
		this.me = p;
		}
	
	//player input method
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			this.me.move(0, 1);
		}
		
		if (keycode == Keys.A){
			this.me.move(-1, 0);
		}
		
		if (keycode == Keys.S) {
			this.me.move(0, -1);
		}
		
		if(keycode == Keys.D) {
			this.me.move(1, 0);
		}
		
		if (keycode == Keys.X) {
		     
		    // if the current screen is Class1ASCreen
		    if (me.getApp().getScreen().getClass() == Class1AScreen.class) {
		    	me.getApp().setScreen(new SecondFloorScreen(me.getApp()));
		    	me.getSprite().setPosition(175, 750);
		    }
		    // if the current screen is SecondFloorScreen
		    else if (me.getApp().getScreen().getClass() == SecondFloorScreen.class) {
		    	me.getApp().setScreen(new Class1AScreen(me.getApp()));
				me.getSprite().setPosition(275, 55);
		    }
		}
		
		return false;
	}
	
}