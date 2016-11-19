package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.States.GameStateManager;

/**
 * Created by Toan on 11/19/2016.
 */

public class Ball extends Actor{
    Texture ballTexture;
    GameStateManager gsm;

    public Ball(GameStateManager gsm) {
        ballTexture = new Texture("ball_128_102.png");
        this.gsm = gsm;
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(ballTexture, 300, 300);
    }
}
