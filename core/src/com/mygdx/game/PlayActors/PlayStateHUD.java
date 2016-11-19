package com.mygdx.game.PlayActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameDemo;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.State;

/**
 * Created by flynn on 11/19/16.
 */


public class PlayStateHUD extends Actor {

    private Texture background;

    public PlayStateHUD() {
        setTouchable(Touchable.enabled);
        background = new Texture("playstate-hud.png");
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(background,0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
    }
}
