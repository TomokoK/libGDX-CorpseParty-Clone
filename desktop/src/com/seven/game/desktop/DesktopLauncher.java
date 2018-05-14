/*
 * Corpse Party clone in libGDX
 * @AUTHOR
 * 
 * TODO:
 * 	Add all maps
 * 	    TODO:
 * 	        Boys bathroom
 * 	        Class 2A
 * 	        Class 3A
 * 	        Class 5A
 * 	        First floor
 * 	        Girls bathroom
 * 	        Infirmary
 * 	        Stair case 2-3
 * 	        Stair case 2-3 bathrooms
 * 		***Method***
 * 				1. Create a production environment for new map
 * 				2. Calculate blackspace adjustments and # of tiles
 * 				   with source map PNG. Do this by getting total px length and width,
 * 				   then dividing those numbers by two. Keep shaving down each px amount
 * 			   	   via cropping until both sides px count can be divided by 24 into a whole number.
 * 				3. Save source PNG to the production environment
 * 				4. cd into production environment
 * 				8. Copy Img2Tmx_v3.jar from ~/.bin to the production environment
 * 				9. Run `java -jar Img2Tmx_v3.jar 24 24 genNewSet <map>.png
 * 				10. Open generated .tmx file in tiled, generate new tsx file from newTileSet.png
 * 				11. Import .tsx file as the missing untitled.tsx file
 * 				12. change map properties from XML to base64, embed tileset into map
 * 				13. Edit resulting TMX file in tiled for collision detection,
 * 				    objects, etc...
 * 				14. Remove original tileset and tmx file from project assets,
 * 				    replace with the new tileset and tmx file
 * 		***END NEW PROCESS***
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
