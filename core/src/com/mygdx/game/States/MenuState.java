package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MenuActors.MenuBackground;
import com.mygdx.game.MenuActors.MenuPlayButton;

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
        menuPlayButton = new com.mygdx.game.MenuActors.MenuPlayButton();
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
