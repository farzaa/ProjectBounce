package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameDemo;

/**
 * Created by flynn on 11/16/16.
 */

public class PlayState extends State {

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {
        //TO DO: Touch Control
    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.end();

    }
}
