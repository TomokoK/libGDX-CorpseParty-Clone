// if confused, refer to the irc logs for #libgdx on freenode, date of 2/9/17 and your conversation with "bryan`"
package com.seven.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.groupofseven.game.Seven;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Group of Seven";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		
		new LwjglApplication(new Seven(), config);
	}
}
