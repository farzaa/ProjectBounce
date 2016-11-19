package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameDemo;
import com.mygdx.game.PlayActors.Bar;
import com.mygdx.game.PlayActors.PlayStateHUD;

/**
 * Created by flynn on 11/16/16.
 */

public class PlayState extends State {

    private Stage playStateStage;
    private PlayStateHUD playStateHUD;
    private Bar bar1;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        playStateStage = new Stage(new ScreenViewport());
        playStateHUD = new PlayStateHUD();
        playStateStage.addActor(playStateHUD);
        bar1 = new Bar();
        playStateStage.addActor(bar1);
        Gdx.input.setInputProcessor(playStateStage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        playStateStage.draw();
        playStateStage.act(Gdx.graphics.getDeltaTime());
    }
}
