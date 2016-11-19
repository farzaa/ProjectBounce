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

    private Stage menuStage;
    private MenuBackground menuBackground;
    private MenuPlayButton menuPlayButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        menuStage = new Stage(new ScreenViewport());
        menuPlayButton = new MenuPlayButton();
        menuBackground = new MenuBackground();
        menuStage.addActor(menuBackground);
        menuStage.addActor(menuPlayButton);
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    protected void handleInput() {
        //This will most likely not be necessary her ebut I still need to have it here since we extend state.
        ;
    }

    @Override
    protected void update(float dt) {
        menuBackground.update(dt);

    }

    @Override
    protected void render(SpriteBatch sb) {
        menuStage.draw();
        menuStage.act(Gdx.graphics.getDeltaTime());
    }
}
