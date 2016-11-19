package com.mygdx.game.PlayActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Toan on 11/19/2016.
 */

public class Ball extends Actor{
    Texture ballTexture;

    public Ball() {
        ballTexture = new Texture("ball.png");
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(ballTexture, 300, 300);
    }
}
