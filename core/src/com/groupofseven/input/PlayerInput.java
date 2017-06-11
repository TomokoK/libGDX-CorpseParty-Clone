package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;

public class PlayerInput extends InputAdapter {

	private Player player;
		
	public PlayerInput(Player p) {
		this.player = p;
		}
	
	//console output is fine, player doesn't move though.
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			this.player.move(0, 1);
			System.out.println("PRESSING W");
		}
		
		if (keycode == Keys.A){
			this.player.move(-1, 0);
			System.out.println("PRESSING A");
		}
		
		if (keycode == Keys.S) {
			this.player.move(0, -1);
			System.out.println("PRESSING S");
		}
		
		if(keycode == Keys.D) {
			this.player.move(1, 0);
			System.out.println("PRESSING D");
		}
		return false;
	}
	
}
