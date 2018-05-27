/*
 * Corpse Party clone in libGDX
 * @AUTHOR
 * 
 * TODO:
 *  Add doors to the correct area
 * 	Remove objects (e.g. desks, buckets) from main map and add them as an object layer (look into this)
 * 	Add/improve collision properties to tiled maps
 * 	Add tile layers to each map for better collision detection (e.g. sprite behind podium)
 * 	Add more voice acting lines
 * 	Main menu
 * 	    Fullscreen/Screen Resolution
 * 		Save/Load
 * 		Exit
 * 		Credits
 * 	Dialog boxes
 * 	Make use of the objects layer (look into this)
 * 		Pick up items, use them in areas
 * 	Change script from edited version -> original CP rip (?)
 * 	Add vignette effect shaders as a scene2d overlay
 * 	Tune the volume via menu screen
 * 	Clean up all debug lines when they are no longer needed
 * 	Fix/Implement cross screen music
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
		config.resizable = false;
		//config.foregroundFPS = 60;

		new LwjglApplication(new Seven(), config);
	}
}
