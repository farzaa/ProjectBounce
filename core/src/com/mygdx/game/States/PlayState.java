package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameDemo;
import com.mygdx.game.PlayActors.Ball;
import com.mygdx.game.PlayActors.Bar;
import com.mygdx.game.PlayActors.PlayStateHUD;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by flynn on 11/16/16.
 */

public class PlayState extends State {

    private Stage playStateStage;
    private PlayStateHUD playStateHUD;
    private Bar bar1;
    private ArrayList<Ball> ballList;

    static final float SCALE = 0.05f;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        playStateStage = new Stage(new ScreenViewport());
        playStateHUD = new PlayStateHUD();
        playStateStage.addActor(playStateHUD);
        bar1 = new Bar();
        playStateStage.addActor(bar1);
        Gdx.input.setInputProcessor(playStateStage);
        ballList = new ArrayList<Ball>();
        spawnBalls();
    }

    @Override
    protected void handleInput() {

    }

    public void spawnBalls() {
        for(int i = 0; i < 5; i++) {
            Vector2 vector = new Vector2();
            vector.set(500, 500);
            Ball ball = new Ball(vector);
            ballList.add(ball);
            playStateStage.addActor(ball);
        }
    }
    @Override
    protected void update(float dt) {
        handleInput();
        if(ballList.size() < 10) {
            spawnBalls();
        }
        for(int i = 0; i < ballList.size(); i++) {
            ballList.get(i).update();
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        playStateStage.draw();
        playStateStage.act(Gdx.graphics.getDeltaTime());
    }
}
