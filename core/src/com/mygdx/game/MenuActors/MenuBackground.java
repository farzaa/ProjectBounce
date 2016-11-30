package com.mygdx.game.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
    public Texture background;
    public Texture title;
    public Texture menuBar;

    private Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -5;

    private Rectangle titleRectangle;
    private Rectangle menuBarRectangle;

    public MenuBackground () {
        background = new Texture("menu-background.jpg");
        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");
        position = new Vector3(0, GameDemo.HEIGHT - 200, 0);
        velocity = new Vector3(0, GRAVITY, 0);

        //Initialize title rectangle
        titleRectangle = new Rectangle(0, GameDemo.HEIGHT - 50, title.getWidth(), title.getHeight());
        menuBarRectangle = new Rectangle(0, (GameDemo.HEIGHT - 355), GameDemo.WIDTH, menuBar.getHeight());
    }

    public void update(float dt) {

        //This stops the title from flying off the top of the screen.
        if(titleRectangle.getY() > (GameDemo.HEIGHT - 350)) {
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }

        //This lets the title bounce when it collides with the black bar.
        else if (titleRectangle.overlaps(menuBarRectangle)) {
            velocity.add(0, 15, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }

        velocity.scl(1/dt);
        //We want to update the value of the titleRectangle since its constantly moving!
        titleRectangle.set(0, position.y, title.getWidth(), title.getHeight());
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(background,0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
//        sb.draw(title, 0, position.y);
//        //I chose to to hard code to draw it at -350. This can be changed later to some fractional value.
//        sb.draw(menuBar, 0, (GameDemo.HEIGHT - 355), GameDemo.WIDTH, menuBar.getHeight());
    }
}