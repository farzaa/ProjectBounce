package com.mygdx.game.MenuActors;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameDemo;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;
import com.mygdx.game.States.PlayState;

/**
 * Created by flynn on 11/18/16.
 */

public class MenuPlayButton extends Actor {
    public Texture region;
    GameStateManager gsm;

    public MenuPlayButton (GameStateManager gsmCurr, final MenuState menustate) {
        this.gsm = gsmCurr;
        setTouchable(Touchable.enabled);
        region = new Texture("play-button.png");
        setBounds((GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()/2), region.getWidth(), region.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("debug", "touched play button!");
                menustate.dispose();
                gsm.set(new PlayState(gsm));
                return true;
            }
        });
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(region, (GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()/2));
    }
}