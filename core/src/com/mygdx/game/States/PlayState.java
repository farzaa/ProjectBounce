package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PlayActors.Ball;
import com.mygdx.game.PlayActors.BarBox2D;

import java.util.ArrayList;

/**
 * Created by flynn on 11/16/16.
 */


//I implement input processor here so that focus can change from the menu to the play screen.
public class PlayState extends State implements InputProcessor {
    public static final float GRAVITY = 0.5f;
    public static final float PIXELS_TO_METERS = 100f;

    public World world;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    Texture playstateHUD;
    ArrayList<Ball> ballList;

    BarBox2D bar;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        //Many tutorials say to this step, but things seems to be working fine without it.
        //Box2D.init();

        playstateHUD = new Texture("playstate-hud.png");
        //First actually create World and add basic boundaries.
        initWorld();

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ballList = new ArrayList<Ball>();
        spawnBalls();

    }

    public void initWorld() {
        //Create world, give it gravity.
        world = new World(new Vector2(0, GRAVITY), true);
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        //Now create the boundaries. Pass it the vertexes.
        //top edge
        createEdge(-w/2,h/2,w/2,h/2);
        //left edge.
        //createEdge(-w/2,-7*h,-w/2,h/2);

        //TESTING
        //Create part of left edge
        createEdge(-w/2 + 1, h/2, -w/2 + 1, 20/PIXELS_TO_METERS);

        //Now create the REST of it.
        createEdge(-w/2 + 1, -20/PIXELS_TO_METERS ,-w/2 + 1, -7*h);

        //right edge
        createEdge(w/2,-7*h,w/2,h/2);

        //Now create the bars
        bar = new BarBox2D(world);
    }

    public void createBar() {

    }


    public void createEdge (float vx1, float vy1, float vx2, float vy2){
        BodyDef edgeInitial = new BodyDef();
        //StaticBody because our edges will not move.
        edgeInitial.type = BodyDef.BodyType.StaticBody;
        edgeInitial.position.set(0,0);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(vx1, vy1, vx2, vy2);
        FixtureDef leftEdgeFix = new FixtureDef();
        leftEdgeFix.shape = edgeShape;

        Body edgeFinal = world.createBody(edgeInitial);
        edgeFinal.createFixture(leftEdgeFix);

        edgeShape.dispose();
    }

    public void spawnBalls() {
        for(int i = 0; i < 5; i++) {
            ballList.add(new Ball(world));
        }
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {

    }

    @Override
    protected void render(SpriteBatch sb) {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        for(int i = 0; i < ballList.size(); i++) {
            Sprite currSprite = ballList.get(i).ballSprite;
            Body currBody = ballList.get(i).ballBody;
            currSprite.setPosition((currBody.getPosition().x * PIXELS_TO_METERS) - currSprite.getWidth()/2 , (currBody.getPosition().y * PIXELS_TO_METERS) -currSprite.getHeight()/2 );
            currSprite.setRotation((float)Math.toDegrees(currBody.getAngle()));
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        debugMatrix = sb.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);

        sb.begin();
        sb.draw(playstateHUD, -540, -960);
        for(int i = 0; i < ballList.size(); i++) {
            Sprite sprite = ballList.get(i).ballSprite;
            //Gdx.app.log("debug", sprite.getX() + " " + sprite.getY());
            sb.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
                    sprite.getOriginY(),
                    sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
                            getScaleY(), sprite.getRotation());
        }

        sb.draw(bar.barSprite, bar.barSprite.getX(),bar.barSprite.getY());

        sb.end();
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //spawnBalls();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Check if bar is being touched!
        Gdx.app.log("debug", "Drag: " + screenX + " " + screenY);
        Gdx.app.log("debug", "BarPos : " + bar.barBody.getPosition().x + " " + bar.barBody.getPosition().y);


        //if(screenX-540 < 100 && -100 > screenX -540) {
            bar.barBody.setTransform(screenX - 540, bar.barBody.getPosition().y, 0);
            Gdx.app.log("debug", "BarSpriteLoc: " + bar.barSprite.getX());
            //if(bar.barSprite.getWidth()/2 + 1080  >= 0)
            //bar.barSprite.setPosition(screenX - 540 - bar.barSprite.getWidth(), 0);
        //}

        //bar.barBody.setLinearVelocity(1,0);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
