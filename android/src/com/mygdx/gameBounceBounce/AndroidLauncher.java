package com.mygdx.gameBounceBounce;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements com.mygdx.gameBounceBounce.States.PlayServices {

	private final static int requestCode = 1;
	private GameHelper gameHelper;

	@Override
	protected void onCreate (Bundle savedInstanceState) {


		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){
				System.out.print("FAIL");
			}
			@Override
			public void onSignInSucceeded(){
				System.out.print("SUCCESS");
			}
		};

		gameHelper.setup(gameHelperListener);

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameDemo(this), config);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		//gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void signOut() {

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void unlockAchievement() {
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_future_esports_legend ));

	}

	@Override
	public void submitScore(int highScore) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highscores), highScore);
		}

	}

	@Override
	public void showAchievement() {

	}

	@Override
	public void showScore() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highscores)), requestCode);
		}
		else
		{
			signIn();
		}

	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}
}
