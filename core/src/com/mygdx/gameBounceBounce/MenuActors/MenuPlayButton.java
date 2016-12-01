package com.mygdx.gameBounceBounce.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.gameBounceBounce.GameDemo;
import com.mygdx.gameBounceBounce.States.GameStateManager;
import com.mygdx.gameBounceBounce.States.PlayServices;
import com.mygdx.gameBounceBounce.States.PlayState;

/**
 * Created by flynn on 11/18/16.
 */

public class MenuPlayButton extends Actor {
    public Texture region;
    GameStateManager gsm;

    public PlayServices playservices;

    public MenuPlayButton (PlayServices playServices, GameStateManager gsmCurr, final com.mygdx.gameBounceBounce.States.MenuState menustate) {
        this.gsm = gsmCurr;
        this.playservices = playServices;
        setTouchable(Touchable.enabled);
        region = new Texture("play-button.png");
        setBounds((GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()/2), region.getWidth(), region.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("debug", "touched play button!");
                menustate.dispose();
                gsm.set(new PlayState(playservices, gsm));
                return true;
            }
        });
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(region, (GameDemo.WIDTH / 2) - (region.getWidth() / 2), GameDemo.HEIGHT / 2 - (region.getHeight()/2));
    }
}