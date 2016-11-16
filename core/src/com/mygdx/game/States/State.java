package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by flynn on 11/16/16.
 */

public abstract class State {
    protected GameStateManager gsm;

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    protected abstract void handleInput();
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);

}
