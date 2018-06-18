package com.groupofseven.model;

import java.io.*;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.groupofseven.game.Settings;
import com.groupofseven.game.Seven;
import com.groupofseven.input.PlayerInput;
import com.groupofseven.screen.*;

public class Player implements Renderable {

    // store references
    private final Seven app; // final reference to Game object

    private final PlayerInput input; // final reference to player input

    private TiledMapTileLayer collisionLayer; // reference to the map layer

    // sprite attributes
    public float spriteX;
    public float spriteY;
    private float futureX;
    private float futureY;
    private boolean yAxisMovement, xAxisMovement;
    private boolean collide;
    public boolean hasKey;

    // booleans handled by the PlayerInput class, used for movement
    public boolean movingUp = false;
    public boolean movingDown = false;
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean movingNowhere = true;

    // used to set the speed of sprite sheet cycling
    private float currentSpeed;

    // used for the delay between movement when holding down a key
    private boolean lastMoveHappened = true;
    private float spriteDelay = 0.25f; // the lower the number, the faster the move speed
    private float elapsedTime = 0f;

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

    // sound effect for key pickup
    Sound pickupEffect = Gdx.audio.newSound(Gdx.files.internal("SFX/COP-SE_k017.wav"));

    // get future map
    public String publicFutureMap;
    private TiledMap tempMap;

    // setup constructor
    public Player(Seven app, TiledMapTileLayer collisionLayer) {
        this.app = app;
        input = new PlayerInput(this);
        this.collisionLayer = collisionLayer;
        tempMap = app.map;
    }

    private Seven getApp() {
        return app;
    }

    public PlayerInput getInput() {
        return input;
    }

    private void move(int dx, int dy) {
        // will be calculated to simulate 1 tile in advance with
        // respect to dx

        // Calculate future x.
        // cases: dx == 0 -> future x is current x (no movement)
        // dx == 1 -> future x is trying to move right one tile
        // dx == -1 -> future x is trying to move left one tile
        if (dx == 1) {
            // case 1 ... simulation of 1 tile movement right
            futureX = spriteX + tileWidth;
            xAxisMovement = true;
            direction = 2;
        } else if (dx == -1) {
            // case: -1 ... simulation of 1 tile movement left
            futureX = spriteX - tileWidth;
            xAxisMovement = true;
            direction = 1;
        } else {
            // case: 0 or invalid dx value -> no movement
            futureX = spriteX;
            xAxisMovement = false;
        }

        // will be calculated to simulate 1 tile in advance with
        // respect to dx

        // Calculate future y.
        // cases: dy == 0 -> future y is current y (no movement)
        // dy == 1 -> future y is going to move up one tile
        // dy == -1 -> future y is going to move down one tile
        if (dy == 1) {
            // move 1 tile up
            futureY = spriteY + tileHeight;
            yAxisMovement = true;
            direction = 3;
        } else if (dy == -1) {
            // move 1 time down
            futureY = spriteY - tileHeight;
            yAxisMovement = true;
            direction = 0;
        } else {
            // do not move
            futureY = spriteY;
            yAxisMovement = false;
        }

        Cell cell = collisionLayer.getCell((int) futureX / tileWidth, (int) futureY / tileHeight);

        // begin movement stuff
        // case: cell exists and the cell is not a blocked tile
        // post: if case is legal, future x is legal. else x is not legal

        if (cell != null && !cell.getTile().getProperties().containsKey("blocked")) {
            collide = false;
        } else {
            collide = true;
        }

        if (!collide) {
            currentSpeed = 1f;
            if (futureX != 0f || futureY != 0f) {
                interpolateSprite();
            }
        } else {
            lastMoveHappened = true;
        }
    }

    public void checkForAction() {
        //check if the tile you are facing is an action tile
        int facingTileY = 0, facingTileX = 0;
        if (direction == 0) {
            facingTileY = (((int) spriteY - 24) / tileHeight);
            facingTileX = ((int) spriteX / tileWidth);
        } else if (direction == 1) {
            facingTileY = ((int) spriteY / tileHeight);
            facingTileX = (((int) spriteX - 24) / tileHeight);
        } else if (direction == 2) {
            facingTileY = ((int) spriteY / tileHeight);
            facingTileX = (((int) spriteX + 24) / tileWidth);
        } else if (direction == 3) {
            facingTileY = (((int) spriteY + 24) / tileHeight);
            facingTileX = ((int) spriteX / tileWidth);
        }

        Cell facingCell = collisionLayer.getCell(facingTileX, facingTileY);

        if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
            if (facingCell.getTile().getProperties().containsKey("looseBoard")) {
                System.out.println("loose board");
            } else if (facingCell.getTile().getProperties().containsKey("poster")) {
                System.out.println("poster");
            } else if (facingCell.getTile().getProperties().containsKey("cabinet")) {
                System.out.println("cabinet");
            } else if (facingCell.getTile().getProperties().containsKey("bucket")) {
                System.out.println("bucket");
            } else {
                System.out.println("null");
            }
        } else if (this.getApp().getScreen().getClass() == SecondFloorScreen.class) {
            if (facingCell.getTile().getProperties().containsKey("infirmaryDoor")) {
                changeSpriteLocation("Infirmary", 96, 144);
            } else if (facingCell.getTile().getProperties().containsKey("scienceLabDoor")) {
                System.out.println("Science Lab Door (locked)");
            } else if (facingCell.getTile().getProperties().containsKey("bucket")) {
                System.out.println("bucket");
            } else {
                System.out.println("null");
            }
        } else if (this.getApp().getScreen().getClass() == Class2AScreen.class) {
            if (facingCell.getTile().getProperties().containsKey("SecondFloorDoor")) {
                changeSpriteLocation("Second Floor", 648, 1512);
                direction = 0;
            } else if (facingCell.getTile().getProperties().containsKey("bodyA") && app.bodyWithKey.equals("bodyA")) {
                hasKey = true;
                pickupEffect.play(1.0f);
                System.out.println("key obtained. get out.");
            }
        } else if (this.getApp().getScreen().getClass() == Class3AScreen.class) {
            if (facingCell.getTile().getProperties().containsKey("bodyB") && app.bodyWithKey.equals("bodyB")) {
                hasKey = true;
                pickupEffect.play(1.0f);
                System.out.println("key obtained. get out.");
            }
        } else if (this.getApp().getScreen().getClass() == FirstFloorScreen.class) {
            if (facingCell.getTile().getProperties().containsKey("exitDoor")) {
                if (hasKey) {
                    //log time it took to escape
                    long totalTime = ((System.nanoTime() - app.startTime) / 1000);
                    //print how long it took your run
                    System.out.println("you escaped in: " + totalTime);
                    // I/O for recording/reading times
                    // attempt to read previous time if it exists
                    try {
                        // get previous time
                        File inputFile = new File("time");
                        BufferedReader parser = new BufferedReader(new FileReader(inputFile));
                        String parseLine = parser.readLine();
                        System.out.println("Your previous best time was: " + parseLine);
                        parser.close();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e);
                    }
                    // attempt to print time to `time`
                    try {
                        File outputFile = new File("time");
                        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
                        fileWriter.write(Long.toString(totalTime));
                        fileWriter.close();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e);
                    }
                    //exit
                    Gdx.app.exit();
                } else {
                    System.out.println("locked");
                }
            } else if (facingCell.getTile().getProperties().containsKey("bodyC") && app.bodyWithKey.equals("bodyC")) {
                hasKey = true;
                pickupEffect.play(1.0f);
                System.out.println("key obtained. get out.");
            } else if (facingCell.getTile().getProperties().containsKey("bodyD") && app.bodyWithKey.equals("bodyD")) {
                hasKey = true;
                pickupEffect.play(1.0f);
                System.out.println("key obtained. get out.");
            } else if (facingCell.getTile().getProperties().containsKey("bodyE") && app.bodyWithKey.equals("bodyE")) {
                hasKey = true;
                pickupEffect.play(1.0f);
                System.out.println("key obtained. get out.");
            }
        }
    }

    private void checkForDoor() {
        // Check if on a door, if so, teleport to respective room
        if (this.getApp().getScreen().getClass() == Class1AScreen.class) {
            if (spriteX == 360 && spriteY == 48) {
                changeSpriteLocation("Second floor", 168, 744);
            } else if (spriteX == 360 && spriteY == 360) {
                changeSpriteLocation("Second floor", 144, 1176);
            }
        } else if (this.getApp().getScreen().getClass() == SecondFloorScreen.class) {
            if (spriteX == 120 && spriteY == 744) {
                changeSpriteLocation("Class 1A", 336, 48);
            } else if (spriteX == 120 && spriteY == 1176) {
                changeSpriteLocation("Class 1A", 336, 360);
            } else if (spriteX == 408 && spriteY == 1536) {
                changeSpriteLocation("Class 2A", 360, 48);
            } else if (spriteX == 1224 && spriteY == 1560) {
                changeSpriteLocation("Class 3A", 384, 384);
            } else if ((spriteX == 240 && spriteY == 1560) || (spriteX == 264 && spriteY == 1560)) {
                changeSpriteLocation("StairCase2-3", 288, 264);
            } else if ((spriteX == 1656 || spriteX == 1632) && spriteY == 1680) {
                changeSpriteLocation("StairCase2-3Bathrooms", 168, 240);
                direction = 0;
            } else if ((spriteX == 264 || spriteX == 240) && spriteY == 432) {
                changeSpriteLocation("SecondFloorStairCase", 72, 264);
            } else if (spriteX == 648 && spriteY == 1536) {
                changeSpriteLocation("Class 2A", 360, 360);
                direction = 1;
            } else if ((spriteX == 1632 || spriteX == 1656) && spriteY == 312) {
                changeSpriteLocation("SecondFloorStairCase2", 72, 264);
            }
        } else if (this.getApp().getScreen().getClass() == SecondFloorStairCaseScreen.class) {
            if (spriteX == 144 && spriteY == 264) {
                changeSpriteLocation("first floor", 192, 240);
            } else if (spriteX == 168 && spriteY == 264) {
                changeSpriteLocation("first floor", 192, 240);
            } else if (spriteX == 192 && spriteY == 264) {
                changeSpriteLocation("first floor", 192, 240);
            } else if (spriteX == 48 && spriteY == 288) {
                changeSpriteLocation("second floor", 264, 480);
            } else if (spriteX == 72 && spriteY == 288) {
                changeSpriteLocation("second floor", 264, 480);
            } else if (spriteX == 96 && spriteY == 288) {
                changeSpriteLocation("second floor", 264, 480);
            }
        } else if (this.getApp().getScreen().getClass() == Class3AScreen.class) {
            if (spriteX == 408 && spriteY == 384) {
                changeSpriteLocation("Second floor", 1224, 1536);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == StairCase2_3Screen.class) {
            if (spriteX == 264 && spriteY == 288) {
                changeSpriteLocation("Second floor", 240, 1536);
                direction = 0;
            } else if (spriteX == 288 && spriteY == 288) {
                changeSpriteLocation("Second floor", 240, 1536);
                direction = 0;
            } else if (spriteX == 312 && spriteY == 288) {
                changeSpriteLocation("Second floor", 240, 1536);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == Class2AScreen.class) {
            if (spriteX == 384 && spriteY == 48) {
                changeSpriteLocation("Second floor", 408, 1512);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == InfirmaryScreen.class) {
            if (spriteX == 72 && spriteY == 144) {
                changeSpriteLocation("Second floor", 1728, 696);
            }
        } else if (this.getApp().getScreen().getClass() == FirstFloorScreen.class) {
            if ((spriteX == 192 || spriteX == 216) && spriteY == 192) {
                changeSpriteLocation("SecondFloorStaircase", 168, 240);
            } else if ((spriteX == 1680 || spriteX == 1704) && spriteY == 240) {
                changeSpriteLocation("SecondFloorStairCase2", 168, 240);
            } else if (spriteX == 1320 && spriteY == 1344) {
                changeSpriteLocation("Class 5A", 360, 72);
                direction = 1;
            } else if (spriteX == 1560 && spriteY == 1344) {
                changeSpriteLocation("Class 5A", 360, 384);
                direction = 1;
            }
        } else if (this.getApp().getScreen().getClass() == StairCase2_3BathroomsScreen.class) {
            if (spriteX == 48 && spriteY == 288) {
                changeSpriteLocation("Bathroom hallway", 552, 216);
                direction = 0;
            } else if (spriteX == 72 && spriteY == 288) {
                changeSpriteLocation("Bathroom hallway", 552, 216);
                direction = 0;
            } else if (spriteX == 96 && spriteY == 288) {
                changeSpriteLocation("Bathroom hallway", 552, 216);
                direction = 0;
            } else if (spriteX == 144 && spriteY == 264) {
                changeSpriteLocation("second floor", 1656, 1656);
                direction = 0;
            } else if (spriteX == 168 && spriteY == 264) {
                changeSpriteLocation("second floor", 1656, 1656);
                direction = 0;
            } else if (spriteX == 192 && spriteY == 264) {
                changeSpriteLocation("second floor", 1656, 1656);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == BathroomHallwayScreen.class) {
            if (spriteX == 528 && spriteY == 240) {
                changeSpriteLocation("Staircase2-3Bathrooms", 72, 264);
                direction = 0;
            } else if (spriteX == 552 && spriteY == 240) {
                changeSpriteLocation("StairCase2-3Bathrooms", 72, 264);
                direction = 0;
            } else if (spriteX == 288 && spriteY == 216) {
                changeSpriteLocation("Boys Bathroom", 312, 72);
                direction = 1;
            } else if (spriteX == 96 && spriteY == 216) {
                changeSpriteLocation("Girls Bathroom", 312, 72);
                direction = 1;
            }
        } else if (this.getApp().getScreen().getClass() == BoysBathroomScreen.class) {
            if (spriteX == 336 && spriteY == 72) {
                changeSpriteLocation("Bathroom hallway", 288, 192);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == GirlsBathroomScreen.class) {
            if (spriteX == 336 && spriteY == 72) {
                changeSpriteLocation("Bathroom hallway", 96, 192);
                direction = 0;
            }
        } else if (this.getApp().getScreen().getClass() == SecondFloorStairCase2Screen.class) {
            if (spriteX == 144 && spriteY == 264) {
                changeSpriteLocation("first floor", 1680, 288);
            } else if (spriteX == 168 && spriteY == 264) {
                changeSpriteLocation("first floor", 1680, 288);
            } else if (spriteX == 192 && spriteY == 264) {
                changeSpriteLocation("first floor", 1680, 288);
            } else if (spriteX == 48 && spriteY == 288) {
                changeSpriteLocation("second floor", 1632, 360);
            } else if (spriteX == 72 && spriteY == 288) {
                changeSpriteLocation("second floor", 1632, 360);
            } else if (spriteX == 96 && spriteY == 288) {
                changeSpriteLocation("second floor", 1632, 360);
            }
        } else if (this.getApp().getScreen().getClass() == Class5AScreen.class) {
            if (spriteX == 384 && spriteY == 384) {
                changeSpriteLocation("first floor", 1560, 1320);
                direction = 0;
            } else if (spriteX == 384 && spriteY == 72) {
                changeSpriteLocation("first floor", 1320, 1320);
                direction = 0;
            }
        }
    }

    private void interpolateSprite() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float alpha = MathUtils.clamp((elapsedTime / spriteDelay), 0f, 1f);
        spriteX = Interpolation.linear.apply(spriteX, futureX, alpha);
        spriteY = Interpolation.linear.apply(spriteY, futureY, alpha);
        if (xAxisMovement) {
            if (spriteX == futureX && futureX != 0) {
                currentSpeed = 0f;
                lastMoveHappened = true;
                elapsedTime = 0f;
                checkForDoor();
            }
        } else if (yAxisMovement) {
            if (spriteY == futureY && futureY != 0) {
                currentSpeed = 0f;
                lastMoveHappened = true;
                elapsedTime = 0f;
                checkForDoor();
            }
        }
    }

    private void changeSpriteLocation(String futureMap, int Xcoord, int Ycoord) {
        //this method is causing the jvm/i965_dri/libc crash!!!
        //maps are not being disposed and are leaking
        //seems to be occurring on the collisionLayer = (Tiled...) ... line
        if (futureMap.equalsIgnoreCase("Class 1A")) {
            publicFutureMap = "Class 1A";
            tempMap.dispose();
            this.getApp().setScreen(new Class1AScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/Class1A.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Second floor")) {
            publicFutureMap = "Second floor";
            tempMap.dispose();
            this.getApp().setScreen(new SecondFloorScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/SecondFloor.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Boys Bathroom")) {
            publicFutureMap = "Boys Bathroom";
            tempMap.dispose();
            this.getApp().setScreen(new BoysBathroomScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/BoysBathroom.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Bathroom hallway")) {
            publicFutureMap = "Bathroom hallway";
            tempMap.dispose();
            this.getApp().setScreen(new BathroomHallwayScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/BathroomHallway.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Class 2A")) {
            publicFutureMap = "Class 2A";
            this.getApp().setScreen(new Class2AScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/Class2A.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
            direction = 1;
        } else if (futureMap.equalsIgnoreCase("Class 3A")) {
            publicFutureMap = "Class 3A";
            tempMap.dispose();
            this.getApp().setScreen(new Class3AScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/Class3A.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
            direction = 1;
        } else if (futureMap.equalsIgnoreCase("Class 5A")) {
            publicFutureMap = "Class 5A";
            tempMap.dispose();
            this.getApp().setScreen(new Class5AScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/Class5A.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Infirmary")) {
            publicFutureMap = "Infirmary";
            tempMap.dispose();
            this.getApp().setScreen(new InfirmaryScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/Infirmary.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("StairCase2-3")) {
            publicFutureMap = "StairCase2-3";
            tempMap.dispose();
            this.getApp().setScreen(new StairCase2_3Screen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/StairCase2-3.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
            direction = 0;
        } else if (futureMap.equalsIgnoreCase("StairCase2-3Bathrooms")) {
            publicFutureMap = "StairCase2-3Bathrooms";
            tempMap.dispose();
            this.getApp().setScreen(new StairCase2_3BathroomsScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/StairCase2-3Bathrooms.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("First Floor")) {
            publicFutureMap = "First Floor";
            tempMap.dispose();
            this.getApp().setScreen(new FirstFloorScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/FirstFloor.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("Girls Bathroom")) {
            publicFutureMap = "Girls Bathroom";
            tempMap.dispose();
            this.getApp().setScreen(new GirlsBathroomScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/GirlsBathroom.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("SecondFloorStairCase")) {
            publicFutureMap = "SecondFloorStairCase";
            tempMap.dispose();
            this.getApp().setScreen(new SecondFloorStairCaseScreen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/SecondFloorStairCase.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else if (futureMap.equalsIgnoreCase("SecondFloorStairCase2")) {
            publicFutureMap = "SecondFloorStairCase2";
            tempMap.dispose();
            this.getApp().setScreen(new SecondFloorStairCase2Screen(this.getApp()));
            tempMap = new TmxMapLoader().load("maps/SecondFloorStairCase.tmx");
            collisionLayer = (TiledMapTileLayer) tempMap.getLayers().get(0);
        } else {
            publicFutureMap = null;
            System.out.println("not a map");
        }
        spriteX = Xcoord;
        spriteY = Ycoord;
        currentSpeed = 0f;
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
                move(0, 1);
            } else if (movingDown && !movingUp && !movingLeft && !movingRight) {
                lastMoveHappened = false;
                move(0, -1);
            } else if (movingLeft && !movingDown && !movingUp && !movingRight) {
                lastMoveHappened = false;
                move(-1, 0);
            } else if (movingRight && !movingUp && !movingDown && !movingLeft) {
                lastMoveHappened = false;
                move(1, 0);
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
            // X is offset by -11 as the source sprite sheet isn't a power of two.
        } else {
            TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(3f, false); // Don't
            // draw the sprite mid animation if you are against a blocked tile
            batch.draw(currentFrame, (spriteX - 11), spriteY); // Draw current frame at (X, Y)
            // X is offset by -11 as the source sprite sheet isn't a power of two.
        }
        // call the movement method every frame, allowing for continuous input
        movement();
        // interpolate sprite if it's moving
        if (!lastMoveHappened && !collide) {
            interpolateSprite();
        }

    }

    public void dispose() {
        spriteBatch.dispose();
        walkSheet.dispose();
    }

}
