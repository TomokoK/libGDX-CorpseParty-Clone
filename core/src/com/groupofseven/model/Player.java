package com.groupofseven.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.screen.Class1AScreen;

public class Player implements Renderable {

    // store references
    private final Seven app; // final reference to Game object

    private final PlayerInput input; // final reference to player input

    private TiledMapTileLayer collisionLayer; // reference to the map layer

    // x and y are used for sprite position
    public float spriteX;
    public float spriteY;

    // booleans handled by the PlayerInput class, used for movement
    public boolean movingUp = false;
    public boolean movingDown = false;
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean movingNowhere = true;

    // used to set the speed of sprite sheet cycling
    public float currentSpeed;

    // used for the delay between movement when holding down a key
    private boolean lastMoveHappened = true;
    private float spriteDelay = 0.25f; // the lower the number, the faster the move speed
    private long startTime; // handles the delay between movement when holding down a key

    // used to set which sprite row we use while moving
    private int direction = 0;

    // set the height and width of the tiles from the settings class
    private int tileWidth = Settings.TILE_SIZE, tileHeight = Settings.TILE_SIZE;

    // Objects here
    private ArrayList<Animation<TextureRegion>> walkAnimation; // declare frame type (texture region)
    private Texture walkSheet;
    private SpriteBatch spriteBatch;

    // this float is used to track elapsed animation time
    private float stateTime;

    // setup constructor
    public Player(Seven app, TiledMapTileLayer collisionLayer) {
        this.app = app;
        input = new PlayerInput(this);
        this.collisionLayer = collisionLayer;
    }

    public Seven getApp() {
        return app;
    }

    public PlayerInput getInput() {
        return input;
    }

    // method to move the sprite
    private void move(int dx, int dy) {

        float futureX; // will be calculated to simulate 1 tile in advance with
        // respect to dx

        // Calculate future x.
        // cases: dx == 0 -> future x is current x (no movement)
        // dx == 1 -> future x is trying to move right one tile
        // dx == -1 -> future x is trying to move left one tile
        if (dx == 1) {
            // case 1 ... simulation of 1 tile movement right
            futureX = spriteX + tileWidth;
            direction = 2;
            // debug line
        } else if (dx == -1) {
            // case: -1 ... simulation of 1 tile movement left
            futureX = spriteX - tileWidth;
            direction = 1;
        } else {
            // case: 0 or invalid dx value -> no movement
            futureX = spriteX;
        }

        float futureY; // will be calculated to simulate 1 tile in advance with
        // respect to dx

        // Calculate future y.
        // cases: dy == 0 -> future y is current y (no movement)
        // dy == 1 -> future y is going to move right one tile
        // dy == -1 -> future y is going to move left one tile
        if (dy == 1) {
            // move 1 tile up
            futureY = spriteY + tileHeight;
            direction = 3;
        } else if (dy == -1) {
            // move 1 time down
            futureY = spriteY - tileHeight;
            direction = 0;
        } else {
            // do not move
            futureY = spriteY;
        }

        Cell cell = collisionLayer.getCell((int) futureX / tileWidth, (int) futureY / tileHeight);
        boolean collideX, collideY;
        // begin movement stuff

        if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
            // case: cell exists and the cell is not a blocked tile
            // post: if case is legal, future x is legal. else x is not legal
            // handle the Class1AMap

            if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
                collideX = false;
                collideY = false;
            } else {
                collideX = true;
                collideY = true;
            }

            if (!collideX || !collideY) {
                currentSpeed = 1f;
                spriteX = (futureX);
                spriteY = (futureY);
            }
        }

        // handle the SecondFloorMap
        // TODO delete this section and do all checks above when all collision detection is added.
        else {
            currentSpeed = 1f;
            spriteX = spriteX + (dx * tileWidth);
            spriteY = spriteY + (dy * tileHeight);
        }

    }

    public float getX() {
        return spriteX;
    }

    public float getY() {
        return spriteY;
    }

    public void loadGFX() {
        // This method is called in Seven.java
        // This loads the sprite into the GPU.

        // Load sprite sheet as a texture
        walkSheet = new Texture("sprites/Seiko.png");
        walkAnimation = new ArrayList<Animation<TextureRegion>>(4);

        // Use the split utility method to create a 2D array of TextureRegions
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / Settings.SPRITE_COLUMNS,
                walkSheet.getHeight() / Settings.SPRITE_ROWS);

        // Cycle through each picture on the selected sprite sheet row
        Animation tmpAnim;
        for (int i = 0; i < Settings.SPRITE_ROWS; i++) {
            tmpAnim = new Animation<TextureRegion>(0.15f, tmp[i]);
            tmpAnim.setPlayMode(Animation.PlayMode.LOOP);
            walkAnimation.add(tmpAnim);
        }

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

    }

    // implementation of update()
    public void update(float delta) {

    }

    private void movement() {
        if (lastMoveHappened) {
            if (movingUp && !movingDown && !movingLeft && !movingRight) {
                lastMoveHappened = false;
                startTime = TimeUtils.millis();
                move(0, 1);

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        lastMoveHappened = true;
                    }
                }, spriteDelay);
            } else if (movingDown && !movingUp && !movingLeft && !movingRight) {
                lastMoveHappened = false;
                startTime = TimeUtils.millis();
                move(0, -1);

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        lastMoveHappened = true;
                    }
                }, spriteDelay);
            } else if (movingLeft && !movingDown && !movingUp && !movingRight) {
                lastMoveHappened = false;
                startTime = TimeUtils.millis();
                move(-1, 0);

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        lastMoveHappened = true;
                    }
                }, spriteDelay);
            } else if (movingRight && !movingUp && !movingDown && !movingLeft) {
                lastMoveHappened = false;
                startTime = TimeUtils.millis();
                move(1, 0);

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        lastMoveHappened = true;
                    }
                }, spriteDelay);
            }
        }
    }

    // implementation of render
    public void render(float delta, SpriteBatch batch) {
        stateTime += (Gdx.graphics.getDeltaTime() * currentSpeed); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        if (currentSpeed > 0.0001f) {
            TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
            batch.draw(currentFrame, (spriteX - 11), spriteY); // Draw current frame at (X, Y)
            // X is offset by -11 as the source sprite sheet isn't a
            // power of two.
        } else {
            TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(3f, false); // Don't
            // draw the sprite mid animation if you are against a blocked tile
            batch.draw(currentFrame, (spriteX - 11), spriteY); // Draw current frame at (X, Y)
            // X is offset by -11 as the source sprite sheet isn't a
            // power of two.
        }

        // call the movement method every frame, allowing for continuous input and interpolation (maybe)
        movement();

    }

    public void dispose() {
        spriteBatch.dispose();
        walkSheet.dispose();
    }

}
