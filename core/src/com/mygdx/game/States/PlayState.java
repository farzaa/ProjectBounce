package com.mygdx.game.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    public static final float GRAVITY = 5;
    public static final float PIXELS_TO_METERS = 100f;

    World world;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    Texture playstateHUD;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        //Box2D.init();

        playstateHUD = new Texture("playstate-hud.png");
        //First actually create World and add basic boundaries.
        initWorld();
        //Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    public void initWorld() {
        //Create world, give it gravity.
        world = new World(new Vector2(0, GRAVITY), true);
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        //Now create the boundaries. Pass it the vertexes.
        //bottom edge
        createEdge(-w/2,-h/2,w/2,-h/2);
        //left edge.
        createEdge(-w/2,-h/2,-w/2,h/2);
        //right edge
        createEdge(w/2,-h/2,w/2,h/2);

        //TO DO: top edge.
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

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        debugMatrix = sb.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);

        sb.begin();
        sb.draw(playstateHUD, -540, -960);

        sb.end();
        debugRenderer.render(world, debugMatrix);
    }
}
