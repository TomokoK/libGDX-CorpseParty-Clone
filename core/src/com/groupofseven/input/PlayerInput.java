package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;
import com.groupofseven.screen.Class1AScreen;
import com.groupofseven.screen.SecondFloorScreen;

public class PlayerInput extends InputAdapter {

	private Player me;
			
	public PlayerInput(Player p) {
		this.me = p;
		}
	
	//console output is fine, player doesn't move though.
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			this.me.move(0, 1);
			System.out.println("PRESSING W");
		}
		
		if (keycode == Keys.A){
			this.me.move(-1, 0);
			System.out.println("PRESSING A");
		}
		
		if (keycode == Keys.S) {
			this.me.move(0, -1);
			System.out.println("PRESSING S");
		}
		
		if(keycode == Keys.D) {
			this.me.move(1, 0);
			System.out.println("PRESSING D");
		}
		
		if (keycode == Keys.X) {
		     
		    // if the current screen is Class1ASCreen
		    if (me.app.getScreen() == Class1AScreen.class) {
		      me.getApp().setScreen(new SecondFloorScreen(me.getApp()));
		    }
		    else if (app.getScreen() == SecondFloorScreen.class) {
		      me.getApp().setScreen(new Class1AScreen(me.getApp()));
		    }
		}
		
		//Class1AScreen code
//		if (keycode == Keys.X) {
//			mp3music.stop();
//			
//			screen = new SecondFloorScreen(this.app);
//
//			this.app.setScreen(screen);
//		}
		
		//SecondFloorScreen code
//		if (keycode == Keys.X) {
//			//stop the currently playing music (but it doesn't actually for some reason)
//			mp3music.stop();
//			
//			screen = new Class1AScreen(this.app);
//
//			this.app.setScreen(screen);
//		}
		
		return false;
	}
	
}
