package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;

public class PlayerInput extends InputAdapter {

	private Player player;
	
	public PlayerInput(Player p) {
		this.player = p;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			player.move(0, 1);
		}
		
		if (keycode == Keys.A){
			player.move(-1, 0);
		}
		
		if (keycode == Keys.S) {
			player.move(0, -1);
		}
		
		if(keycode == Keys.D) {
			player.move(1, 0);
		}
		return false;
	}
	
}
