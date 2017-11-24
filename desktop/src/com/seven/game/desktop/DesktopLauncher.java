/*
 * Corpse Party clone in libGDX
 * @AUTHOR
 * 
 * TODO:
 * 	Add all maps
 * 		Test new img2tmx script for better col. detec. ?
 * 	Add + improve collision detection
 * 	Add voice acting
 * 	Sprite animations
 * 	Main menu
 * 		Save/Load
 * 		Exit
 * 		Credits
 * 	Dialog boxes
 * 	Make use of the objects layer
 * 		Door collision detection -> change map
 * 		Pick up items, use them in areas
 * 	Change script from edited version -> original CP rip (?)
 * 
 */
package com.seven.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.groupofseven.game.Seven;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Corpse Party";
		config.width = 640;
		config.height = 480;
		config.vSyncEnabled = true;

		new LwjglApplication(new Seven(), config);
	}
}