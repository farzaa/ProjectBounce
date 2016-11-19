package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameDemo;
import com.mygdx.game.PlayActors.Ball;
import com.mygdx.game.PlayActors.PlayStateHUD;

/**
 * Created by flynn on 11/16/16.
 */

public class PlayState extends State {

    private Stage playStateStage;
    private PlayStateHUD playStateHUD;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        playStateStage = new Stage(new ScreenViewport());
        playStateHUD = new PlayStateHUD();
        playStateStage.addActor(playStateHUD);
        Gdx.input.setInputProcessor(playStateStage);
    }

    @Override
    protected void handleInput() {
        //TO DO: Touch Control
    }

    @Override
    protected void update(float dt) {
        handleInput();
        playStateStage.addActor(new Ball(gsm));
    }

    @Override
    protected void render(SpriteBatch sb) {
        playStateStage.draw();
        playStateStage.act(Gdx.graphics.getDeltaTime());
    }
}
