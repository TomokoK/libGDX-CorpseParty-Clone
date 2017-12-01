/*
 * Corpse Party clone in libGDX
 * @AUTHOR
 * 
 * TODO:
 * 	Add all maps
 * 		***NEW ADD MAP PROCESS***
 * 		***ALL EXISTING MAPS EXCEPT FOR CLASS 1A MUST BE CONVERTED***
 * 				1. Create a production environment for new map
 * 				2. Calculate blackspace adjustments and # of tiles
 * 				   with source map PNG
 * 				3. Adjust source PNG, save to production environment
 * 				4. cd into production environment
 * 				5. Run 'node ~/.bin/Image2Map.js 24 24 [name_of_map].png'
 * 				6. Edit resulting TMX file in tiled for collision detection,
 * 				   objects, etc...
 * 				7. Remove original tileset and tmx file from project assets,
 * 				   replace with the new tileset and tmx file
 * 		***END NEW PROCESS***
 * 	Add + improve collision detection
 * 	Add tile layers to each map for better collision detection (e.g. sprite behind podium)
 * 	Add voice acting
 * 	Sprite animations *PRIORITY*
 * 		Need to convert player input from isKeyDown to continuous input
 * 	Fix TextureRender size
 * 		Either through code or resize the source PNG
 * 	Main menu
 * 		Save/Load
 * 		Exit
 * 		Credits
 * 	Dialog boxes
 * 	Make use of the objects layer
 * 		Door collision detection -> change map
 * 		Pick up items, use them in areas
 * 	Change script from edited version -> original CP rip (?)
 * 	Fix camera to be more authentic to the original (Load up a vita save and take a look)
 * 	Tune the volume, fix the no music loop in Class 1A
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
