package com.groupofseven.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.groupofseven.model.Player;

public class PlayerInput extends InputAdapter {

    private Player me;

    // store a reference of Player as this class.player = p
    public PlayerInput(Player p) {
        this.me = p;
    }

    // player input method
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.W) {
            this.me.movingNowhere = false;
            this.me.movingUp = true;
        }

        if (keycode == Keys.A) {
            this.me.movingNowhere = false;
            this.me.movingLeft = true;
        }

        if (keycode == Keys.S) {
            this.me.movingNowhere = false;
            this.me.movingDown = true;
        }

        if (keycode == Keys.D) {
            this.me.movingNowhere = false;
            this.me.movingRight = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.W) {
//			me.currentSpeed = 0f;
            this.me.movingUp = false;
            this.me.movingNowhere = true;
        }

        if (keycode == Keys.A) {
//			me.currentSpeed = 0f;
            this.me.movingLeft = false;
            this.me.movingNowhere = true;
        }

        if (keycode == Keys.S) {
//			me.currentSpeed = 0f;
            this.me.movingDown = false;
            this.me.movingNowhere = true;
        }

        if (keycode == Keys.D) {
//			me.currentSpeed = 0f;
            this.me.movingRight = false;
            this.me.movingNowhere = true;
        }

        if (keycode == Keys.F) {
            this.me.checkForAction();
        }

        return false;
    }
}
