package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameDemo;

/**
 * Created by flynn on 11/16/16.
 */

public class MenuState extends State {
    private Texture background;
    private Texture playButton;
    private Texture title;
    private Texture menuBar;

    private Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -5;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        position = new Vector3(0, GameDemo.HEIGHT - 200, 0);
        velocity = new Vector3(0, GRAVITY, 0);
        background = new Texture("menu-background.jpg");
        playButton = new Texture("play-button.png");
        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");
    }

    @Override
    protected void handleInput() {
        //TO DO: Buttons
    }

    @Override
    protected void update(float dt) {
        handleInput();
        Gdx.app.log("debug", " " + position.y);
        if(position.y > (GameDemo.HEIGHT - 50) - 350) {
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }

        else if (position.y < (GameDemo.HEIGHT - 50) - 350) {
            velocity.add(0, 15, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
        }


        velocity.scl(1/dt);
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
        sb.draw(title, 0, position.y);
        sb.draw(menuBar, 0, (GameDemo.HEIGHT - 50) - 350 - 10);
        sb.draw(playButton, (GameDemo.WIDTH / 2) - (playButton.getWidth() / 2), GameDemo.HEIGHT / 2 - (playButton.getHeight() /2) );
        sb.end();

    }
}
