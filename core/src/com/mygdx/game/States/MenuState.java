package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameDemo;

/**
 * Created by flynn on 11/16/16.
 */

public class MenuState extends State {
    private Texture background;
    private Texture title;
    private Texture menuBar;

    public static Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -5;

    private Stage menuStage;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        menuStage = new Stage(new ScreenViewport());
        position = new Vector3(0, GameDemo.HEIGHT - 200, 0);
        velocity = new Vector3(0, GRAVITY, 0);
        background = new Texture("menu-background.jpg");
        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");

        MenuPlayButton menuPlayButton = new MenuPlayButton();
        MenuBackground menuBackground = new MenuBackground();
        menuStage.addActor(menuBackground);
        menuStage.addActor(menuPlayButton);
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {
        handleInput();
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
        menuStage.draw();
        menuStage.act(Gdx.graphics.getDeltaTime());
    }
}
