package com.groupofseven.game;

public class Settings {
	//size of each map tile
	public static int TILE_SIZE = 24;
	
	//stats on the sprite sheets
	public static int SPRITE_ROWS = 4;
	public static int SPRITE_COLUMNS = 4;
	public static int SPRITE_PX_WIDTH = 128;
	public static int SPRITE_PX_HEIGHT = 192;
	
	//stats on the rendered sprite size
	public static int SPRITE_HEIGHT = SPRITE_PX_HEIGHT / SPRITE_COLUMNS;
	public static int SPRITE_WIDTH = SPRITE_PX_WIDTH / SPRITE_ROWS;
	
}
