package com.mygdx.gameBounceBounce;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gameBounceBounce.States.GameStateManager;
import com.mygdx.gameBounceBounce.States.PlayServices;


public class GameDemo extends ApplicationAdapter {

	public static PlayServices playServices;
	public static int WIDTH;
	public static int HEIGHT;
	private SpriteBatch batch;
	private GameStateManager gsm;

	public GameDemo(PlayServices playServices) {
		this.playServices = playServices;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		if (Gdx.app.getType().equals(Application.ApplicationType.Android)) {
			WIDTH = Gdx.graphics.getWidth();
			HEIGHT = Gdx.graphics.getHeight();
			Gdx.app.log("debug", "Android Device recognized. Width/Height... " + WIDTH + " / " + HEIGHT);
			batch = new SpriteBatch();
			gsm = new GameStateManager();
			gsm.push(new com.mygdx.gameBounceBounce.States.MenuState(playServices, gsm));
		}

		else {
			Gdx.app.log("debug", "App was launched as neither Desktop or Android... quitting");
			System.exit(0);
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}


}
