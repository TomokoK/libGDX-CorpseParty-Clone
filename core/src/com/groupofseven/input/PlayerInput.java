package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;

public class PlayerInput extends InputAdapter {

	private Player player;
		
	public PlayerInput(Player p) {
		this.player = p;
		
		//Player player = new Player(0, 0);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		//player = new Player(0, 0);
		//pressing W A S or D crashes the program with a nullpointerexception
		if (keycode == Keys.W) {
			// *** UNCOMMENTING THIS WILL GIVE A NULLPOINTEREXCEPTION ***
			this.player.move(0, 1);
			System.out.println("PRESSING W");
		}
		
		if (keycode == Keys.A){
			// *** UNCOMMENTING THIS WILL GIVE A NULLPOINTEREXCEPTION ***
			this.player.move(-1, 0);
			System.out.println("PRESSING A");
		}
		
		if (keycode == Keys.S) {
			// *** UNCOMMENTING THIS WILL GIVE A NULLPOINTEREXCEPTION ***
			this.player.move(0, -1);
			System.out.println("PRESSING S");
		}
		
		if(keycode == Keys.D) {
			// *** UNCOMMENTING THIS WILL GIVE A NULLPOINTEREXCEPTION ***
			this.player.move(1, 0);
			System.out.println("PRESSING D");
		}
		return false;
	}
	
}
