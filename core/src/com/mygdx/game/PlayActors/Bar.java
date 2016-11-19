package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.GameDemo;
import com.mygdx.game.States.PlayState;

/**
 * Created by flynn on 11/19/16.
 */

public class Bar extends Actor {

    private Texture barTexture;
    private Rectangle textureRegion;

    public Bar() {
        barTexture = new Texture("ingame-gap-bar.png");
        setTouchable(Touchable.enabled);
        //Initialize. The bounds WILL change as the user slides the bar around.
        textureRegion = new Rectangle(0, 900, GameDemo.WIDTH, barTexture.getHeight());
        this.setX(0);
        this.setY(900);
        this.setWidth(1080);
        this.setHeight(30);
        //setBounds(0, 900, barTexture.getWidth(), barTexture.getHeight());

        addListener(new DragListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                updateBarLocation(Gdx.input.getX());
            }
        });
    }

    public void updateBarLocation(float x) {
        textureRegion.setX(x);
    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        sb.draw(barTexture, textureRegion.getX(), textureRegion.getY());
    }
}
