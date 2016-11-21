package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameDemo;

import java.util.Random;

/**
 * Created by flynn on 11/19/16.
 */

public class Ball extends Actor{

    private final int UPWARD_ACCEL = 4;
    private int X_ACCEL;
    private Texture ballTexture;
    private Vector2 ballVector;
    private Circle ballCircle;
    public Ball(Vector2 ballVector) {

        ballTexture = new Texture("circle-image.png");
        this.ballVector = ballVector;
        Random rn = new Random();
        X_ACCEL = rn.nextInt(20) -10;
        ballCircle = new Circle(ballVector.x, ballVector.y, 1f);
    }

    public void update() {

        ballVector.add(X_ACCEL, UPWARD_ACCEL);
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(ballTexture, ballVector.x, ballVector.y, ballTexture.getWidth()/2, ballTexture.getHeight()/2);
    }
}
