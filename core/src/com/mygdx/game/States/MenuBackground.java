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

public class MenuBackground extends Actor {
    private Texture background;
    private Texture title;
    private Texture menuBar;

    public MenuBackground () {
        background = new Texture("menu-background.jpg");
        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(background,0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
        sb.draw(title, 0, MenuState.position.y);
        sb.draw(menuBar, 0, (GameDemo.HEIGHT - 50) - 350 - 10);
    }
}