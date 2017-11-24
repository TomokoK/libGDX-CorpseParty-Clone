package com.groupofseven.game;

public class Settings {
	// size of each map tile
	public static final int TILE_SIZE = 24;

	// define stats on the sprite sheets
	public static final int SPRITE_ROWS = 4;
	public static final int SPRITE_COLUMNS = 4;
	public static final int SPRITE_PX_WIDTH = 128;
	public static final int SPRITE_PX_HEIGHT = 192;

	// define stats on the rendered sprite size
	public static final int SPRITE_HEIGHT = SPRITE_PX_HEIGHT / SPRITE_COLUMNS;
	public static final int SPRITE_WIDTH = SPRITE_PX_WIDTH / SPRITE_ROWS;

}
