package com.mygdx.gameBounceBounce.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gameBounceBounce.GameDemo;
import com.mygdx.gameBounceBounce.MenuActors.MenuBackground;
import com.mygdx.gameBounceBounce.MenuActors.MenuPlayButton;

/**
 * Created by flynn on 11/16/16.
 */

public class MenuState extends State {


    public Texture title;
    public Texture menuBar;

    private Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -5;

    private Rectangle titleRectangle;
    private Rectangle menuBarRectangle;


    private Stage menuStage;
    private MenuBackground menuBackground;
    private MenuPlayButton menuPlayButton;
    Music menuMusic;

    com.mygdx.gameBounceBounce.States.PlayServices playServices;

    public MenuState(com.mygdx.gameBounceBounce.States.PlayServices playServices, final com.mygdx.gameBounceBounce.States.GameStateManager gsm) {
        super(gsm);

        this.playServices = playServices;
        //playServices.signIn();

        title = new Texture("menu-title.png");
        menuBar = new Texture("menu-black-bar.png");

        position = new Vector3(0, GameDemo.HEIGHT - 200, 0);
        velocity = new Vector3(0, GRAVITY, 0);

        //Initialize title rectangle
        titleRectangle = new Rectangle(0, GameDemo.HEIGHT - 50, title.getWidth(), title.getHeight());
        menuBarRectangle = new Rectangle(0, (GameDemo.HEIGHT - 355), GameDemo.WIDTH, menuBar.getHeight());

        menuStage = new Stage(new ScreenViewport());
        menuPlayButton = new MenuPlayButton(playServices, gsm, this);
        menuBackground = new MenuBackground();
        menuStage.addActor(menuBackground);
        menuStage.addActor(menuPlayButton);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu-state-audio.mp3"));
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
    }

    @Override
    protected void render(SpriteBatch sb) {

        menuStage.draw();
        menuStage.act(Gdx.graphics.getDeltaTime());


//        if(playServices.isSignedIn() ==  true) {
//            menuBackground.update(Gdx.graphics.getDeltaTime());
//            sb.draw(title, 0, position.y);
//            //I chose to to hard code to draw it at -350. This can be changed later to some fractional value.
//            sb.draw(menuBar, 0, (GameDemo.HEIGHT - 355), GameDemo.WIDTH, menuBar.getHeight());
//        }



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
