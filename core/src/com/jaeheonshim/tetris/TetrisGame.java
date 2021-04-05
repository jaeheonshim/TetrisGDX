package com.jaeheonshim.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jaeheonshim.tetris.screens.MusicPreferenceScreen;
import com.jaeheonshim.tetris.screens.game.GameScreen;
import com.jaeheonshim.tetris.screens.game.MenuScreen;

public class TetrisGame extends Game {
	private GameScreen gameScreen;
	private MusicPreferenceScreen musicPreferenceScreen;
	private MenuScreen menuScreen;

	public static String PREFERENCES = "com.jaeheonshim.tetris.preferences";
	
	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		musicPreferenceScreen = new MusicPreferenceScreen(this);
		menuScreen = new MenuScreen(this);

		Preferences preferences = Gdx.app.getPreferences(PREFERENCES);
		if(preferences.getInteger("soundOn", -1) == -1) {
			setScreen(new MusicPreferenceScreen(this));
		} else {
			setScreen(menuScreen);
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
