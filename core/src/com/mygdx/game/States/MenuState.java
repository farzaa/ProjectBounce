package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    Music menuMusic;

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        menuStage = new Stage(new ScreenViewport());
        menuPlayButton = new MenuPlayButton(gsm, this);
        menuBackground = new MenuBackground();
        menuStage.addActor(menuBackground);
        menuStage.addActor(menuPlayButton);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menu-state-audio.mp3"));
        menuMusic.play();
        menuMusic.setVolume(0.3f);
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    protected void handleInput() {
        menuPlayButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("debug", "touched play button!");
                //gsm.set(new PlayState(gsm));
                return true;
            }
        });
    }

    @Override
    protected void update(float dt) {
        handleInput();
        menuBackground.update(dt);
    }

    @Override
    protected void render(SpriteBatch sb) {
        menuStage.draw();
        menuStage.act(Gdx.graphics.getDeltaTime());
    }

    //do not want any nasty memory leaks.
    public void dispose() {
        Gdx.app.log("debug", "Disposing menu state assets...");
        menuMusic.stop();
        menuMusic.dispose();
        menuBackground.background.dispose();
        menuBackground.menuBar.dispose();
        menuBackground.title.dispose();
        menuPlayButton.region.dispose();
    }

}
