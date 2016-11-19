package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameDemo;

/**
 * Created by flynn on 11/18/16.
 */

public class MenuPlayButton extends Actor {
    Texture region;

    public MenuPlayButton () {
        setTouchable(Touchable.enabled);

        region = new Texture("play-button.png");
        setBounds((GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()), region.getWidth(), region.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("debug", "touched down");
                return true;
            }
        });
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(region, (GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()));

    }
}