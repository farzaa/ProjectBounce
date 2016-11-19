package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameDemo;

/**
 * Created by flynn on 11/18/16.
 */

public class MenuBackground extends Actor {
    private Texture background;
    private Texture title;
    private Texture menuBar;

    public  Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -5;


    public MenuBackground () {
        background = new Texture("menu-background.jpg");
        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");
        position = new Vector3(0, GameDemo.HEIGHT - 200, 0);
        velocity = new Vector3(0, GRAVITY, 0);
    }

    public void update(float dt) {

        if(position.y > (GameDemo.HEIGHT - 350)) {
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }

        else if (position.y < (GameDemo.HEIGHT - 350)) {
            velocity.add(0, 15, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }


        velocity.scl(1/dt);
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(background,0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
        sb.draw(title, 0, position.y);
        sb.draw(menuBar, 0, (GameDemo.HEIGHT - 350), GameDemo.WIDTH, menuBar.getHeight());
    }
}