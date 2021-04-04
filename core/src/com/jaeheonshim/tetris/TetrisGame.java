package com.jaeheonshim.tetris;

import com.badlogic.gdx.Game;
import com.jaeheonshim.tetris.screens.GameScreen;

public class TetrisGame extends Game {
	private GameScreen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();

		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
